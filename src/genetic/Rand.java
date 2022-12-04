package genetic;

import java.util.Random;

public class Rand {
    private Random rand = new Random();

    public double get(double from, double to){
        return from + (to - from) * this.rand.nextDouble();
    }
    public double get(double to){
        return to * this.rand.nextDouble();
    }
    public double gaussian(){
        return rand.nextGaussian();
    }
}
