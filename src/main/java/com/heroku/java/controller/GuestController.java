package com.heroku.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.java.model.*;

import jakarta.servlet.http.HttpSession;

import java.sql.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;

import java.util.List;

@Controller
public class GuestController {
    private final DataSource dataSource;

    @Autowired
    public GuestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // original syahir punyer, guna session
    // @GetMapping("/managerAddRoom")
    // public String managerAddRoom(HttpSession session) {
    // // int roomNumber = (int) session.getAttribute("roomNumber");
    // // System.out.println("roomNumber id :" + roomNumber);
    // return "manager/managerAddRoom";
    // }

    @GetMapping("/managerGuestList")
    public String managerGuestList(Model model) {

        List<guest> guests = new ArrayList<guest>();
        // Retrieve the logged-in room's role from the session (syahir punya nih)
        // String staffsrole = (String) session.getAttribute("staffsrole");
        // System.out.println("staffrole managerRoomList : " + staffsrole);
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT guesticnumber, guestname, guestphonenumber, guestgender, guestreligion, guestrace, guestaddress, guestemail, guestpassword FROM public.guest order by guestname"; // ni
            final var statement = connection.prepareStatement(sql);
            // statement.setString(1, "baker"); (syahir punya nih)
            final var resultSet = statement.executeQuery();
            System.out.println("pass try managerGuestList >>>>>");

            while (resultSet.next()) {
                String guestName = resultSet.getString("guestName");
                String guestPhoneNumber = resultSet.getString("guestPhoneNumber");
                String guestICNumber = resultSet.getString("guestICNumber");
                String guestGender = resultSet.getString("guestGender");
                String guestReligion = resultSet.getString("guestReligion");
                String guestRace = resultSet.getString("guestRace");
                String guestAddress = resultSet.getString("guestAddress");
                String guestEmail = resultSet.getString("guestEmail");
                String guestPassword = resultSet.getString("guestPassword");

                // System.out.println("room number" + roomNum);

                guest guest = new guest();
                guest.setGuestName(guestName);
                guest.setGuestPhoneNumber(guestPhoneNumber);
                guest.setGuestICNumber(guestICNumber);
                guest.setGuestGender(guestGender);
                guest.setGuestRace(guestRace);
                guest.setGuestReligion(guestReligion);
                guest.setGuestAddress(guestAddress);
                guest.setGuestEmail(guestEmail);
                guest.setGuestPassword(guestPassword);

                guests.add(guest);
                model.addAttribute("guests", guests);
                // model.addAttribute("isAdmin", staffsrole != null &&
                // staffsrole.equals("admin")); // Add isAdmin flag to the modelF (syahir punya
                // gak)

            }

            connection.close();

            return "manager/managerGuestList";
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as desired (e.g., show an error message)
            return "error";
        }

    }

    @PostMapping("/guestRegister")
    public String guestRegister(@ModelAttribute("guestRegister") guest guest) {

        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO public.guest(guesticnumber, guestname, guestphonenumber, guestgender, guestreligion, guestrace, guestaddress, guestemail, guestpassword) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            final var statement = connection.prepareStatement(sql);

            String guestName = guest.getGuestName();
            String guestPhoneNumber = guest.getGuestPhoneNumber();
            String guestICNumber = guest.getGuestICNumber();
            String guestGender = guest.getGuestGender();
            String guestReligion = guest.getGuestReligion();
            String guestRace = guest.getGuestRace();
            String guestAddress = guest.getGuestAddress();
            String guestEmail = guest.getGuestEmail();
            String guestPassword = guest.getGuestPassword();


            statement.setString(1, guestICNumber);
            statement.setString(2, guestName);
            statement.setString(3, guestPhoneNumber);
            statement.setString(4, guestGender);
            statement.setString(5, guestReligion);
            statement.setString(6, guestRace);
            statement.setString(7, guestAddress);
            statement.setString(8, guestEmail);
            statement.setString(9, guestPassword);
            statement.executeUpdate();

            System.out.println("guest IC : " + guestICNumber);
            // System.out.println("type : "+protype);
            // System.out.println("product price : RM"+proprice);
            // System.out.println("proimg: "+proimgs.getBytes());

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index";
        }
        return "redirect:/guestLogin";
    }

    @GetMapping("/managerViewGuest")
    public String managerViewGuest(@RequestParam("guestICNumber") String guestICNumber, Model model) {
        System.out.println("IC Number : " + guestICNumber);
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT guesticnumber, guestname, guestphonenumber, guestgender, guestreligion, guestrace, guestaddress, guestemail, guestpassword FROM public.guest WHERE guesticnumber LIKE ?";
            final var statement = connection.prepareStatement(sql);
            statement.setString(1, guestICNumber);
            final var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String guestName = resultSet.getString("guestName");
                String guestPhoneNumber = resultSet.getString("guestPhoneNumber");
                String guestGender = resultSet.getString("guestGender");
                String guestReligion = resultSet.getString("guestReligion");
                String guestRace = resultSet.getString("guestRace");
                String guestAddress = resultSet.getString("guestAddress");
                String guestEmail = resultSet.getString("guestEmail");
                String guestPassword = resultSet.getString("guestPassword");

                guest guest = new guest();

                guest.setGuestName(guestName);
                guest.setGuestPhoneNumber(guestPhoneNumber);
                guest.setGuestICNumber(guestICNumber);
                guest.setGuestGender(guestGender);
                guest.setGuestRace(guestRace);
                guest.setGuestReligion(guestReligion);
                guest.setGuestAddress(guestAddress);
                guest.setGuestEmail(guestEmail);
                guest.setGuestPassword(guestPassword);
                model.addAttribute("guest", guest);

                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "manager/managerViewGuest";
    }

    @GetMapping("/guestProfile")
    public String guestProfile(HttpSession session, Model model) {
        String guestICNumber = (String) session.getAttribute("guestICNumber");
        System.out.println("IC Number : " + guestICNumber);
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT guesticnumber, guestname, guestphonenumber, guestgender, guestreligion, guestrace, guestaddress, guestemail, guestpassword FROM public.guest WHERE guesticnumber LIKE ?";
            final var statement = connection.prepareStatement(sql);
            statement.setString(1, guestICNumber);
            final var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String guestName = resultSet.getString("guestName");
                String guestPhoneNumber = resultSet.getString("guestPhoneNumber");
                String guestGender = resultSet.getString("guestGender");
                String guestReligion = resultSet.getString("guestReligion");
                String guestRace = resultSet.getString("guestRace");
                String guestAddress = resultSet.getString("guestAddress");
                String guestEmail = resultSet.getString("guestEmail");
                String guestPassword = resultSet.getString("guestPassword");

                guest guest = new guest();
                
                guest.setGuestName(guestName);
                guest.setGuestPhoneNumber(guestPhoneNumber);
                guest.setGuestICNumber(guestICNumber);
                guest.setGuestGender(guestGender);
                guest.setGuestRace(guestRace);
                guest.setGuestReligion(guestReligion);
                guest.setGuestAddress(guestAddress);
                guest.setGuestEmail(guestEmail);
                guest.setGuestPassword(guestPassword);
                
                model.addAttribute("guest", guest);

                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "manager/guestProfile";
    }

    @GetMapping("/guestUpdate")
    public String managerUpdateRoom(HttpSession session, Model model) {
        String guestICNumber = (String) session.getAttribute("guestICNumber");
        System.out.println("IC Number : " + guestICNumber);
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT guesticnumber, guestname, guestphonenumber, guestgender, guestreligion, guestrace, guestaddress, guestemail, guestpassword FROM public.guest WHERE guesticnumber LIKE ?";
            final var statement = connection.prepareStatement(sql);
            statement.setString(1, guestICNumber);
            final var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String guestName = resultSet.getString("guestName");
                String guestPhoneNumber = resultSet.getString("guestPhoneNumber");
                String guestGender = resultSet.getString("guestGender");
                String guestReligion = resultSet.getString("guestReligion");
                String guestRace = resultSet.getString("guestRace");
                String guestAddress = resultSet.getString("guestAddress");
                String guestEmail = resultSet.getString("guestEmail");
                String guestPassword = resultSet.getString("guestPassword");

                guest guest = new guest();
                guest.setGuestName(guestName);
                guest.setGuestPhoneNumber(guestPhoneNumber);
                guest.setGuestICNumber(guestICNumber);
                guest.setGuestGender(guestGender);
                guest.setGuestRace(guestRace);
                guest.setGuestReligion(guestReligion);
                guest.setGuestAddress(guestAddress);
                guest.setGuestEmail(guestEmail);
                guest.setGuestPassword(guestPassword);
                model.addAttribute("guest", guest);

                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "guest/guestUpdate";
    }

    @PostMapping("/guestUpdate")
    public String guestUpdate1(HttpSession session, @ModelAttribute("guestUpdate1") guest guest, Model model) {
        String guestICNumber = (String) session.getAttribute("guestICNumber");
        System.out.println("pass here <<<<<<<");
        try {
            Connection connection = dataSource.getConnection();
            String sql = "UPDATE public.guest SET guestname=?, guestphonenumber=?, guestgender=?, guestreligion=?, guestrace=?, guestaddress=?, guestemail=?, guestpassword=? WHERE guesticnumber=?";
            final var statement = connection.prepareStatement(sql);

            String guestName = guest.getGuestName();
            String guestPhoneNumber = guest.getGuestPhoneNumber();
            String guestGender = guest.getGuestGender();
            String guestReligion = guest.getGuestReligion();
            String guestRace = guest.getGuestRace();
            String guestAddress = guest.getGuestAddress();
            String guestEmail = guest.getGuestEmail();
            String guestPassword = guest.getGuestPassword();


            statement.setString(1, guestName);
            statement.setString(2, guestPhoneNumber);
            statement.setString(3, guestGender);
            statement.setString(4, guestReligion);
            statement.setString(5, guestRace);
            statement.setString(6, guestAddress);
            statement.setString(7, guestEmail);
            statement.setString(8, guestPassword);
            statement.setString(9, guestICNumber);
            statement.setString(10, guestICNumber);
            statement.executeUpdate();

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/guestProfile";
    }
}

/*
 * delete controller
 * 
 * @GetMapping("/deletestaff")
 * public String deleteProfileCust(HttpSession session, Model model) {
 * String fullname = (String) session.getAttribute("roomType");
 * int userid = (int) session.getAttribute("roomNum");
 * 
 * if (fullname != null) {
 * try (Connection connection = dataSource.getConnection()) {
 * 
 * // Delete user record
 * final var deleteStaffStatement =
 * connection.prepareStatement("DELETE FROM staffs WHERE roomNum=?");
 * deleteStaffStatement.setInt(1, userid);
 * int userRowsAffected = deleteStaffStatement.executeUpdate();
 * 
 * if (userRowsAffected > 0) {
 * // Deletion successful
 * // You can redirect to a success page or perform any other desired actions
 * 
 * session.invalidate();
 * connection.close();
 * return "redirect:/";
 * } else {
 * // Deletion failed
 * connection.close();
 * System.out.println("Delete Failed");
 * return "admin/deletestaff";
 * 
 * }
 * } catch (SQLException e) {
 * // Handle any potential exceptions (e.g., log the error, display an error
 * page)
 * e.printStackTrace();
 * 
 * // Deletion failed
 * // You can redirect to an error page or perform any other desired actions
 * System.out.println("Error");
 * }
 * }
 * // Username is null or deletion failed, handle accordingly (e.g., redirect to
 * an
 * // error page)
 * return "staff/stafforder";
 * }
 */
