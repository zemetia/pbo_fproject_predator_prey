package entities;

import Collection.Coordinate;
import genetic.EdgeHistory;
import genetic.Genome;
import genetic.Rand;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entities {
    protected double energy;
    protected double speed;
    private double direction = 0; //360 degrees convert into a vector (x, y) with sin and cos
    protected Coordinate<Double> position = new Coordinate<Double>(0.0,0.0);
    protected Coordinate<Double> velocity = new Coordinate<Double>(0.0,0.0);
    protected Coordinate<Double> size = new Coordinate<Double>(0.0,0.0);
    protected Coordinate<Double> centerPosition;
    int generation = 0;
    int children = 0;
    int eaten = 0;
    private Color color = Color.LIGHT_GRAY;

    public Genome brain;
    private ArrayList<Double> decision = new ArrayList<>();

    protected Vision vision;

    private Rand rand = new Rand();

    public Entities(){
        //init location
        this.position.setAll(
                rand.get(790),
                rand.get(590)
        );
        energy = 1.2;
        direction = rand.get(360);
        speed = rand.get(5, 15);
        updateVelocityDirection();
    }

    public void think(){
        int max = 0;
        int maxIndex = 0;
        this.decision = this.brain.feedForward(
                this.vision.getResult(getCenterPosition(position.clone(), size.clone()), direction)
        );

    }

    public void see(){
        //pass dulu ye oakwoawkoawk
    }

    public void updateVelocityDirection() {
        double radian = 2 * this.direction * Math.PI; //putaran ke rad = x * 2pi
        this.velocity.setAll(Math.cos(radian) * this.speed, Math.sin(radian) * this.speed);
    }

    public Coordinate<Double> getCenterPosition(Coordinate<Double> newPosition, Coordinate<Double> newSize) {
        newSize.multiply(0.5);
        newPosition.addWithCoordinate(newSize); //coordinat ditengah lingkaran
        return newPosition;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void die() {
        // dont know
    }

    public void update() {
        position.addWithCoordinate(velocity);

        if (position.getPosX() < 0) position.setPosX( 790.0 );
        if (position.getPosX() >= 790) position.setPosX( 0.0 );
        if (position.getPosY() < 0) position.setPosY( 590.0 );
        if (position.getPosY() >= 590) position.setPosY( 0.0 );

        this.centerPosition = this.getCenterPosition(
                position.clone(),
                size.clone()
        );
    }

    public void childUpdate(){
        //pass
    }

    public void paint(Graphics graph) {
        //System.out.println(this.x + " " + this.y);

        update();
        childUpdate();

        this.vision.updatePosition(this.centerPosition);

        direction += rand.get(-0.05, 0.05);
        speed += rand.get(-1, 1);
        speed %= 10;
        updateVelocityDirection();
        this.vision.drawVision(graph, direction * 2 * Math.PI);

        graph.setColor(this.color);
        graph.fillOval(
                position.getPosX().intValue(),
                position.getPosY().intValue(),
                size.getPosX().intValue(),
                size.getPosY().intValue()
        );
    }

    public void setLocation(double x, double y) {
        this.position.setAll(x, y);
    }
}
