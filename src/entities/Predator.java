package entities;

import genetic.Genome;

import java.awt.*;

public class Predator extends Entities{
    public Predator(){
        super();
        this.inputAmount = 8;
        this.brain = new Genome(this.inputAmount, 2, false);
        this.vision = new Vision(10, this.inputAmount, 0.3);
        this.size.setAll(30, 30);
        this.setColor(Color.RED);
    }
}
