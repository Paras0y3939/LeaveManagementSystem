import java.sql.*;
import java.util.Scanner;

public class AdminDashboard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("📋 Leave Request:\n");

            String sql = "SELECT lr.request_id, lr.user_id, u.name AS user_name, lr.start_date, lr.end_date, lr.reason, lr.status " +
             "FROM leave_request lr " +
             "JOIN users u ON lr.user_id = u.user_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int requestId = rs.getInt("request_id");
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                String reason = rs.getString("reason");
                String status = rs.getString("status");

                System.out.println("Request ID: " + requestId);
                System.out.println("User ID: " + userId);
                System.out.println("User Name: " + userName);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);
                System.out.println("Reason: " + reason);
                System.out.println("Status: " + status);
                System.out.println("--------------------------");

                if (status.equalsIgnoreCase("pending")) {
                    System.out.print("Approve or Reject this request (A/R)? ");
                    String input = scanner.nextLine();

                    String newStatus = input.equalsIgnoreCase("A") ? "approved" : "rejected";
                    String updateSql = "UPDATE leave_request SET status = ? WHERE request_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, newStatus);
                    updateStmt.setInt(2, requestId);
                    updateStmt.executeUpdate();

                    System.out.println("✅ Request " + requestId + " has been " + newStatus + "!\n");
                }
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("❌ Error fetching leave requests.");
            e.printStackTrace();
        }
    }
}


