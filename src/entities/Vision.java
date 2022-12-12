package entities;

import Collection.Coordinate;

import java.awt.*;
import java.util.ArrayList;

public class Vision {
    private ArrayList<Double> result = new ArrayList<>();
    private int maxLength;
    private int amount;
    private double angleArea;
    private Coordinate<Double> centerPosition;
    private ArrayList< Coordinate<Double> > visioning = new ArrayList<>();

    public Vision(int maxLength, int amount, double angleArea) {
        this.maxLength = maxLength;
        this.amount = amount;
        this.angleArea = angleArea * 2 * Math.PI;
    }

    public void updatePosition(Coordinate<Double> position) {
        centerPosition = position;
    }

    public ArrayList<Double> getResult(Coordinate<Double> centerPosition, double direction) {
        return new ArrayList<Double>();
    }

    public void drawVision(Graphics graph, double direction) {
        graph.setColor(Color.BLACK);

        double r = angleArea/amount;
        double ap = angleArea/2;
        Coordinate<Double> temp = new Coordinate<Double>(0.0, 0.0);

        for (double i = 0; i <= angleArea; i += r) {
            temp.setAll(
                    Math.cos(i-ap+direction) * maxLength,
                    Math.sin(i-ap+direction) * maxLength
            );
            temp.addWithCoordinate(centerPosition);
            graph.drawLine(
                    centerPosition.getPosX().intValue(),
                    centerPosition.getPosY().intValue(),
                    temp.getPosX().intValue(),
                    temp.getPosY().intValue()
            );
        }
    }


}
