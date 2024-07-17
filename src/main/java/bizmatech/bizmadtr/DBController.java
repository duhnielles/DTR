package bizmatech.bizmadtr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
            System.out.println(e.getMessage());
        }
    }

    public boolean register(String name, String lastname, String email, String contactno, String password) {
        try {
            String insert = "INSERT INTO bizmadb(name, lastname, email, password, contactno) VALUES('"
                + name + "','" + lastname + "','" + email + "','" + password + "','" + contactno + "')";
            Statement statement = con.createStatement();
            statement.execute(insert);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            String query = "SELECT name, lastname FROM bizmadb";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String firstName = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                users.add(firstName + " " + lastName);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public boolean validateUser(String username, String password) {
    try {
        String query = "SELECT COUNT(*) FROM bizmadb WHERE CONCAT(name, ' ', lastname) = ? AND password = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            return true;
        }
        
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public boolean setTimeIn(String username) {
    try {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String update;
        if (hour < 12) {
            update = "UPDATE bizmadb SET timein = NOW(), date = CURDATE() WHERE CONCAT(name, ' ', lastname) = ?";
        } else {
            update = "UPDATE bizmadb SET timein_pm = NOW(), date = CURDATE() WHERE CONCAT(name, ' ', lastname) = ?";
        }

        PreparedStatement preparedStatement = con.prepareStatement(update);
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



public boolean setTimeOut(String username) {
    try {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String update;
        if (hour < 12) {
            update = "UPDATE bizmadb SET timeout = NOW() WHERE CONCAT(name, ' ', lastname) = ?";
        } else {
            update = "UPDATE bizmadb SET timeout_pm = NOW() WHERE CONCAT(name, ' ', lastname) = ?";
        }

        PreparedStatement preparedStatement = con.prepareStatement(update);
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


public List<UserEntry> getUserEntries(String username) {
    List<UserEntry> entries = new ArrayList<>();
    try {
        String query = "SELECT name, lastname, date, timein, timeout, timein_pm, timeout_pm FROM bizmadb WHERE CONCAT(name, ' ', lastname) = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String lastname = resultSet.getString("lastname");
            Date date = resultSet.getDate("date");
            Time timein = resultSet.getTime("timein");
            Time timeout = resultSet.getTime("timeout");
            Time timein_pm = resultSet.getTime("timein_Pm");
            Time timeout_pm = resultSet.getTime("timeout_Pm");

            UserEntry entry = new UserEntry(name, lastname, date, timein, timeout, timein_pm, timeout_pm);
            entries.add(entry);
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return entries;
}


    
}
