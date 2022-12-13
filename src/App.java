import Collection.Coordinate;
import entities.Entities;
import entities.Predator;
import entities.Prey;
import genetic.EdgeHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class App extends JPanel implements ActionListener, MouseListener {
    ArrayList<Entities> population = new ArrayList<>();
    ArrayList<EdgeHistory> innovationHistory = new ArrayList<>();
    Entities selected = null;

    public static void main(String[] args){
        App sim = new App();
    }

    public App() {
        JFrame frame = new JFrame("Predator and Prey");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String data = (String)JOptionPane.showInputDialog(
                frame,
                "Banyak jumlah Predator dan Prey:\n"
                        + " dengan format (Predator) dan (Prey)",
                "Initial Jumlah Predator dan Preys",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "1 dan 20");

        String[] result = data.split(" dan ", 2);
        int initCountPred = Integer.parseInt( result[0] );
        int initCountPrey = Integer.parseInt( result[1] );

        //init the first entities
        initPreds(initCountPred);
        initPreys(initCountPrey);

        for (Entities entity: population) {
            entity.brain.fullyConnect(this.innovationHistory);
            entity.brain.generateNetwork();
        }

        Timer t = new Timer(16, this);
        t.restart();

        frame.add(this);
        frame.addMouseListener(this);
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        for(Entities entity: population){
            try {
                entity.paint(g);
            }
            catch (Exception e) {
                //pass
            }

        }
        long totalPopulation = population.size();
        long predatorPopulation = population .stream() .filter((s) -> s instanceof Predator) .count();
        long preyPopulation = totalPopulation - predatorPopulation;

        g.drawString("All Population: " + totalPopulation, 10, 10);
        g.drawString("Predators Population: " + predatorPopulation, 10, 30);
        g.drawString("Preys Population: " + preyPopulation, 10, 50);

        if (selected != null) {
            g.drawString("Type: " + ((selected instanceof Predator)? "Predator" : "Prey"), 10, 300);
            g.drawString("Generation: " + selected.getGeneration(), 10, 320);
            g.drawString("Eaten: " + selected.getEaten(), 10, 340);
            g.drawString("Children: " + selected.getChildren(), 10, 360);
            g.drawString("Energy: " + String.format("%.5g%n", selected.getEnergy()), 10, 380);
            g.drawString("Status: " + ((selected.isLived())? "Live" : "Dead"), 10, 400);
        }

    }

    public void initPreys(int amount){
        for(int i = 0; i < amount; i++)
            population.add(new Prey(population));
    }

    public void initPreds(int amount){
        for(int i = 0; i < amount; i++)
            population.add(new Predator(population));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + "," + e.getY());
        Coordinate<Double> clickLocation = new Coordinate<Double>((double)e.getX(), (double)e.getY());

        for (Entities entity: population) {
            double distance = entity.getCenterPositionVal().determineDistance(clickLocation);
            if(distance < entity.getRadius()) {
                selected = entity;
                break;
            }
        }
    }

    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
