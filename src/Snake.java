// Snake class representing the snake in the game

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class Snake extends GameCompnents implements Reset{

    //Defualt Constructor of class snake
    public Snake(){
        System.out.println("Default Constructorn");
    }
    
    //Parameterized Constructor
    //Must have at least one overloaded Constructor
    public Snake(int x, int y) throws MyCustomFileNotFoundException {
        readFile( );
        this.head = new Point(x, y);
        this.direction = Direction.RIGHT;
        this.tail = new LinkedList<Point>();
        
        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
    }
    
    /*Must have at least one method that throws the custom exception and
is caught and handled without the game crashing*/
 @Override
    public void readFile( ) throws MyCustomFileNotFoundException{
        
        try {
            image = ImageIO.read(new File("food.png"));
        } catch (IOException ex) {
           throw new MyCustomFileNotFoundException("File Not Found");
        }
      
       
    }
    // Move the snake
    //Must have at least one overridden method.
    @Override
    public void move() {
     super.move();
     this.head.move(this.direction, 10);
    }
    
    // Add a tail to the snake
    public void addTail() {
        this.tail.add(new Point(-10, -10));
    }
    
    // Turn the snake
    public void turn(Direction d) {       
        if (d.isX() && direction.isY() || d.isY() && direction.isX()) {
           direction = d; 
        }       
    }
    
    // Get the tail of the snake
    public LinkedList<Point> getTail() {
        return this.tail;
    }
    
    // Get the head of the snake
    public Point getHead() {
        return this.head;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
   
}
