package genetic;

import java.util.ArrayList;

public class Genome {
    private ArrayList<EdgeGen> genes = new ArrayList<EdgeGen>();
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private int inputs;
    private int outputs;
    private int layers = 2;
    private int nextNode = 0;
    private ArrayList<Node> network = new ArrayList<Node>();
    private int biasNode;
    private Rand rand = new Rand();

    public Genome(int inputs, int output, boolean crossover) {
        this.inputs = inputs;
        this.outputs = outputs;

        if(crossover) return;

        for (int i = 0; i < this.inputs; i++) {
            this.nodes.add(new Node(i));
            this.nextNode++;
            this.nodes.get(i).layer = 0;
        }

        for (int i = 0; i < this.outputs; i++) {
            this.nodes.add(new Node(i + this.inputs));
            this.nodes.get(i + this.inputs).layer = 1;
            this.nextNode++;
        }

        this.nodes.add(new Node(this.nextNode)); //bias node
        this.biasNode = this.nextNode;
        this.nextNode++;
        this.nodes.get(this.biasNode).layer = 0;
    }

    public ArrayList<EdgeGen> getGenes(){
        return this.genes;
    }
    public void fullyConnect(ArrayList<EdgeHistory> innovationHistory) {
        //this will be a new number if no identical genome has mutated in the same

        for (var i = 0; i < this.inputs; i++) {
            for (var j = 0; j < this.outputs; j++) {
                int connectionInnovationNumber = this.getInnovationNumber(
                        innovationHistory,
                        this.nodes.get(i),
                        this.nodes.get(this.nodes.size() - j - 2)
                );

                this.genes.add(
                        new EdgeGen(
                                this.nodes.get(i),
                                this.nodes.get(this.nodes.size() - j - 2),
                                rand.get(-1, 1),
                                connectionInnovationNumber
                        )
                );
            }
        }

        int connectionInnovationNumber = this.getInnovationNumber(
                innovationHistory,
                this.nodes.get(this.biasNode),
                this.nodes.get(this.nodes.size() - 2)
        );

        this.genes.add(
                new EdgeGen(
                        this.nodes.get(this.biasNode),
                        this.nodes.get(this.nodes.size() - 2),
                        rand.get(-1, 1),
                        connectionInnovationNumber
                )
        );

        connectionInnovationNumber = this.getInnovationNumber(
                innovationHistory,
                this.nodes.get(this.biasNode),
                this.nodes.get(this.nodes.size() - 3)
        );
        this.genes.add(
                new EdgeGen(
                        this.nodes.get(this.biasNode),
                        this.nodes.get(this.nodes.size() - 3),
                        rand.get(-1, 1),
                        connectionInnovationNumber
                )
        );
        //add the connection with a random array


        //changed this so if error here
        this.connectNodes();
    }

    public int getInnovationNumber(ArrayList<EdgeHistory> innovationHistory, Node from, Node to) {
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
                            from,
                            to,
                            connectionInnovationNumber,
                            innoNumbers
                    )
            );
            nextConnectionNo++;
        }
        return connectionInnovationNumber;
    }
}
