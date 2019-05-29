package vn.com.japfa.esigning_user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AnnualLeave {

    @SerializedName("From1")
    @Expose
    private String from1;
    @SerializedName("From2")
    @Expose
    private String from2;
    @SerializedName("To1")
    @Expose
    private String to1;
    @SerializedName("To2")
    @Expose
    private String to2;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("current_number_days_leave")
    @Expose
    private Float currentNumberDaysLeave;
    @SerializedName("current_year_rest")
    @Expose
    private Float currentYearRest;
    @SerializedName("document_id")
    @Expose
    private Integer documentId;
    @SerializedName("from_time_1")
    @Expose
    private String fromTime1;
    @SerializedName("from_time_2")
    @Expose
    private String fromTime2;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_send")
    @Expose
    private Integer isSend;
    @SerializedName("last_year_rest")
    @Expose
    private Integer lastYearRest;
    @SerializedName("to_time_1")
    @Expose
    private String toTime1;
    @SerializedName("to_time_2")
    @Expose
    private String toTime2;
    @SerializedName("total_current_year")
    @Expose
    private Integer totalCurrentYear;
    @SerializedName("total_days_leave_this_year")
    @Expose
    private Float totalDaysLeaveThisYear;
    @SerializedName("txtDayWillTake1")
    @Expose
    private String txtDayWillTake1;
    @SerializedName("txtDayWillTake2")
    @Expose
    private String txtDayWillTake2;
    @SerializedName("type_of_leave")
    @Expose
    private String typeOfLeave;
    @SerializedName("userLocation")
    @Expose
    private Object userLocation;
    @SerializedName("userName")
    @Expose
    private Object userName;
    @SerializedName("userPosition")
    @Expose
    private Object userPosition;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    /**
     * No args constructor for use in serialization
     *
     */
    public AnnualLeave() {
    }

    /**
     *
     * @param txtDayWillTake1
     * @param toTime2
     * @param fromTime1
     * @param txtDayWillTake2
     * @param fromTime2
     * @param toTime1
     * @param totalDaysLeaveThisYear
     * @param totalCurrentYear
     * @param currentYearRest
     * @param id
     * @param from2
     * @param lastYearRest
     * @param currentNumberDaysLeave
     * @param userId
     * @param userLocation
     * @param typeOfLeave
     * @param to1
     * @param userName
     * @param to2
     * @param isSend
     * @param from1
     * @param createdDate
     * @param documentId
     * @param userPosition
     */
    public AnnualLeave(String from1, String from2, String to1, String to2, String createdDate, Float currentNumberDaysLeave, Float currentYearRest, Integer documentId, String fromTime1, String fromTime2, Integer id, Integer isSend, Integer lastYearRest, String toTime1, String toTime2, Integer totalCurrentYear, Float totalDaysLeaveThisYear, String txtDayWillTake1, String txtDayWillTake2, String typeOfLeave, Object userLocation, Object userName, Object userPosition, Integer userId) {
        super();
        this.from1 = from1;
        this.from2 = from2;
        this.to1 = to1;
        this.to2 = to2;
        this.createdDate = createdDate;
        this.currentNumberDaysLeave = currentNumberDaysLeave;
        this.currentYearRest = currentYearRest;
        this.documentId = documentId;
        this.fromTime1 = fromTime1;
        this.fromTime2 = fromTime2;
        this.id = id;
        this.isSend = isSend;
        this.lastYearRest = lastYearRest;
        this.toTime1 = toTime1;
        this.toTime2 = toTime2;
        this.totalCurrentYear = totalCurrentYear;
        this.totalDaysLeaveThisYear = totalDaysLeaveThisYear;
        this.txtDayWillTake1 = txtDayWillTake1;
        this.txtDayWillTake2 = txtDayWillTake2;
        this.typeOfLeave = typeOfLeave;
        this.userLocation = userLocation;
        this.userName = userName;
        this.userPosition = userPosition;
        this.userId = userId;
    }

    public String getFrom1() {
        return from1;
    }

    public void setFrom1(String from1) {
        this.from1 = from1;
    }

    public String getFrom2() {
        return from2;
    }

    public void setFrom2(String from2) {
        this.from2 = from2;
    }

    public String getTo1() {
        return to1;
    }

    public void setTo1(String to1) {
        this.to1 = to1;
    }

    public String getTo2() {
        return to2;
    }

    public void setTo2(String to2) {
        this.to2 = to2;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Float getCurrentNumberDaysLeave() {
        return currentNumberDaysLeave;
    }

    public void setCurrentNumberDaysLeave(Float currentNumberDaysLeave) {
        this.currentNumberDaysLeave = currentNumberDaysLeave;
    }

    public Float getCurrentYearRest() {
        return currentYearRest;
    }

    public void setCurrentYearRest(Float currentYearRest) {
        this.currentYearRest = currentYearRest;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getFromTime1() {
        return fromTime1;
    }

    public void setFromTime1(String fromTime1) {
        this.fromTime1 = fromTime1;
    }

    public String getFromTime2() {
        return fromTime2;
    }

    public void setFromTime2(String fromTime2) {
        this.fromTime2 = fromTime2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Integer getLastYearRest() {
        return lastYearRest;
    }

    public void setLastYearRest(Integer lastYearRest) {
        this.lastYearRest = lastYearRest;
    }

    public String getToTime1() {
        return toTime1;
    }

    public void setToTime1(String toTime1) {
        this.toTime1 = toTime1;
    }

    public String getToTime2() {
        return toTime2;
    }

    public void setToTime2(String toTime2) {
        this.toTime2 = toTime2;
    }

    public Integer getTotalCurrentYear() {
        return totalCurrentYear;
    }

    public void setTotalCurrentYear(Integer totalCurrentYear) {
        this.totalCurrentYear = totalCurrentYear;
    }

    public Float getTotalDaysLeaveThisYear() {
        return totalDaysLeaveThisYear;
    }

    public void setTotalDaysLeaveThisYear(Float totalDaysLeaveThisYear) {
        this.totalDaysLeaveThisYear = totalDaysLeaveThisYear;
    }

    public String getTxtDayWillTake1() {
        return txtDayWillTake1;
    }

    public void setTxtDayWillTake1(String txtDayWillTake1) {
        this.txtDayWillTake1 = txtDayWillTake1;
    }

    public String getTxtDayWillTake2() {
        return txtDayWillTake2;
    }

    public void setTxtDayWillTake2(String txtDayWillTake2) {
        this.txtDayWillTake2 = txtDayWillTake2;
    }

    public String getTypeOfLeave() {
        return typeOfLeave;
    }

    public void setTypeOfLeave(String typeOfLeave) {
        this.typeOfLeave = typeOfLeave;
    }

    public Object getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Object userLocation) {
        this.userLocation = userLocation;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(Object userPosition) {
        this.userPosition = userPosition;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("from1", from1).append("from2", from2).append("to1", to1).append("to2", to2).append("createdDate", createdDate).append("currentNumberDaysLeave", currentNumberDaysLeave).append("currentYearRest", currentYearRest).append("documentId", documentId).append("fromTime1", fromTime1).append("fromTime2", fromTime2).append("id", id).append("isSend", isSend).append("lastYearRest", lastYearRest).append("toTime1", toTime1).append("toTime2", toTime2).append("totalCurrentYear", totalCurrentYear).append("totalDaysLeaveThisYear", totalDaysLeaveThisYear).append("txtDayWillTake1", txtDayWillTake1).append("txtDayWillTake2", txtDayWillTake2).append("typeOfLeave", typeOfLeave).append("userLocation", userLocation).append("userName", userName).append("userPosition", userPosition).append("userId", userId).toString();
    }

}