public class LeaveRequest {
    private int requestId;
    private int userId;
    private String userName;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;

    // Constructor
    public LeaveRequest(int requestId, int userId, String userName, String startDate, String endDate, String reason, String status) {
        this.requestId = requestId;
        this.userId = userId;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }

    // Getters
    public int getRequestId() { return requestId; }
    public int getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }

    // Optional: You can add setters if you need to modify values later
}
