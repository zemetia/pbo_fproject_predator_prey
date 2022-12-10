package entities;

import genetic.Genome;

import java.awt.*;
import java.util.ArrayList;

public class Prey extends Entities{
    private int inputAmount = 8;
    private ArrayList<Prey> population;
    final double radius = 20.0;
    public Prey(ArrayList<Prey> prey) {
        super();
        this.population = prey;
        this.inputAmount = 8;
        this.brain = new Genome(inputAmount, 2, false);
        this.vision = new Vision(50, inputAmount, 0.3);
        this.size.setAll(radius, radius);
        this.setColor(Color.BLUE);
    }

    public Prey(ArrayList<Prey> prey, Prey parent) {
        super();
        this.population = prey;
        this.inputAmount = 8;
        this.brain = parent.brain.cloneGenome();
        this.position.setByCoordinate(parent.position);
        this.size.setByCoordinate(parent.size);

        this.vision = new Vision(10, inputAmount, 0.3);
        this.setColor(Color.BLUE);
    }

}
