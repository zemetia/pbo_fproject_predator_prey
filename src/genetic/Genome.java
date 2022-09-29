package genetic;

import java.util.ArrayList;

public class Genome {
    private ArrayList<EdgeGen> genes = new ArrayList<EdgeGen>();
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private int inputs = inputs;
    private int outputs = outputs;
    private int layers = 2;
    private int nextNode = 0;
    private ArrayList<Node> network = new ArrayList<Node>();
    private int biasNode;

    public Genome(int inputs, int output, boolean crossover) {
        this.inputs = inputs;
        this.outputs = outputs;

        if(crossover) return;

        for (int i = 0; i < this.inputs; i++) {
            this.nodes.add(new Node(i));
            this.nextNode++;
            this.nodes[i].layer = 0;
        }

        for (int i = 0; i < this.outputs; i++) {
            this.nodes.add(new Node(i + this.inputs));
            this.nodes[i + this.inputs].layer = 1;
            this.nextNode++;
        }

        this.nodes.add(new Node(this.nextNode)); //bias node
        this.biasNode = this.nextNode;
        this.nextNode++;
        this.nodes[this.biasNode].layer = 0;
    }
}
