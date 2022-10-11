package genetic;

import java.util.ArrayList;

public class EdgeHistory {
    private Node fromNode;
    private Node toNode;
    private int innovationNumber;
    private ArrayList<Integer> innovationNumbers = new ArrayList<Integer>();

    public EdgeHistory(Node from, Node to, int inno, ArrayList<Integer> innovationNumers) {
        this.fromNode = from;
        this.toNode = to;
        this.innovationNumber = inno;
        this.innovationNumbers = (ArrayList)innovationNumbers.clone();
    }

    public int getInnovationNo(){
        return this.innovationNumber;
    }

    public boolean matches(Genome genome, Node from, Node to) {
        if(genome.getGenes().size() === this.innovationNumbers.size()) {
            if(from.getNumber() === this.fromNode && to.getNumber() === this.toNode) {
                for(EdgeGen edge: genome.getGenes()) {
                    if(!this.innovationNumbers.contains(edge.getInnovationNo()))
                        return false;
                }
                return true;
            }
        }
        return false;
    }
}
