package org.example.energyconsumption.entity;

public class EnergyConsumption {

    float produced; //kwH
    float used; //kwH
    float grid_used; //kwH


    public EnergyConsumption(float produced, float used, float grid_used) {
        this.produced = produced;
        this.used = used;
        this.grid_used = grid_used;
    }

    public float getProduced() {
        return produced;
    }

    public float getUsed() {
        return used;
    }
}
