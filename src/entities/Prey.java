package entities;

import genetic.Genome;

import java.awt.*;
import java.util.ArrayList;

public class Prey extends Entities implements PreySetting {
    private ArrayList<Entities> population;
    private boolean stunt;
    public Prey(ArrayList<Entities> prey) {
        super();
        this.population = prey;
        this.stunt = false;
        this.brain = new Genome(INPUT_AMOUNT, GEN_OUTPUT, false);
        this.vision = new Vision(INPUT_MAXLENGTH, INPUT_AMOUNT, INPUT_ANGLEAREA);
        this.size.setAll(RADIUS, RADIUS);
        this.setColor(Color.BLUE);
    }

    public Prey lahiran() {
        Prey child = new Prey(population);
        child.brain = this.brain.cloneGenome();
        child.position.setByCoordinate(this.position);
        child.generation = this.generation + 1;
        this.children++;

        return child;
    }

    @Override
    public void childUpdate() {

        if ( energy <= 0 ) {
            this.population.add(this.lahiran());
            energy += 3;
        }

        energy -= 0.001 * Math.abs(speed);
    }

}
