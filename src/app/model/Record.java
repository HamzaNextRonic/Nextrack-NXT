package app.model;

import java.time.Instant;

public class Record {
    private int id;
    private Instant dateRecord;
    private int id_user;
    private int id_device;

    public Record(int id, Instant dateRecord, int id_user, int id_device) {
        this.id = id;
        this.dateRecord = dateRecord;
        this.id_user = id_user;
        this.id_device = id_device;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getDateRecord() {
        return dateRecord;
    }

    public void setDateRecord(Instant dateRecord) {
        this.dateRecord = dateRecord;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_device() {
        return id_device;
    }

    public void setId_device(int id_device) {
        this.id_device = id_device;
    }
}
