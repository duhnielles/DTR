/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bizmatech.bizmadtr;

/**
 *
 * @author Danielle
 */
import java.sql.Time;
import java.util.Date;

public class UserEntry {
    private String name;
    private String lastname;
    private Date date;
    private Time timein;
    private Time timeout;
    private Time timein_pm;
    private Time timeout_pm;

    public UserEntry(String name, String lastname, Date date, Time timein, Time timeout, Time timein_pm, Time timeout_pm) {
        this.name = name;
        this.lastname = lastname; 
        this.date = date;
        this.timein = timein;
        this.timeout = timeout;
    }

    // Getters and Setters
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getdate() {
        return date;
    }

    public void setdate(Date date) {
        this.date = date;
    }

    public Time getTimein() {
        return timein;
    }

    public void setTimein(Time timein) {
        this.timein = timein;
    }

    public Time getTimeout() {
        return timeout;
    }

    public void setTimeout(Time timeout) {
        this.timeout = timeout;
    }
    
    public Time getTimein_Pm() {
        return timein_pm;
    }

    public void setTimein_Pm(Time timein_Pm) {
        this.timein_pm = timein_pm;
    }
    
    public Time getTimeout_Pm() {
        return timeout_pm;
    }

    public void setTimeout_Pm(Time timeout_pm) {
        this.timeout_pm = timeout_pm;
    }
}

