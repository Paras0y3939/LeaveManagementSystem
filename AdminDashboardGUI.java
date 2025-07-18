import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminDashboardGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton approveButton, rejectButton;

    public AdminDashboardGUI() {
        setTitle("Admin Dashboard - Leave Management");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"Request ID", "User ID", "Name", "Start Date", "End Date", "Reason", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadLeaveRequests();

        approveButton.addActionListener(e -> updateStatus("Approved"));
        rejectButton.addActionListener(e -> updateStatus("Rejected"));
    }

    private void loadLeaveRequests() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT lr.request_id, lr.user_id, u.name, lr.start_date, lr.end_date, lr.reason, lr.status " +
                         "FROM leave_request lr JOIN users u ON lr.user_id = u.user_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("request_id"),
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getString("reason"),
                    rs.getString("status")
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    private void updateStatus(String newStatus) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to " + newStatus.toLowerCase());
            return;
        }

        int requestId = (int) model.getValueAt(row, 0);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE leave_request SET status = ? WHERE request_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, requestId);
            ps.executeUpdate();

            model.setValueAt(newStatus, row, 6);
            JOptionPane.showMessageDialog(this, "Request " + requestId + " " + newStatus.toLowerCase() + " successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboardGUI().setVisible(true);
        });
    }
}
