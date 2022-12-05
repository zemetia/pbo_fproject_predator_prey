package entities;

import genetic.Genome;

import java.awt.*;

public class Prey extends Entities{
    public Prey() {
        super();
        this.inputAmount = 8;
        this.brain = new Genome(this.inputAmount, 2, false);
        this.vision = new Vision(10, this.inputAmount, 0.3);
        this.size.setAll(20, 20);
        this.setColor(Color.BLUE);
    }

}
