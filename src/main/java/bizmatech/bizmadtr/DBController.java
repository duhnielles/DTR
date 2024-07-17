package bizmatech.bizmadtr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Danielle
 */
public class DBController {
    private Connection con;

    public DBController() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String database = "jdbc:mysql://localhost:3306/dtrdatabase";
            con = DriverManager.getConnection(database, "admin", "admin123");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean register(String name, String lastname, String email, String contactno, String password) {
        String insert = "INSERT INTO bizmadb(name, lastname, email, password, contactno) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(insert)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, contactno);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        String query = "SELECT name, lastname FROM bizmadb";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String firstName = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                users.add(firstName + " " + lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean validateUser(String username, String password) {
        String query = "SELECT COUNT(*) FROM bizmadb WHERE CONCAT(name, ' ', lastname) = ? AND password = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setTimeIn(String username) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String update = hour < 12 ? 
            "UPDATE bizmadb SET timein = NOW(), date = CURDATE() WHERE CONCAT(name, ' ', lastname) = ?" : 
            "UPDATE bizmadb SET timein_pm = NOW(), date = CURDATE() WHERE CONCAT(name, ' ', lastname) = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(update)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setTimeOut(String username) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String update = hour < 12 ? 
            "UPDATE bizmadb SET timeout = NOW() WHERE CONCAT(name, ' ', lastname) = ?" : 
            "UPDATE bizmadb SET timeout_pm = NOW() WHERE CONCAT(name, ' ', lastname) = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(update)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserEntry> getUserEntries(String username) {
        List<UserEntry> entries = new ArrayList<>();
        String query = "SELECT name, lastname, date, timein, timeout, timein_pm, timeout_pm FROM bizmadb WHERE CONCAT(name, ' ', lastname) = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String lastname = resultSet.getString("lastname");
                    Date date = resultSet.getDate("date");
                    Time timein = resultSet.getTime("timein");
                    Time timeout = resultSet.getTime("timeout");
                    Time timein_pm = resultSet.getTime("timein_pm");
                    Time timeout_pm = resultSet.getTime("timeout_pm");

                    UserEntry entry = new UserEntry(name, lastname, date, timein, timeout, timein_pm, timeout_pm);
                    entries.add(entry);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
