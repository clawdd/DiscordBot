package sql;

import main.MainBot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ReminderHandler {

    public void updateReminderTable(String className, String assignmentType, Date date, String userId) throws SQLException {
        try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(
                "INSERT INTO reminder (userid, class_name, assignment_type, date) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, userId);
            statement.setString(2, className);
            statement.setString(3, assignmentType);
            statement.setObject(4, date);

            int inserted = statement.executeUpdate();
            if (inserted > 0) {
                System.out.println("Data updated");
            } else {
                System.out.println("Update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert data into the table");
        }
    }

    public Assignment findClosestAssignment(String userId) throws SQLException {
        try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(
                "SELECT idReminder, userid, class_name, assignment_type, date FROM reminder " +
                        "WHERE userid = ? " +
                        "ORDER BY ABS(DATEDIFF(date, CURDATE())) ASC LIMIT 1")) {

            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("idReminder");
                    String className = resultSet.getString("class_name");
                    String assignmentType = resultSet.getString("assignment_type");
                    Date date = resultSet.getTimestamp("date");

                    return new Assignment(id, className, assignmentType, date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
