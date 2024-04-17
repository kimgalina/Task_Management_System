/**
 * The `TaskDAO` class is responsible for interacting with the database to perform CRUD operations
 * related to tasks. It uses PostgreSQL as the underlying database and is designed as a Spring
 * component.
 *
 * This class provides methods for adding, retrieving, editing, and deleting tasks in the database.
 * Additionally, it includes static initialization to set up the database connection during class
 * loading.
 *
 * @author [Isa Kasymbek uulu]
 * @version 1.0
 */

package com.example.SpringBootWebApp.DAO;

import com.example.SpringBootWebApp.Models.Task;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDAO {

    private final DatabaseManager databaseManager;
    private final Connection connection;

    public TaskDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        connection = databaseManager.getConnection();
    }


    /**
     * Adds a new task to the database.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        try {
            String SQL = "INSERT INTO Tasks (user_id, task_content, is_done) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setString(2, task.getTaskContent());
            preparedStatement.setBoolean(3, task.isDone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all tasks for a specific user from the database.
     *
     * @param userId The ID of the user.
     * @return A list of tasks associated with the given user.
     */
    public List<Task> getTasksByUser(int userId) {
        List<Task> usersTask = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Tasks";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                if(resultSet.getInt("user_id") == userId) {
                    Task task = new Task(null ,userId);
                    task.setTaskContent(resultSet.getString("task_content"));
                    task.setDone(resultSet.getBoolean("is_done"));
                    usersTask.add(task);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usersTask;
    }

    /**
     * Filters tasks based on the specified criteria and returns the filtered list.
     *
     * @param userId      An integer representing the user ID for whom tasks need to be filtered.
     * @param filterType  A string specifying the type of filter to be applied ("all", "completed", or "uncompleted").
     * @return            A list of tasks that satisfy the specified filter criteria.
     */
    public List<Task> filterTasks(int userId, String filterType) {
        List<Task> allUsersTasks = getTasksByUser(userId);

        // Switch statement to apply different filters based on filterType
        switch (filterType) {
            case "all":
                return allUsersTasks;
            case "completed":
                System.out.println("Begin filter by completed");
                for (int i = 0; i < allUsersTasks.size(); i++) {
                    if (!allUsersTasks.get(i).isDone()) {
                        allUsersTasks.remove(i);
                        i--;
                    }
                }
                return allUsersTasks;
            case "uncompleted":
                System.out.println("Begin filter by uncompleted");
                for (int i = 0; i < allUsersTasks.size(); i++) {
                    if (allUsersTasks.get(i).isDone()) {
                        allUsersTasks.remove(i);
                        i--;
                    }
                }
                return allUsersTasks;
        }

        // If the filterType is not recognized, print an error message
        System.out.println("FORMAT FILTER IS NOT RECOGNIZED !!!!");
        return null;
    }

    /**
     * Changes the status of a task for a given user.
     *
     * @param userId      An integer representing the user ID for whom the task status needs to be updated.
     * @param taskContent A string representing the content of the task for which the status needs to be updated.
     * @param isDone      A boolean value indicating the new status of the task (true for completed, false for uncompleted).
     */
    public void changeTaskStatus(int userId, String taskContent, boolean isDone) {
        try {
            // SQL update statement to change the task status in the database
            String updateSQL = "UPDATE Tasks SET is_done = ? WHERE user_id = ? AND task_content = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                updateStatement.setBoolean(1, isDone);
                updateStatement.setInt(2, userId);
                updateStatement.setString(3, taskContent);

                int rowsAffected = updateStatement.executeUpdate();

                // Print a message indicating the success or failure of the task status update
                if (rowsAffected > 0) {
                    System.out.println("Task status successfully updated");
                } else {
                    System.out.println("Task status NOT updated. Error occurred.");
                }
            }
        } catch (SQLException e) {
            // Throw a runtime exception if there is an issue with the SQL operation
            throw new RuntimeException(e);
        }
    }


    /**
     * Edits the content of a task for a specific user in the database.
     *
     * @param userId The ID of the user.
     * @param taskContent The current content of the task.
     * @param newTaskContent The new content to be set for the task.
     */
    public void editTaskContentByUser(int userId, String taskContent, String newTaskContent) {
        try {
            // Prepare a query to select tasks by userId
            String selectSQL = "SELECT * FROM Tasks WHERE user_id = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
                selectStatement.setInt(1, userId);
                ResultSet resultSet = selectStatement.executeQuery();

                // Iterate through the results
                while (resultSet.next()) {
                    // Check if taskContent matches
                    if (taskContent.equals(resultSet.getString("task_content"))) {
                        // Update the task content
                        String updateSQL = "UPDATE Tasks SET task_content = ? WHERE user_id = ? AND task_content = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                            updateStatement.setString(1, newTaskContent);
                            updateStatement.setInt(2, userId);
                            updateStatement.setString(3, taskContent);

                            int rowsAffected = updateStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Task content for the user successfully edited.");
                            } else {
                                System.out.println("No tasks found for the user to edit.");
                            }
                        }
                        break; // Exit the loop as the task is found and edited
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a task for a specific user from the database.
     *
     * @param userId The ID of the user.
     * @param taskContent The content of the task to be deleted.
     */
    public void deleteUserTask(int userId, String taskContent) {
        try {
            String SQL = "DELETE FROM Tasks WHERE user_id = ? AND task_content = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, taskContent);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                // If no rows were deleted, the task with the specified parameters was not found
                System.out.println("Task not found for deletion.");
            } else {
                System.out.println("Task successfully deleted.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
