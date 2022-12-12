package entities;

import genetic.Genome;

import java.awt.*;
import java.util.ArrayList;

public class Predator extends Entities{
    private int inputAmount = 10;
    private ArrayList<Entities> population;
    final double radius = 30.0;
    public Predator(ArrayList<Entities> pred){
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

    public boolean eatPrey(Prey prey) {
        double distance = this.centerPosition.determineDistance(prey.centerPosition);
        if (distance < (this.radius + prey.radius) / 2 ) {
            this.eaten++;
            this.energy++;
            System.out.println("kemakan" + this.eaten);
            return true;
        }
        return false;
    }

    @Override
    public void childUpdate() {
        if(this.energy == 3){
            this.population.add(this.lahiran());
            System.out.println("Lahiran anak");
            this.energy = 0;
        }

        for(Entities entity: population){
            if(entity instanceof Prey)
                if( this.eatPrey( (Prey)entity ) )
                    population.remove(entity);
        }
        //this.eatPrey();

    }
}
