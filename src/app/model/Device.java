package app.model;




public class Device {

    private int id;
    private String sensorName;
    private String event;
    private String serial;
    private int type;
    private  String description;
    private int interval;
    private int user_Id;
    private String fullName;


    public Device() {
    }

    public Device(int id, String serial, int type, int interval, String fullName) {
        this.id = id;
       this.fullName= fullName;
        this.serial = serial;
        this.type = type;
        this.interval = interval;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String username) {
        this.fullName = username;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String name) {
        this.event = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
