package entities;

import java.util.ArrayList;

public class Vision {
    private ArrayList<Double> result = new ArrayList<>();
    private int maxLength;
    private int amount;
    private double angleArea;

    public Vision(int maxLength, int amount, double angleArea) {
        this.maxLength = maxLength;
        this.amount = amount;
        this.angleArea = angleArea * 2 * Math.PI;
    }

    public void initVision() {
        //pass
    }

    public ArrayList<Double> getResult() {
        return result;
    }
}
