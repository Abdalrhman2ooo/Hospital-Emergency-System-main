package hospital.CaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CaseStatusUpdaterListener implements ServletContextListener {
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new CaseStatusUpdateTask(), 0, 60 * 60 * 1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) {
            timer.cancel();
        }
    }

    private class CaseStatusUpdateTask extends TimerTask {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "admin";

        @Override
        public void run() {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String updateQuery = "UPDATE cases SET status = 'Canceled' WHERE status = 'New' AND TIMESTAMPDIFF(HOUR, start_date, ?) >= 1";
                try (PreparedStatement pst = conn.prepareStatement(updateQuery)) {
                    pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    int rowsAffected = pst.executeUpdate();
                    System.out.println("Updated " + rowsAffected + " cases to 'Canceled'");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
