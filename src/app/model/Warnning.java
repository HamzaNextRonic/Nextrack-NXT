package app.model;

import java.time.Instant;

public class Warnning {
    private int id;
    private float temperature;
    private Instant timeEvent;
    private int id_device;
    private int id_record;

    public Warnning() {
    }

    public Warnning(int id, float temperature, int id_device, int id_record,Instant timeEvent) {
        this.id = id;
        this.temperature = temperature;
        this.id_device = id_device;
        this.id_record = id_record;
        this.timeEvent = timeEvent;
    }

    public Instant getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(Instant timeEvent) {
        this.timeEvent = timeEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getId_device() {
        return id_device;
    }

    public void setId_device(int id_device) {
        this.id_device = id_device;
    }

    public int getId_record() {
        return id_record;
    }

    public void setId_record(int id_record) {
        this.id_record = id_record;
    }
}
