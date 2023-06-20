package sql;

import main.MainBot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PointsHanlder {

    public void updatePointsTable(String userId, String testType, String testName, int pointsAchieved, int pointsPossible, double percentageToPass, int numOfTests, int pointsAdmission) {
        try {
            String checkSql = "SELECT COUNT(*) FROM pointscollection WHERE class_name = ? AND test_name = ?";
            String insertSql = "INSERT INTO pointscollection (userid, class_name, test_name, points_archieved, points_possible, percentage_to_pass, num_of_tests, points_for_admission) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement checkStatement = MainBot.INSTANCE.getConnection().prepareStatement(checkSql);
                 PreparedStatement insertStatement = MainBot.INSTANCE.getConnection().prepareStatement(insertSql)) {

                checkStatement.setString(1, testType);
                checkStatement.setString(2, testName);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Duplicate test name found");
                        return; // Stop further execution of the method
                    }
                }

                insertStatement.setString(1, userId);
                insertStatement.setString(2, testType);
                insertStatement.setString(3, testName);
                insertStatement.setInt(4, pointsAchieved);
                insertStatement.setInt(5, pointsPossible);
                insertStatement.setDouble(6, percentageToPass);
                insertStatement.setInt(7, numOfTests);
                insertStatement.setInt(8, pointsAdmission);

                int inserted = insertStatement.executeUpdate();
                if (inserted > 0) {
                    System.out.println("Data updated");
                } else {
                    System.out.println("Update failed");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public TestTypeInformation getTestTypeInformation(String userId, String testType) {
        try {
            String sql = "SELECT userid, points_possible, percentage_to_pass, num_of_tests, points_for_admission FROM pointscollection WHERE userid = ? AND class_name = ?";

            try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(sql)) {
                statement.setString(1, userId);
                statement.setString(2, testType);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String userID = resultSet.getString("userid");
                        int pointsPossible = resultSet.getInt("points_possible");
                        double percentageToPass = resultSet.getDouble("percentage_to_pass");
                        int numOfTests = resultSet.getInt("num_of_tests");
                        int pointsAdmission = resultSet.getInt("points_for_admission");

                        return new TestTypeInformation(userID, testType, pointsPossible, percentageToPass, numOfTests, pointsAdmission);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public int getTotalPoints(String userId, String testType) {
        try {
            String sql = "SELECT SUM(points_archieved) AS total_points FROM pointscollection WHERE userid = ? AND class_name = ?";

            try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(sql)) {
                statement.setString(1, userId);
                statement.setString(2, testType);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("total_points");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public int getTotalPointsPossible(String userId, String testType) {
        try {
            String sql = "SELECT SUM(points_possible) AS total_points FROM pointscollection WHERE userid = ? AND class_name = ?";

            try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(sql)) {
                statement.setString(1, userId);
                statement.setString(2, testType);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("total_points");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public List<String> getAllTestTypes() {
        List<String> testTypes = new ArrayList<>();

        try {
            String sql = "SELECT DISTINCT class_name FROM pointscollection";

            try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String testType = resultSet.getString("class_name");
                    testTypes.add(testType);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return testTypes;
    }

}
