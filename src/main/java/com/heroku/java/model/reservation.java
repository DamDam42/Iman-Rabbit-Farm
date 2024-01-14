package com.heroku.java.model;
import java.util.Date;

public class reservation {

    private String reservationID;
    private String guestID;
    private int guestQuantity;
    private int durationOfStay;
    private Date dateStart;
    private Date dateEnd;
    private int totalAdult;
    private int totalKids;
    private String reserveStatus;
    private int totalRoom;
    private String totalPayment;

    public reservation() {

    }

    /**
     * @return String return the reservationID
     */
    public String getReservationID() {
        return reservationID;
    }

    /**
     * @param reservationID the reservationID to set
     */
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    /**
     * @return String return the guestID
     */
    public String getGuestID() {
        return guestID;
    }

    /**
     * @param guestID the guestID to set
     */
    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    /**
     * @return String return the guestQuantity
     */
    public int getGuestQuantity() {
        this.guestQuantity = this.totalAdult + this.totalKids;
        return this.guestQuantity;
    }

    /**
     * @param guestQuantity the guestQuantity to set
     */
    public void setGuestQuantity(int guestQuantity) {
        this.guestQuantity = guestQuantity;
    }

    /**
     * @return String return the durationOfStay
     */
    public int getDurationOfStay() {
        return durationOfStay;
    }

    /**
     * @param durationOfStay the durationOfStay to set
     */
    public void setDurationOfStay(int durationOfStay) {
        this.durationOfStay = durationOfStay;
    }

    /**
     * @return String return the dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart the dateStart to set
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return String return the dateEnd
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd the dateEnd to set
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * @return String return the totalAdult
     */
    public int getTotalAdult() {
        return totalAdult;
    }

    /**
     * @param totalAdult the totalAdult to set
     */
    public void setTotalAdult(int totalAdult) {
        this.totalAdult = totalAdult;
    }

    /**
     * @return String return the totalKids
     */
    public int getTotalKids() {
        return totalKids;
    }

    /**
     * @param totalKids the totalKids to set
     */
    public void setTotalKids(int totalKids) {
        this.totalKids = totalKids;
    }

    /**
     * @return String return the reserveStatus
     */
    public String getReserveStatus() {
        return reserveStatus;
    }

    /**
     * @param reserveStatus the reserveStatus to set
     */
    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    /**
     * @return String return the totalRoom
     */
    public int getTotalRoom() {
        return totalRoom;
    }

    /**
     * @param totalRoom the totalRoom to set
     */
    public void setTotalRoom(int totalRoom) {
        this.totalRoom = totalRoom;
    }

    /**
     * @return String return the totalPayment
     */
    public String getTotalPayment() {
        return totalPayment;
    }

    /**
     * @param totalPayment the totalPayment to set
     */
    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

}
