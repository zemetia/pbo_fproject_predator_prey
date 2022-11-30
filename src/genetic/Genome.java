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

    public void addConnection(ArrayList<EdgeHistory> innovationHistory) {
        //cannot add a connection to a fully connected network
        if (this.fullyConnected()) {
            System.out.println("connection failed");
            return;
        }


        //get random this.nodes
        double randomNode1 = Math.floor(rand.get(this.nodes.size()));
        double randomNode2 = Math.floor(rand.get(this.nodes.size()));
        while (this.randomConnectionNodesAreShit(randomNode1, randomNode2)) { //while the random this.nodes are no good
            //get new ones
            randomNode1 = Math.floor(rand.get(this.nodes.size()));
            randomNode2 = Math.floor(rand.get(this.nodes.size()));
        }
        var temp;
        if (this.nodes[randomNode1].layer > this.nodes[randomNode2].layer) { //if the first random node is after the second then switch
            temp = randomNode2;
            randomNode2 = randomNode1;
            randomNode1 = temp;
        }

        //get the innovation number of the connection
        //this will be a new number if no identical genome has mutated in the same way
        var connectionInnovationNumber = this.getInnovationNumber(innovationHistory, this.nodes[randomNode1], this.nodes[randomNode2]);
        //add the connection with a random array

        this.genes.add(new EdgeGen(this.nodes[randomNode1], this.nodes[randomNode2], rand.get(-1, 1), connectionInnovationNumber)); //changed this so if error here
        this.connectNodes();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    public boolean randomConnectionNodesAreShit(int r1, int r2) {
        if (this.nodes[r1].layer == this.nodes[r2].layer) return true; // if the this.nodes are in the same layer
        if (this.nodes[r1].isConnectedTo(this.nodes[r2])) return true; //if the this.nodes are already connected



        return false;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------
    //returns the innovation number for the new mutation
    //if this mutation has never been seen before then it will be given a new unique innovation number
    //if this mutation matches a previous mutation then it will be given the same innovation number as the previous one
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
    //----------------------------------------------------------------------------------------------------------------------------------------

    //returns whether the network is fully connected or not
    public boolean fullyConnected() {

        //System.out.println("fullyconnect1");
        int maxConnections = 0;
        ArrayList<Integer> nodesInLayers = new ArrayList<Integer>(); //array which stored the amount of this.nodes in each layer
        for (int i = 0; i < this.layers; i++) {
            nodesInLayers.set(i, 0);
        }
        //populate array
        for (int i = 0; i < this.nodes.size(); i++) {
            nodesInLayers[this.nodes.get(i).layer] += 1;
        }
        //System.out.println("fullyconnect2");
        //for each layer the maximum amount of connections is the number in this layer * the number of this.nodes infront of it
        //so lets add the max for each layer together and then we will get the maximum amount of connections in the network
        for (int i = 0; i < this.layers - 1; i++) {
            var nodesInFront = 0;
            for (var j = i + 1; j < this.layers; j++) { //for each layer infront of this layer
                nodesInFront += nodesInLayers[j]; //add up this.nodes
            }

            maxConnections += nodesInLayers.get(i) * nodesInFront;
        }
        //System.out.println("fullyconnect3");
        if (maxConnections == this.genes.size()) { //if the number of connections is equal to the max number of connections possible then it is full
            return true;
        }


        //System.out.println(this.genes.size());
        //System.out.println(maxConnections);
        //System.out.println(nodesInLayers);
        return false;
    }


    //-------------------------------------------------------------------------------------------------------------------------------
    //mutates the genome
    public void mutate(ArrayList<EdgeHistory> innovationHistory) {
        if (this.genes.size() == 0) {
            this.addConnection(innovationHistory);
        }


        var rand1 = rand.get(1);
        if (rand1 < 0.8) { // 80% of the time mutate weights

            for (int i = 0; i < this.genes.size(); i++) {
                this.genes.get(i).mutateWeight();
            }
        }

        //5% of the time add a new connection
        var rand2 = rand.get(1);
        if (rand2 < 0.05) {

            this.addConnection(innovationHistory);
        }

        //1% of the time add a node
        var rand3 = rand.get(1);
        if (rand3 < 0.01) {

            this.addNode(innovationHistory);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------
    //called when this Genome is better that the other parent
    public void crossover(parent2) {
        var child = new Genome(this.inputs, this.outputs, true);
        child.genes = [];
        child.nodes = [];
        child.layers = this.layers;
        child.nextNode = this.nextNode;
        child.biasNode = this.biasNode;
        var childGenes = []; // new ArrayList<EdgeGen>();//list of genes to be inherrited form the parents
        var isEnabled = []; // new ArrayList<Boolean>();
        //all inherited genes
        for (int i = 0; i < this.genes.size(); i++) {
            var setEnabled = true; //is this node in the chlid going to be enabled

            var parent2gene = this.matchingGene(parent2, this.genes.get(i).innovationNo);
            if (parent2gene != -1) { //if the genes match
                if (!this.genes.get(i).enabled || !parent2.genes[parent2gene].enabled) { //if either of the matching genes are disabled

                    if (rand.get(1) < 0.75) { //75% of the time disabel the childs gene
                        setEnabled = false;
                    }
                }
                var rand = rand.get(1);
                if (rand < 0.5) {
                    childGenes.add(this.genes.get(i));

                    //get gene from this fucker
                } else {
                    //get gene from parent2
                    childGenes.add(parent2.genes[parent2gene]);
                }
            } else { //disjoint or excess gene
                childGenes.add(this.genes.get(i));
                setEnabled = this.genes.get(i).enabled;
            }
            isEnabled.add(setEnabled);
        }


        //since all excess and disjovar genes are inherrited from the more fit parent (this Genome) the childs structure is no different from this parent | with exception of dormant connections being enabled but this wont effect this.nodes
        //so all the this.nodes can be inherrited from this parent
        for (int i = 0; i < this.nodes.size(); i++) {
            child.nodes.add(this.nodes.get(i).clone());
        }

        //clone all the connections so that they connect the childs new this.nodes

        for (int i = 0; i < childGenes.size(); i++) {
            child.genes.add(childGenes.get(i).clone(child.getNode(childGenes.get(i).fromNode.number), child.getNode(childGenes.get(i).toNode.number)));
            child.genes.get(i).enabled = isEnabled.get(i);
        }

        child.connectNodes();
        return child;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //returns whether or not there is a gene matching the input innovation number  in the input genome
    public int matchingGene(parent2, innovationNumber) {
        for (int i = 0; i < parent2.genes.size(); i++) {
            if (parent2.genes.get(i).innovationNo == innovationNumber) {
                return i;
            }
        }
        return -1; //no matching gene found
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //prints out info about the genome to the console
    public void printGenome() {
        System.out.println("Prvar genome  layers:" + this.layers);
        System.out.println("bias node: " + this.biasNode);
        System.out.println("this.nodes");
        for (int i = 0; i < this.nodes.size(); i++) {
            System.out.println(this.nodes.get(i).getNumber() + ",");
        }
        System.out.println("Genes");
        for (int i = 0; i < this.genes.size(); i++) { //for each EdgeGen
            System.out.println("gene " + this.genes.get(i).innovationNo + "From node " + this.genes.get(i).fromNode.getNumber() + "To node " + this.genes.get(i).toNode.getNumber() +
                    "is enabled " + this.genes.get(i).enabled + "from layer " + this.genes.get(i).fromNode.layer + "to layer " + this.genes.get(i).toNode.layer + "weight: " + this.genes.get(i).weight);
        }

        System.out.println();
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //returns a copy of this genome
    public [this] clone() {

        Genome clone = new Genome(this.inputs, this.outputs, true);

        for (int i = 0; i < this.nodes.size(); i++) { //copy this.nodes
            clone.nodes.add(this.nodes.get(i).clone());
        }

        //copy all the connections so that they connect the clone new this.nodes

        for (int i = 0; i < this.genes.size(); i++) { //copy genes
            clone.genes.add(this.genes.get(i).clone(clone.getNode(this.genes.get(i).fromNode.getNumber()), clone.getNode(this.genes.get(i).toNode.getNumber())));
        }

        clone.layers = this.layers;
        clone.nextNode = this.nextNode;
        clone.biasNode = this.biasNode;
        clone.connectNodes();

        return clone;
    }

}
