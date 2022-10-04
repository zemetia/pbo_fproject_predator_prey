package genetic;

public class EdgeGen {
    Node fromNode;
    Node toNode;
    double weight;
    boolean enabled = true;
    private int innovation;

    public EdgeGen (Node from, Node to, double weight, int innovation) {
        this.fromNode = from;
        this.toNode = to;
        this.weight = weight;
        this.innovation = innovation;
    }

    public int getInnovationNo(){
        return this.innovation;
    }

    public void mutateWeight() {
        //double rand2 = random(1);

        /*
        if (rand2 < 0.1)
            this.weight = random(-1, 1);
        else {
            this.weight += (randomGaussian() / 50);
            this.weight = this.weight > 1? 1 : this.weight < -1? -1:this.weight;
        }
         */
    }

    public EdgeGen clone(Node from, Node to) {
        EdgeGen clone = new EdgeGen(from, to, this.weight, this.innovation);
        clone.enabled = this.enabled;
        return clone;
    }
}
