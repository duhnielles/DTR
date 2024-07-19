package bizmatech.bizmadtr;

import java.sql.Time;
import java.util.Date;

/**
 * Represents a user's entry with details about their time in and out.
 */
public class UserEntry {
    private String name;
    private String lastname;
    private Date date;
    private Time timein;
    private Time timeout;
    private Time timein_pm;
    private Time timeout_pm;
    private String idNumber; // This field is now optional.

    // Constructor
    public UserEntry(String name, String lastname, Date date, Time timein, Time timeout, Time timein_pm, Time timeout_pm) {
        this.name = name;
        this.lastname = lastname;
        this.date = date;
        this.timein = timein;
        this.timeout = timeout;
        this.timein_pm = timein_pm;
        this.timeout_pm = timeout_pm;
        this.idNumber = ""; // Default value or modify as needed.
    }

    // Getters
    public String getName() { return name; }
    public String getLastname() { return lastname; }
    public Date getDate() { return date; }
    public Time getTimein() { return timein; }
    public Time getTimeout() { return timeout; }
    public Time getTimeinPm() { return timein_pm; }
    public Time getTimeoutPm() { return timeout_pm; }
    public String getIdNumber() { return idNumber; }
    
    // Setters (optional, if you need to modify fields)
    public void setName(String name) { this.name = name; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setDate(Date date) { this.date = date; }
    public void setTimein(Time timein) { this.timein = timein; }
    public void setTimeout(Time timeout) { this.timeout = timeout; }
    public void setTimeinPm(Time timein_pm) { this.timein_pm = timein_pm; }
    public void setTimeoutPm(Time timeout_pm) { this.timeout_pm = timeout_pm; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
}
