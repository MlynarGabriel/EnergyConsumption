package org.example.serviceusage;

public class EnergyMessage {
    private String type;
    private String association;
    private double kwh;
    private String datetime;

    public String getType() {
        return type;
    }

    public String getAssociation() {
        return association;
    }

    public double getKwh() {
        return kwh;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s kWh", datetime, type, kwh);
    }
}
