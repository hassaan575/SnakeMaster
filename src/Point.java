// Point class representing a coordinate

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Point extends GameCompnents {

//Parmeterized Point Constructor .................
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
      
    }
//Parmeterized Point Constructor .................
    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
       
    }

    // Move point
    public void move(Direction d, int value) {
        switch(d) {
            case UP: this.y -= value; break;
            case DOWN: this.y += value; break;
            case RIGHT: this.x += value; break;
            case LEFT: this.x -= value; break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point setX(int x) {
        this.x = x;

        return this;
    }

    public Point setY(int y) {
        this.y = y;

        return this;
    }

    // Check if point equals another point
    public boolean equals(Point p) {
        return this.x == p.getX() && this.y == p.getY();
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Check if point intersects another point with a given tolerance
    public boolean intersects(Point p) {
        return intersects(p, 10);
    }

    public boolean intersects(Point p, int tolerance) {
        int diffX = Math.abs(x - p.getX());
        int diffY = Math.abs(y - p.getY());

        return this.equals(p) || (diffX <= tolerance && diffY <= tolerance);
    }

    

   
}
