package entities;

import Collection.Coordinate;
import genetic.Genome;

import java.awt.*;
import java.util.ArrayList;

public class Entities {
    private double energy;
    private double speed = 10;
    private double direction = 0; //360 degrees convert into a vector (x, y) with sin and cos
    protected Coordinate<Double> position = new Coordinate<Double>(0.0,0.0);
    protected Coordinate<Double> velocity = new Coordinate<Double>(0.0,0.0);
    protected Coordinate<Integer> size = new Coordinate<Integer>(0,0);
    private int generation;
    private int children = 0;
    private int eaten = 0;
    private Color color = Color.LIGHT_GRAY;

    private Genome brain = new Genome(8, 2, false);
    private ArrayList<Double> decision = new ArrayList<>();
    private ArrayList<Double> vision = new ArrayList<>();

    public Entities(){
        //init location
        this.position.setAll(
                (Math.random()*790+0),
                (Math.random()*590+0)
        );

        direction = Math.random()*359;
        updateVelocityDirection();
    }

    public void think(){
        int max = 0;
        int maxIndex = 0;
        this.decision = this.brain.feedForward(this.vision);

    }

    public void updateVelocityDirection() {
        double radian = 2 * this.direction * Math.PI; //putaran ke rad = x * 2pi
        this.velocity.setAll(Math.cos(radian) * this.speed, Math.sin(radian) * this.speed);
    }

    public void see(){
        //pass dulu ye oakwoawkoawk
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void die() {
        // dont know
    }

    public void paint(Graphics graph) {
        //System.out.println(this.x + " " + this.y);
        graph.setColor(this.color);
        position.addWithCoordinate(velocity);

        if(position.getPosX() < 0 || position.getPosX() >= 790) {
            velocity.setPosX(velocity.getPosX() * -1);
        }

        //bounce off the top and bottom
        if(position.getPosY() < 0 || position.getPosY() >= 590) {
            velocity.setPosY(velocity.getPosY() * -1);
        }

        graph.fillOval(
                position.getPosX().intValue(),
                position.getPosY().intValue(),
                size.getPosX(),
                size.getPosY()
        );
    }

    public void setLocation(double x, double y) {
        this.position.setAll(x, y);
    }
}
