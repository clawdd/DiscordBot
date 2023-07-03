package sql;

import main.MainBot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionHandler {

    public void insertFunction(String userId, String function_name, String function) throws SQLException {
        String checkSQL = "SELECT id FROM functions WHERE userid = ? AND function_name = ?";
        String insertSQL = "INSERT INTO functions (userid, function_name, function) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE functions SET function = ? WHERE id = ?";

        try (PreparedStatement checkStatement = MainBot.INSTANCE.getConnection().prepareStatement(checkSQL);
             PreparedStatement insertStatement = MainBot.INSTANCE.getConnection().prepareStatement(insertSQL);
             PreparedStatement updateStatement = MainBot.INSTANCE.getConnection().prepareStatement(updateSQL)) {

            checkStatement.setString(1, userId);
            checkStatement.setString(2, function_name);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    int functionId = resultSet.getInt("id");

                    updateStatement.setString(1, function);
                    updateStatement.setInt(2, functionId);

                    int updatedRows = updateStatement.executeUpdate();
                    System.out.println(updatedRows > 0 ? "Function updated successfully" : "Update failed");
                } else {

                    insertStatement.setString(1, userId);
                    insertStatement.setString(2, function_name);
                    insertStatement.setString(3, function);

                    int insertedRows = insertStatement.executeUpdate();
                    System.out.println(insertedRows > 0 ? "Function inserted successfully" : "Insertion failed");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert or update function in the database.", e);
        }
    }

    public String getFunctionString(String userId, String function_name) throws SQLException {
        String selectSQL = "SELECT function FROM functions WHERE userid = ? AND function_name = ?";

        try (PreparedStatement selectStatement = MainBot.INSTANCE.getConnection().prepareStatement(selectSQL)) {
            selectStatement.setString(1, userId);
            selectStatement.setString(2, function_name);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("function");
                } else {
                    throw new RuntimeException("Function not found for the given function_name and userId.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch function from the database.", e);
        }
    }
}
