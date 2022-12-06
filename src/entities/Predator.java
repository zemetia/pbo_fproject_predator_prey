package entities;

import genetic.Genome;

import java.awt.*;

public class Predator extends Entities{
    private final int inputAmount = 10;
    public Predator(){
        super();
        this.brain = new Genome(inputAmount, 2, false);
        this.vision = new Vision(80, inputAmount, 0.3);
        this.size.setAll(30.0, 30.0);
        this.setColor(Color.RED);
    }
}
