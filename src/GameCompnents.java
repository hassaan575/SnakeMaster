
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*Must have at least one parent class that has 2 or more child classes
that extend it*/
public class GameCompnents   extends JPanel{
 
    public GameCompnents(){
        
    }
        // Game components declare inside GameCompnents class
    protected Timer timer;
    protected Snake snake;
    protected Point apple;
    protected int points = 0;
    protected int bestScore = 0;
    public static BufferedImage image;
    protected GameStatus status;
    protected boolean didLoadCherryImage = true;
    

    // Fonts for text rendering
    protected static Font FONT_M = new Font("MV Boli", Font.PLAIN, 24);
    protected static Font FONT_M_ITALIC = new Font("MV Boli", Font.ITALIC, 24);
    protected static Font FONT_L = new Font("MV Boli", Font.PLAIN, 84);
    protected static Font FONT_XL = new Font("MV Boli", Font.PLAIN, 150);
    protected static int WIDTH = 760;
    protected static int HEIGHT = 520;
    protected static int DELAY = 50;
    protected Direction direction;
    protected Point head;
    //Must incorporate at least one of the following:
    //Linked List
    protected LinkedList<Point> tail;
    protected int x;
    protected int y;
    public void move() {
        LinkedList<Point> newTail = new LinkedList<Point>();
        for (int i = 0, size = tail.size(); i < size; i++) {
            Point previous = i == 0 ? head : tail.get(i - 1);

            newTail.add(new Point(previous.getX(), previous.getY()));
        }
        this.tail = newTail;
        
       
    }
}
