// Main class for game window

import java.awt.EventQueue;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
//Must use a Layout Manager with a JFrame.
class Main extends JFrame {
    public Main() throws MyCustomFileNotFoundException, IOException, LineUnavailableException {
        initUI();
    }

    // Initialize UI class which initilize the GUI
    private void initUI() throws MyCustomFileNotFoundException, IOException, LineUnavailableException {
        //Creating Game Class Object 
        Game game=new Game();
        //Passing game class object to StartAppFrame.run function
        StartAppFrame.run(game);
        //adding game object to JFrame...
        add(game);
        //Playing background sound start Thread which play sound in loop 
        new BackgroundSoundPlay().start();
        setTitle("Snake");
        setSize(800, 610);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
//Main Function for Program Entry Point
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main ex = null;
            try {
                ex = new Main();
            } catch (Exception ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            } 
            ex.setVisible(true);
        });
    }
}