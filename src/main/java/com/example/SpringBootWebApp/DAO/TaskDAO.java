package com.example.SpringBootWebApp.DAO;

import com.example.SpringBootWebApp.Models.Task;
import com.example.SpringBootWebApp.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDAO {
//    private static final List<Task> db = new ArrayList<>();
    @Autowired
    private static int TASK_COUNT = Task.TASK_COUNT;
    private static Connection connection;


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addTask(Task task) {
        TASK_COUNT++;
        try {
            String SQL = "INSERT INTO Tasks (user_id, task_content, is_done) VALUES ( ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setString(2, task.getTaskContent());
            preparedStatement.setBoolean(3, task.isDone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // метод возвращает все таски конкретного пользователя
    public static List<Task> getTasksByUser(int userId) {
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
    public List<Task> filterTasks(int userId, String filterType) {
        List<Task> allUsersTasks = getTasksByUser(userId);
        switch(filterType) {
            case "all":
                return allUsersTasks;
            case "completed":
                System.out.println("begin filter by completed");
                for(int i = 0; i < allUsersTasks.size(); i++) {
                    if(!allUsersTasks.get(i).isDone()) {
                        allUsersTasks.remove(i);
                        i--;
                    }
                }
                return allUsersTasks;
            case "uncompleted":
                System.out.println("begin filter by uncompleted");
                for(int i = 0; i < allUsersTasks.size(); i++) {
                    if(allUsersTasks.get(i).isDone()) {
                        allUsersTasks.remove(i);
                        i--;
                    }
                }
                return allUsersTasks;
        }
        System.out.println("FORMAT FILTER IS NOT RECOGNIZED !!!!");
        return null;
    }



    public void editTaskContentByUser(int userId, String taskContent, String newTaskContent) {
        try {
            // Подготовка запроса для выборки задач по userId
            String selectSQL = "SELECT * FROM Tasks WHERE user_id = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
                selectStatement.setInt(1, userId);
                ResultSet resultSet = selectStatement.executeQuery();

                // Перебор результатов запроса
                while (resultSet.next()) {
                    // Проверка соответствия taskContent
                    if (taskContent.equals(resultSet.getString("task_content"))) {
                        // Обновление содержимого задачи
                        String updateSQL = "UPDATE Tasks SET task_content = ? WHERE user_id = ? AND task_content = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                            updateStatement.setString(1, newTaskContent);
                            updateStatement.setInt(2, userId);
                            updateStatement.setString(3, taskContent);

                            int rowsAffected = updateStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Содержимое задачи пользователя успешно отредактировано.");
                            } else {
                                System.out.println("Задачи для пользователя не найдены для редактирования.");
                            }
                        }
                        break; // Выход из цикла, так как задача найдена и отредактирована
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteUserTask(int userId, String taskContent) {
        try {
            String SQL = "DELETE FROM Tasks WHERE user_id = ? AND task_content = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, taskContent);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                // Если ни одна строка не была удалена, задача с заданными параметрами не найдена
                System.out.println("Задача не найдена для удаления.");
            } else {
                System.out.println("Задача успешно удалена.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
