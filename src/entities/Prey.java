package entities;

import genetic.Genome;

import java.awt.*;

public class Prey extends Entities{
    private int inputAmount = 8;
    public Prey() {
        super();
        this.inputAmount = 8;
        this.brain = new Genome(inputAmount, 2, false);
        this.vision = new Vision(50, inputAmount, 0.3);
        this.size.setAll(20.0, 20.0);
        this.setColor(Color.BLUE);
    }

    public Prey(Prey parent) {
        super();
        this.inputAmount = 8;
        this.brain = parent.brain.cloneGenome();
        this.position.setByCoordinate(parent.position);
        this.size.setByCoordinate(parent.size);

        this.vision = new Vision(10, inputAmount, 0.3);
        this.setColor(Color.BLUE);
    }

}
