package com.heroku.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.Duration;

import com.heroku.java.model.*;

import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

// import org.jscience.physics.amount.Amount;
// import org.jscience.physics.model.RelativisticModel;
// import javax.measure.unit.SI;
@SpringBootApplication
@Controller

public class ReservationController {
  private final DataSource dataSource;

  @Autowired
  public ReservationController(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @GetMapping("/guestMakeRoomReservation")
  public String guestMakeRoomReservation(HttpSession session) {
    String guestICNumber = (String) session.getAttribute("guestICNumber");
    return "guest/guestMakeRoomReservation";
  }

    public static boolean checkRoomAvailability(String roomType, int totalRooms, Date startDate, Date endDate, Connection connection) throws SQLException {
        // Check if there are any overlapping reservations for the selected room type and date range
        String sql = "SELECT COUNT(*) FROM roomreservation rr " +
                "JOIN reservation r ON rr.reservationid = r.reservationid " +
                "WHERE rr.roomnum IN (SELECT roomnum FROM room WHERE roomtype = ? AND roomstatus = 'Available') " +
                "  AND ((r.datestart <= ? AND r.dateend >= ?) OR (r.datestart <= ? AND r.dateend >= ?))";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roomType);
            statement.setDate(2, new java.sql.Date(endDate.getTime()));  // Check if the reservation end date is after the selected start date
            statement.setDate(3, new java.sql.Date(startDate.getTime())); // Check if the reservation start date is before the selected end date
            statement.setDate(4, new java.sql.Date(startDate.getTime())); // Check if the reservation start date is before the selected end date
            statement.setDate(5, new java.sql.Date(endDate.getTime()));   // Check if the reservation end date is after the selected start date

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int overlappingReservationsCount = resultSet.getInt(1);
                    return overlappingReservationsCount == 0;
                }
            }
        }
        return false;
    }

    public static List<String> getAvailableRoomNumbers(String roomType, int totalRooms, Date startDate, Date endDate, Connection connection) throws SQLException {
        // Query to get available room numbers
        String sql = "SELECT roomnum FROM room WHERE roomtype = ? AND roomstatus = 'Available' " +
                "AND roomnum NOT IN (SELECT roomnum FROM roomreservation rr " +
                "JOIN reservation r ON rr.reservationid = r.reservationid " +
                "WHERE (r.datestart <= ? AND r.dateend >= ?) OR (r.datestart <= ? AND r.dateend >= ?)) " +
                "LIMIT ?";
    
        List<String> availableRoomNumbers = new ArrayList<>();
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roomType);
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            statement.setDate(3, new java.sql.Date(startDate.getTime()));
            statement.setDate(4, new java.sql.Date(startDate.getTime()));
            statement.setDate(5, new java.sql.Date(endDate.getTime()));
            statement.setInt(6, totalRooms);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    availableRoomNumbers.add(resultSet.getString("roomnum"));
                }
            }
        }
    
        return availableRoomNumbers;
    }

    public static Date convertToPostgresDate(String originalDateString) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("MM-dd-yy");

        try {
            // Parse the original string
            java.util.Date utilDate = originalFormat.parse(originalDateString);

            // Format it for PostgreSQL (YYYY-MM-DD)
            SimpleDateFormat postgresqlFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDateString = postgresqlFormat.format(utilDate);

            // Convert the formatted string to java.sql.Date
            return Date.valueOf(formattedDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
            return null; // or throw an exception
        }
    }

    public static int calculateDurationOfStay(String startDateString, String endDateString) {
        try {
            // Convert start date to java.sql.Date
            Date startDate = convertToPostgresDate(startDateString);
    
            // Convert end date to java.sql.Date
            Date endDate = convertToPostgresDate(endDateString);
    
            // Calculate the duration in milliseconds
            long durationMillis = endDate.getTime() - startDate.getTime();
    
            // Convert duration to days and cast to int
            return (int) Duration.ofMillis(durationMillis).toDays();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception according to your needs
            return -1; // or throw an exception
        }
    }

    private int getMaxGuestsForRoom(String roomNumber, Connection connection) throws SQLException {
        // Query to get the max guests for the specified room
        String sql = "SELECT maxGuest FROM room WHERE roomnum = ?";
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roomNumber);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("maxGuest");
                }
            }
        }
        throw new SQLException("Failed to get max guests for room: " + roomNumber);
    }

    private double calculateTotalPayment(List<String> availableRoomNumbers, Connection connection) throws SQLException {
        double totalPayment = 0.0;
    
        // Iterate through each room and fetch its roomrate
        for (String roomNumber : availableRoomNumbers) {
            String sqlRoomRate = "SELECT roomrate FROM room WHERE roomnum = ?";
            try (PreparedStatement statementRoomRate = connection.prepareStatement(sqlRoomRate)) {
                statementRoomRate.setString(1, roomNumber);
    
                try (ResultSet resultSetRoomRate = statementRoomRate.executeQuery()) {
                    if (resultSetRoomRate.next()) {
                        double roomRate = resultSetRoomRate.getDouble("roomrate");
                        totalPayment += roomRate;
                    }
                }
            }
        }
    
        return totalPayment;
    }

  @PostMapping("/guestMakeRoomReservation")
  public String guestMakeRoomReservation(HttpSession session, @ModelAttribute("guestMakeRoomReservation") reservation reservation, 
  room room, roomReservation roomReservation, staff staff, Model model, @RequestParam("addon") String addon,
  @RequestParam("roomType") String roomType, @RequestParam("date") String date){

    try{
        Connection connection = dataSource.getConnection();

        //assign manager ic number to reservation
        String sqlStaff = "SELECT stafficnumber FROM staff where staffrole = ?";
        final var statementStaff = connection.prepareStatement(sqlStaff);
        statementStaff.setString(1, "Manager");
        final var resultSetStaff = statementStaff.executeQuery();

        if (resultSetStaff.next()) {
            String staffICNumber = resultSetStaff.getString("staffICNumber");

        String[] dateParts = date.split(" to ");
        
        // Extract start date and end date
        String dateStart = dateParts[0];
        String dateEnd = dateParts[1];
        String guestICNumber = (String) session.getAttribute("guestICNumber");
        int totalAdult = reservation.getTotalAdult();
        int totalKids = reservation.getTotalKids();
        String reserveStatus = "Pending";
        int totalRoom = reservation.getTotalRoom();
        double totalPayment = 0.00;
        int guestQuantity = totalAdult + totalKids;
        
        //debugging
        System.out.println("dateStart: " + dateStart);
        System.out.println("dateEnd: " + dateEnd);
        System.out.println("guestICNumber: " + guestICNumber);
        System.out.println("totalAdult: " + totalAdult);
        System.out.println("totalKids: " + totalKids);
        System.out.println("totalRoom: " + totalRoom);
        System.out.println("guestQuantity: " + guestQuantity);
        System.out.println("roomType: " + roomType);

        Date dateStartDate = convertToPostgresDate(dateStart);
        Date dateEndDate = convertToPostgresDate(dateEnd);
        System.out.println("date start: " + dateStartDate);
        System.out.println("date end: " + dateEndDate);
        int durationOfStay = calculateDurationOfStay(dateStart, dateEnd);

        String sqlReservation = "INSERT INTO reservation(guestICNumber, guestQuantity, durationOfStay, datestart, dateend, totaladult, totalkids, reservestatus, totalroom, totalpayment, stafficnumber) VALUES (?,?,?,?,?,?,?,?,?,?,?) RETURNING reservationid";
        final var statementReservation = connection.prepareStatement(sqlReservation);
        
        statementReservation.setString(1,guestICNumber);
        statementReservation.setInt(2,guestQuantity);
        statementReservation.setInt(3,durationOfStay);
        statementReservation.setDate(4,dateStartDate);
        statementReservation.setDate(5,dateEndDate);
        statementReservation.setInt(6,totalAdult);
        statementReservation.setInt(7,totalKids);
        statementReservation.setString(8,reserveStatus);
        statementReservation.setInt(9,totalRoom);
        statementReservation.setDouble(10,totalPayment);
        statementReservation.setString(11,staffICNumber);

        final var resultSetReservation = statementReservation.executeQuery();

        int reservationID = 0;
        // Retrieve the auto-generated reservationID
        if (resultSetReservation.next()) {
            reservationID = resultSetReservation.getInt("reservationID");
        }
        System.out.println("id from db reservation : " + reservationID);
        
        boolean available = checkRoomAvailability(roomType, totalRoom, dateStartDate, dateEndDate, connection);
        System.out.println(available);

        if (available) {
             // Get available room numbers
             List<String> availableRoomNumbers = getAvailableRoomNumbers(roomType, totalRoom, dateStartDate, dateEndDate, connection);
            int totalMaxGuests = availableRoomNumbers.stream()
            .mapToInt(roomNumber -> {
                try{ return getMaxGuestsForRoom(roomNumber, connection);
            }
            catch (SQLException e){
                e.printStackTrace();
                return 0;
            }
        }).sum();
            // Check if the total guest quantity exceeds the total maximum allowed guests
            boolean exceedsMaxGuests = guestQuantity > totalMaxGuests;
            if (!exceedsMaxGuests){
            // Insert room numbers into roomreservation table
            for (String roomNumber : availableRoomNumbers) {
                String sqlRoomReservation = "INSERT INTO roomreservation(roomnum, reservationid) VALUES (?, ?)";
                try (PreparedStatement statementRoomReservation = connection.prepareStatement(sqlRoomReservation)) {
                    statementRoomReservation.setString(1, roomNumber);
                    statementRoomReservation.setInt(2, reservationID);
                    statementRoomReservation.executeUpdate();
                    System.out.println("room number: "+roomNumber);
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                        System.out.println("fail to insert into roomreservation table");
                    }
                }
            } else {
                System.out.println("Guest quantity exceeds max guest allowed");
                return "redirect:/guestMakeRoomReservation";
            }

            totalPayment = calculateTotalPayment(availableRoomNumbers, connection);
            String sqlUpdateTotalPayment = "UPDATE reservation SET totalpayment = ? WHERE reservationid = ?";
            try (PreparedStatement statementUpdateTotalPayment = connection.prepareStatement(sqlUpdateTotalPayment)) {
                statementUpdateTotalPayment.setDouble(1, totalPayment);
                statementUpdateTotalPayment.setInt(2, reservationID);
                statementUpdateTotalPayment.executeUpdate();
            }
        }
        else {
            System.out.println("Room not available");
            return "redirect:/guestMakeRoomReservation";
        }

        connection.close();

        //set reservation id into session
        session.setAttribute("reservationID", reservationID);
        }
        System.out.println("reservation date: " + date);

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("reservation date: " + date);
            return "redirect:/index";
        }

        if (addon.equalsIgnoreCase("Yes"))
        return "guest/guestMakeRoomService";
        
        else
        return "guest/guestRoomReservation";
    }
      

  
@GetMapping("/guestMakeRoomService")
public String guestMakeRoomService(HttpSession session) {
  String guestICNumber = (String) session.getAttribute("guestICNumber");
  String reservationID = (String) session.getAttribute("reservationID");
  return "guest/guestMakeRoomService";
}

@GetMapping("/guestMakeEventService")
public String guestMakeEventService(HttpSession session) {
  String guestICNumber = (String) session.getAttribute("guestICNumber");
  String reservationID = (String) session.getAttribute("reservationID");
  return "guest/guestMakeEventService";
}

}
