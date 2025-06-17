package org.example.serviceusage;

public class EnergyMessage {
    public String type;
    public String association;
    public double kwh;
    public String datetime;

    public String getDatetime() {
        return datetime;
    }

    public double getKwh() {
        return kwh;
    }

    public String getAssociation() {
        return association;
    }

    public String getType() {
        return type;
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

