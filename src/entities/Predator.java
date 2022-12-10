package entities;

import genetic.Genome;

import java.awt.*;
import java.util.ArrayList;

public class Predator extends Entities{
    private int inputAmount = 10;
    private ArrayList<Predator> population;
    final double radius = 30.0;
    public Predator(ArrayList<Predator> pred){
        super();
        this.population = pred;
        this.brain = new Genome(inputAmount, 2, false);
        this.vision = new Vision(80, inputAmount, 0.3);
        this.size.setAll(radius, radius);
        this.setColor(Color.RED);
    }

    public Predator lahiran() {
        Predator child = new Predator(population);
        child.brain = this.brain.cloneGenome();
        child.position.setByCoordinate(this.position);
        child.vision = new Vision(80, this.inputAmount, 0.3);
        child.generation = this.generation + 1;
        this.children++;

        return child;
    }

    public void eatPrey(Prey prey) {
        double distance = this.centerPosition.determineDistance(prey.centerPosition);
        if (distance < this.radius + prey.radius) {
            this.eaten++;
            System.out.println("kemakan");
        }
    }

    @Override
    public void childUpdate() {
        if(this.position.getPosX() >= 789){
            this.population.add(this.lahiran());
            System.out.println("Lahiran anak");
        }
        //this.eatPrey();

    }
}
