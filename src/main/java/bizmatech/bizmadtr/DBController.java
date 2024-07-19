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

    public boolean register(String name, String lastname, String email, String contactno, String idNumber) {
        String insert = "INSERT INTO bizmadb(name, lastname, email, contactno, id_number) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(insert)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, contactno);
            preparedStatement.setString(5, idNumber);
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
    

    public boolean validateUser(String userId) {
        String query = "SELECT * FROM bizmadb WHERE user_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, userId); // Set the user_id as a String
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a matching row is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean setTimeIn(String userId) {
        if (!getUserId(userId)) return false;

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String insert;
        if (hour < 12) {
            insert = "INSERT INTO attendance(user_id, date, timein) VALUES (?, CURDATE(), NOW()) ON DUPLICATE KEY UPDATE timein = NOW()";
        } else {
            insert = "INSERT INTO attendance(user_id, date, timein_pm) VALUES (?, CURDATE(), NOW()) ON DUPLICATE KEY UPDATE timein_pm = NOW()";
        }

        try (PreparedStatement preparedStatement = con.prepareStatement(insert)) {
            preparedStatement.setString(1, userId); // Use user_id as String
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    public boolean setTimeOut(String userId) {
        if (!getUserId(userId)) return false;

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String update;
        if (hour < 12) {
            update = "UPDATE attendance SET timeout = NOW() WHERE user_id = ? AND date = CURDATE()";
        } else {
            update = "UPDATE attendance SET timeout_pm = NOW() WHERE user_id = ? AND date = CURDATE()";
        }

        try (PreparedStatement preparedStatement = con.prepareStatement(update)) {
            preparedStatement.setString(1, userId); // Use user_id as String
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    private boolean getUserId(String userId) {
        String query = "SELECT COUNT(*) FROM bizmadb WHERE user_id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if user_id is not found
    }


    public List<UserEntry> getUserEntries(String userId) throws SQLException {
    List<UserEntry> entries = new ArrayList<>();
    String query = "SELECT b.name, b.lastname, a.date, a.timein, a.timeout, a.timein_pm, a.timeout_pm " +
                   "FROM attendance a JOIN bizmadb b ON a.user_id = b.user_id " +
                   "WHERE b.user_id = ?"; 
    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
        preparedStatement.setString(1, userId); 
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
