package entities;

import java.awt.*;

public class Entities {
    private double energy;
    private double speed = 10;
    protected int x, y; // vector y location of this entity
    protected int h, w;
    private double direction; //360 degrees convert into a vector (x, y) with sin and cos
    private int generation;
    private int children = 0;
    private int eaten = 0;
    private Color color = Color.LIGHT_GRAY;

    private int vx, vy;

    public Entities(){
        //init location
        setLocation(
                (int)(Math.random()*790+0),
                (int)(Math.random()*590+0)
        );

        direction = Math.random()*359;
        vx = calcLoc('x');
        vy = calcLoc('y');

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
        this.x += vx;
        this.y += vy;

        if(x < 0 || x >= 790) {
            vx *= -1;
        }

        //bounce off the top and bottom
        if(y < 0 || y >= 590) {
            vy *= -1;
        }


        graph.fillOval(this.x, this.y, this.w, this.h);
    }

    public int calcLoc(char loc){
        double rad = Math.toRadians(this.direction);
        if(loc == 'x')
            return (int)(Math.cos(rad) * this.speed);
        else if(loc == 'y')
            return (int)(Math.sin(rad) * this.speed);
        return 0;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
