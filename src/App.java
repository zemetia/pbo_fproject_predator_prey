import entities.Entities;
import entities.Predator;
import entities.Prey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App extends JPanel implements ActionListener {
    ArrayList<Entities> population = new ArrayList<>();

    public static void main(String[] args){
        App sim = new App();
    }

    public App() {
        JFrame frame = new JFrame("Predator and Prey");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //init the first entities
        initPreys(10);
        initPreds(1);

        Timer t = new Timer(16, this);
        t.restart();

        frame.add(this);
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        for(Entities entity: population){

                entity.paint(g);

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

}
