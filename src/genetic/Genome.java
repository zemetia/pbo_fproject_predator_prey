package genetic;

import java.util.ArrayList;

public class Genome {
    private ArrayList<EdgeGen> genes = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private int inputs;
    private int outputs;
    private int layers = 2;
    private int nextNode = 0;
    private ArrayList<Node> network = new ArrayList<>();
    private int biasNode;
    private final Rand rand = new Rand();
    private int nextConnectionNo;

    public Genome(int inputs, int outputs, boolean crossover) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.nextConnectionNo = 0;

        if(crossover) return;

        for (int i = 0; i < this.inputs; i++) {
            this.nodes.add(new Node(i));
            this.nextNode++;
            this.nodes.get(i).setLayer(0);
        }

        for (int i = 0; i < this.outputs; i++) {
            this.nodes.add(new Node(i + this.inputs));
            this.nodes.get(i + this.inputs).setLayer(1);
            this.nextNode++;
        }

        this.nodes.add(new Node(this.nextNode)); //bias node
        this.biasNode = this.nextNode;
        this.nextNode++;
        this.nodes.get(this.biasNode).setLayer(0);
    }

    public ArrayList<EdgeGen> getGenes(){

        return this.genes;
    }
    public void fullyConnect(ArrayList<EdgeHistory> innovationHistory) {
        //this will be a new number if no identical genome has mutated in the same

        for (int i = 0; i < this.inputs; i++) {
            for (int j = 0; j < this.outputs; j++) {
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

    public Node getNode(int nodeNumber) {
        for (Node node: this.nodes) {
            if (node.getNumber() == nodeNumber) {
                return node;
            }
        }
        return null;
    }

    public void connectNodes() {

        for (Node node: this.nodes) //clear the connections
            node.outputEdge.clear();

        for (EdgeGen gene: this.genes)  //for each EdgeGen
            gene.fromNode.outputEdge.add(gene); //add it to node

    }

    public ArrayList<Double> feedForward(ArrayList<Double> inputValues) {
        //set the outputs of the input this.nodes
        for (int i = 0; i < this.inputs; i++)
            this.nodes.get(i).setOutputValue(inputValues.get(i));

        this.nodes.get(this.biasNode).setOutputValue(1); //output of bias is 1

        for (Node net: this.network)  //for each node in the network engage it(see node class for what this does)
            net.engage();

        //the outputs are this.nodes[inputs] to this.nodes [inputs+outputs-1]
        ArrayList<Double> outs = new ArrayList<>();
        for (int i = 0; i < this.outputs; i++)
            outs.add(i, this.nodes.get(this.inputs + i).getOutputValue());

        for (Node node: this.nodes) //reset all the this.nodes for the next feed forward
            node.setInputSum(0);

        return outs;
    }

    public void generateNetwork() {
        this.connectNodes();
        this.network.clear();
        //for each layer add the node in that layer, since layers cannot connect to themselves there is no need to order the this.nodes within a layer

        for (int i = 0; i < this.layers; i++) { //for each layer
            for (Node node: this.nodes) { //for each node
                if (node.getLayer() == i)  //if that node is in that layer
                    this.network.add(node);
            }
        }
    }

    public void addNode(ArrayList<EdgeHistory> innovationHistory) {
        //pick a random connection to create a node between
        if (this.genes.size() == 0) {
            this.addConnection(innovationHistory);
            return;
        }

        int randomConnection = (int)Math.floor(rand.get(this.genes.size()));

        while (this.genes.get(randomConnection).fromNode == this.nodes.get(this.biasNode) && this.genes.size() != 1) { //dont disconnect bias
            randomConnection = (int)Math.floor(rand.get(this.genes.size()));
        }

        this.genes.get(randomConnection).enabled = false; //disable it

        int newNodeNo = this.nextNode;
        this.nodes.add(new Node(newNodeNo));
        this.nextNode++;
        //add a new connection to the new node with a weight of 1
        int connectionInnovationNumber = this.getInnovationNumber(innovationHistory, this.genes.get(randomConnection).fromNode, this.getNode(newNodeNo));
        this.genes.add(new EdgeGen(this.genes.get(randomConnection).fromNode, this.getNode(newNodeNo), 1, connectionInnovationNumber));


        connectionInnovationNumber = this.getInnovationNumber(innovationHistory, this.getNode(newNodeNo), this.genes.get(randomConnection).toNode);
        //add a new connection from the new node with a weight the same as the disabled connection
        this.genes.add(new EdgeGen(this.getNode(newNodeNo), this.genes.get(randomConnection).toNode, this.genes.get(randomConnection).weight, connectionInnovationNumber));
        this.getNode(newNodeNo).setLayer(this.genes.get(randomConnection).fromNode.getLayer() + 1);


        connectionInnovationNumber = this.getInnovationNumber(innovationHistory, this.nodes.get(this.biasNode), this.getNode(newNodeNo));
        //connect the bias to the new node with a weight of 0
        this.genes.add(new EdgeGen(this.nodes.get(this.biasNode), this.getNode(newNodeNo), 0, connectionInnovationNumber));

        //if the layer of the new node is equal to the layer of the output node of the old connection then a new layer needs to be created
        //more accurately the layer numbers of all layers equal to or greater than this new node need to be incrimented
        if (this.getNode(newNodeNo).getLayer() == this.genes.get(randomConnection).toNode.getLayer()) {
            for (int i = 0; i < this.nodes.size() - 1; i++) { //dont include this newest node
                if (this.nodes.get(i).getLayer() >= this.getNode(newNodeNo).getLayer()) {
                    this.nodes.get(i).setLayer(this.nodes.get(i).getLayer() + 1);
                }
            }
            this.layers++;
        }
        this.connectNodes();
    }

    public void addConnection(ArrayList<EdgeHistory> innovationHistory) {
        //cannot add a connection to a fully connected network
        if (this.fullyConnected()) {
            System.out.println("connection failed");
            return;
        }


        //get random this.nodes
        int randomNode1 = (int)Math.floor(rand.get(this.nodes.size()));
        int randomNode2 = (int)Math.floor(rand.get(this.nodes.size()));
        while (this.randomEdgeNode(randomNode1, randomNode2)) { //while the random this.nodes are no good
            //get new ones
            randomNode1 = (int)Math.floor(rand.get(this.nodes.size()));
            randomNode2 = (int)Math.floor(rand.get(this.nodes.size()));
        }
        int temp;
        if (this.nodes.get(randomNode1).getLayer() > this.nodes.get(randomNode2).getLayer()) { //if the first random node is after the second then switch
            temp = randomNode2;
            randomNode2 = randomNode1;
            randomNode1 = temp;
        }

        int connectionInnovationNumber = this.getInnovationNumber(innovationHistory, this.nodes.get(randomNode1), this.nodes.get(randomNode2));
        //add the connection with a random array

        this.genes.add(new EdgeGen(this.nodes.get(randomNode1), this.nodes.get(randomNode2), rand.get(-1, 1), connectionInnovationNumber)); //changed this so if error here
        this.connectNodes();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    public boolean randomEdgeNode(int r1, int r2) {
        return this.nodes.get(r1).getLayer() == this.nodes.get(r2).getLayer() || // if the this.nodes are in the same layer
                this.nodes.get(r1).isConnectedTo(this.nodes.get(r2)); // if the this.nodes are already connected
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
            ArrayList<Integer> innoNumbers = new ArrayList<>();
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

    public boolean fullyConnected() {

        //System.out.println("fullyconnect1");
        int maxConnections = 0;
        ArrayList<Integer> nodesInLayers = new ArrayList<>(); //array which stored the amount of this.nodes in each layer
        for (int i = 0; i < this.layers; i++) {
            nodesInLayers.set(i, 0);
        }
        //populate array

        int nodeLayer;
        for (Node node: this.nodes) {
            nodeLayer = nodesInLayers.get(node.getLayer());
            nodesInLayers.set(node.getLayer(), ++nodeLayer);
        }

        for (int i = 0; i < this.layers - 1; i++) {
            int nodesInFront = 0;
            for (int j = i + 1; j < this.layers; j++) { //for each layer infront of this layer
                nodesInFront += nodesInLayers.get(j); //add up this.nodes
            }

            maxConnections += nodesInLayers.get(i) * nodesInFront;
        }

        return maxConnections == this.genes.size();

    }


    //-------------------------------------------------------------------------------------------------------------------------------
    //mutates the genome
    public void mutate(ArrayList<EdgeHistory> innovationHistory) {
        if (this.genes.size() == 0) {
            this.addConnection(innovationHistory);
        }

        double rand1 = rand.get(1);
        if (rand1 < 0.8) { // 80% of the time mutate weights

            for (EdgeGen gene: this.genes) {
                gene.mutateWeight();
            }
        }

        double rand2 = rand.get(1);
        if (rand2 < 0.05) {

            this.addConnection(innovationHistory);
        }

        //1% of the time add a node
        double rand3 = rand.get(1);
        if (rand3 < 0.01) {

            this.addNode(innovationHistory);
        }
    }

    public Genome crossover(Genome parent2) {
        Genome child = new Genome(this.inputs, this.outputs, true);
        child.layers = this.layers;
        child.nextNode = this.nextNode;
        child.biasNode = this.biasNode;
        ArrayList<EdgeGen> childGenes = new ArrayList<>(); // new ArrayList<EdgeGen>();//list of genes to be inherrited form the parents
        ArrayList<Boolean> isEnabled = new ArrayList<>(); // new ArrayList<Boolean>();
        //all inherited genes
        for (EdgeGen gene: this.genes) {
            boolean setEnabled = true; //is this node in the chlid going to be enabled

            int parent2gene = this.matchingGene(parent2, gene.getInnovationNo());
            if (parent2gene != -1) { //if the genes match
                if (!gene.enabled || !parent2.genes.get(parent2gene).enabled) { //if either of the matching genes are disabled

                    if (rand.get(1) < 0.75) { //75% of the time disabel the childs gene
                        setEnabled = false;
                    }
                }
                double random = rand.get(1);
                if (random < 0.5) {
                    childGenes.add(gene);

                    //get gene from this fucker
                } else {
                    //get gene from parent2
                    childGenes.add(parent2.genes.get(parent2gene));
                }
            } else { //disjoint or excess gene
                childGenes.add(gene);
                setEnabled = gene.enabled;
            }
            isEnabled.add(setEnabled);
        }


        //since all excess and disjovar genes are inherrited from the more fit parent (this Genome) the childs structure is no different from this parent | with exception of dormant connections being enabled but this wont effect this.nodes
        //so all the this.nodes can be inherrited from this parent
        for (Node node: this.nodes)
            child.nodes.add(node.cloneNode());


        //clone all the connections so that they connect the childs new this.nodes
        for (int i = 0; i < childGenes.size(); i++) {
            child.genes.add(
                    childGenes.get(i).cloneEdge(
                            child.getNode(childGenes.get(i).fromNode.getNumber()),
                            child.getNode(childGenes.get(i).toNode.getNumber())
                    )
            );
            child.genes.get(i).enabled = isEnabled.get(i);
        }

        child.connectNodes();
        return child;
    }

    public int matchingGene(Genome parent2, int innovationNumber) {
        for (int i = 0; i < parent2.genes.size(); i++) {
            if (parent2.genes.get(i).getInnovationNo() == innovationNumber) return i;
        }
        return -1; //no matching gene found
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //prints out info about the genome to the console
    public void printGenome() {
        System.out.println("Prvar genome layers:" + this.layers);
        System.out.println("bias node: " + this.biasNode);
        System.out.println("this.nodes");
        for (Node node : this.nodes)
            System.out.println(node.getNumber() + ",");

        System.out.println("Genes");
        for (EdgeGen gene : this.genes)  //for each EdgeGen
            System.out.println("gene " + gene.getInnovationNo() + "From node " + gene.fromNode.getNumber() + "To node " + gene.toNode.getNumber() +
                    "is enabled " + gene.enabled + "from layer " + gene.fromNode.getLayer() + "to layer " + gene.toNode.getLayer() + "weight: " + gene.weight);


        System.out.println();
    }

    public Genome cloneGenome() {

        Genome clone = new Genome(this.inputs, this.outputs, true);

        for (int i = 0; i < this.nodes.size(); i++) { //copy this.nodes
            clone.nodes.add(this.nodes.get(i).cloneNode());
        }

        //copy all the connections so that they connect the clone new this.nodes

        for (int i = 0; i < this.genes.size(); i++) { //copy genes
            clone.genes.add(
                this.genes.get(i).cloneEdge(
                    clone.getNode(this.genes.get(i).fromNode.getNumber()),
                    clone.getNode(this.genes.get(i).toNode.getNumber()
                    )
                )
            );
        }

        clone.layers = this.layers;
        clone.nextNode = this.nextNode;
        clone.biasNode = this.biasNode;
        clone.connectNodes();

        return clone;
    }

}
