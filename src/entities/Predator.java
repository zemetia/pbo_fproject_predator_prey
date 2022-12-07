package entities;

import genetic.Genome;

import java.awt.*;
import java.util.ArrayList;

public class Predator extends Entities{
    private int inputAmount = 10;
    private ArrayList<Predator> population;
    public Predator(ArrayList<Predator> pred){
        super();
        this.population = pred;
        this.brain = new Genome(inputAmount, 2, false);
        this.vision = new Vision(80, inputAmount, 0.3);
        this.size.setAll(30.0, 30.0);
        this.setColor(Color.RED);
    }

    public Predator(ArrayList<Predator> pred, Predator parent) {
        super();
        this.population = pred;
        this.brain = parent.brain.cloneGenome();
        this.position.setByCoordinate(parent.position);
        this.size.setByCoordinate(parent.size);

        this.vision = new Vision(80, inputAmount, 0.3);
        this.setColor(Color.RED);
    }

    public Predator lahiran() {
        return new Predator(population, this);
    }

    @Override
    public void childUpdate() {
        if(this.position.getPosX() >= 789){
            this.population.add(this.lahiran());
            System.out.println("Lahiran anak");
        }

    }
}
