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

    public ArrayList<EdgeGen> getGenes(){
        return this.genes;
    }
    public void fullyConnect(innovationHistory) {
        //this will be a new number if no identical genome has mutated in the same

        for (var i = 0; i < this.inputs; i++) {
            for (var j = 0; j < this.outputs; j++) {
                EdgeGen connectionInnovationNumber = this.getInnovationNumber(
                        innovationHistory,
                        this.nodes[i],
                        this.nodes[this.nodes.size() - j - 2]
                );

                this.genes.add(
                        new EdgeGen(
                                this.nodes[i],
                                this.nodes[this.nodes.size() - j - 2],
                                random(-1, 1),
                                connectionInnovationNumber
                        )
                );
            }
        }

        EdgeGen connectionInnovationNumber = this.getInnovationNumber(
                innovationHistory,
                this.nodes[this.biasNode],
                this.nodes[this.nodes.size() - 2]
        );
        this.genes.add(
                new EdgeGen(
                        this.nodes[this.biasNode],
                        this.nodes[this.nodes.size() - 2],
                        random(-1, 1),
                        connectionInnovationNumber
                )
        );

        connectionInnovationNumber = this.getInnovationNumber(
                innovationHistory,
                this.nodes[this.biasNode],
                this.nodes[this.nodes.size() - 3]
        );
        this.genes.add(
                new EdgeGen(
                        this.nodes[this.biasNode],
                        this.nodes[this.nodes.size() - 3],
                        random(-1, 1),
                        connectionInnovationNumber
                )
        );
        //add the connection with a random array


        //changed this so if error here
        this.connectNodes();
    }

    public int getInnovationNumber(ArrayList<EdgeGen> innovationHistory, Node from, Node to) {
        boolean isNew = true;
        int connectionInnovationNumber = nextConnectionNo;
        for (EdgeHistory history: innovationHistory) {
            if (history.matches(this, from, to)) {
                isNew = false;
                connectionInnovationNumber = history.getInnovationNo();
                break;
            }
        }

        if (isNew) {
            ArrayList<Integer> innoNumbers = new ArrayList<Integer>();
            for (EdgeGen edge: this.genes)
                innoNumbers.add(edge.getInnovationNo());

            innovationHistory.add(
                    new EdgeHistory(
                            from.getNumber(),
                            to.getNumber(),
                            connectionInnovationNumber,
                            innoNumbers
                    )
            );
            nextConnectionNo++;
        }
        return connectionInnovationNumber;
    }
}
