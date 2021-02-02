package app.model;


import java.util.Date;

public class User {

    private int Id;
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String status;
    private String lastCctn;
    private int type;
    private String region;
    private String province;


    public User() {
    }

    public User(int id, String username, String password, String fname, String lname, String status, String lastCctn, int type, String region, String province) {
        Id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.status = status;
        this.lastCctn = lastCctn;
        this.type = type;
        this.region = region;
        this.province = province;
    }

    public User(int id, String username, String password, String fname, String lname, String status, String lastCctn, int type) {
        Id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.status = status;
        this.lastCctn = lastCctn;
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastCctn() {
        return this.lastCctn;
    }

    public void setLastCctn(String lastConnecton) {
        this.lastCctn = lastConnecton;
    }





    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
