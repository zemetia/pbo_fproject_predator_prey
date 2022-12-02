package genetic;

import java.util.ArrayList;

//Coded by Yoel Mountanus Sitorus a.k.a Zymer
public class Node {
    private int number;
    private double inputSum = 0;
    private double outputValue = 0;
    private int layer = 0;
    ArrayList<EdgeGen> outputEdge = new ArrayList<>();

    public Node(int number) {
        this.number = number;
        //this.drawPos = createVector();
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    //sigmoid activation function
    public double sigmoid(double val) {
        return 1.0 / (1.0 + Math.pow(Math.E, (-4.9) * val));
    }

    //the node sends its output to the inputs of the nodes its connected to
    public void engage() {
        if(this.layer != 0)
            this.outputValue = (int) sigmoid((double)this.inputSum);

        for(EdgeGen edge: this.outputEdge) {
            if(edge.enabled)
                edge.toNode.inputSum += edge.weight * this.outputValue;
        }
    }

    public Node cloneNode() {
        Node clone = new Node(this.number);
        clone.layer = this.layer;
        return clone;
    }

    public boolean isConnectedTo(Node node){
        if(node.layer == this.layer)
            return false;

        if(node.layer < this.layer) {
            for (EdgeGen edge: node.outputEdge) {
                if (edge.toNode == this) return true;
            }
        } else {
            for (EdgeGen edge: node.outputEdge) {
                if (edge.toNode == node) return true;
            }
        }

        return false;
    }

    public int getNumber(){
        return this.number;
    }
    public void setInputSum(double inputSum) {
        this.inputSum = inputSum;
    }
    public double getOutputValue() {
        return outputValue;
    }
    public void setLayer(int layer) {
        this.layer = layer;
    }
    public int getLayer() {
        return layer;
    }
}
