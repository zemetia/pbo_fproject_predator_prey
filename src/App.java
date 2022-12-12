import entities.Entities;
import entities.Predator;
import entities.Prey;
import genetic.EdgeHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App extends JPanel implements ActionListener {
    ArrayList<Entities> population = new ArrayList<>();
    ArrayList<EdgeHistory> innovationHistory = new ArrayList<>();

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

}
