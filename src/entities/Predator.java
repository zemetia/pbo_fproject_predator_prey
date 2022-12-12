package entities;

import genetic.Genome;

import java.awt.*;
import java.util.ArrayList;

public class Predator extends Entities implements PredatorSetting {
    private ArrayList<Entities> population;
    public Predator(ArrayList<Entities> pop){
        super();
        this.population = pop;
        this.brain = new Genome(INPUT_AMOUNT, GEN_OUTPUT, false);
        this.vision = new Vision(INPUT_MAXLENGTH, INPUT_AMOUNT, INPUT_ANGLEAREA);
        this.size.setAll(RADIUS, RADIUS);
        this.setColor(Color.RED);
    }

    public Predator lahiran() {
        Predator child = new Predator(population);
        child.brain = this.brain.cloneGenome();
        child.position.setByCoordinate(this.position);
        child.vision = new Vision(INPUT_MAXLENGTH, INPUT_AMOUNT, INPUT_ANGLEAREA);
        child.generation = this.generation + 1;
        this.children++;

        return child;
    }

    public boolean eatPrey(Prey prey) {
        double distance = this.centerPosition.determineDistance(prey.centerPosition);
        if (distance < (RADIUS + prey.radius) / 2 ) {
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
