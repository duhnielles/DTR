/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bizmatech.bizmadtr;

/**
 *
 * @author Danielle
 */
public class dtr {
    private String name;
    private String lname;
    private String email;
    private float cnumber;
    private String password;
 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLName() {
        return lname;
    }

    public void setLName(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public float setCnumber(){
        return cnumber;
    }
    
    public void getCnumber(int cnumber){
        this.cnumber = cnumber;
    }
    
    public dtr(String name, String lname, String email, int cnumber, String password) {
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.cnumber = cnumber;
        this.password = password;
    }

    public dtr() {
    }
}
