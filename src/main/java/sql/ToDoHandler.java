package sql;

import main.MainBot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ToDoHandler {

    public void addTodoItem(String userId, String todo) {
        String sql = "INSERT INTO todo (userid, todo) VALUES (?, ?)";

        try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(sql)) {
            statement.setString(1, userId);
            statement.setString(2, todo);

            int inserted = statement.executeUpdate();
            if (inserted > 0) {
                System.out.println("Todo item added successfully.");
            } else {
                System.out.println("Failed to add todo item.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, String> getTodoEntries(String userId) {
        Map<Integer, String> todoEntries = new HashMap<>();

        String sql = "SELECT idtodo, todo FROM todo WHERE userid = ?";

        try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(sql)) {
            statement.setString(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("idtodo");
                    String todo = resultSet.getString("todo");
                    todoEntries.put(id, todo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return todoEntries;
    }

    public void deleteTodoItem(int idTodo) {
        String sql = "DELETE FROM todo WHERE idtodo = ?";

        try (PreparedStatement statement = MainBot.INSTANCE.getConnection().prepareStatement(sql)) {
            statement.setInt(1, idTodo);

            int deleted = statement.executeUpdate();
            if (deleted > 0) {
                System.out.println("Todo item deleted successfully.");
            } else {
                System.out.println("Failed to delete todo item. No matching ID found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
