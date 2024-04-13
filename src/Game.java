import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.LineUnavailableException;

// Main game class
class Game extends GameCompnents implements Reset {

    // Default Constructor Constructor
    public Game() throws MyCustomFileNotFoundException, IOException, LineUnavailableException {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        readFile();
        // Adding Key listener
        addKeyListener(new KeyListener());
        setFocusable(true);
        setBackground(new Color(  211, 247, 236   ));
        setDoubleBuffered(true);

        // Initialize snake and game status
         status = GameStatus.NOT_STARTED;
         //repaint function paint the panel to jframe
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);

        Toolkit.getDefaultToolkit().sync();
    }

    // Game rendering logic
    private void update() {
        //moving  snake forwad ..........
        snake.move();

        //if condition checking if snake eat apple then increament score to 1
        if (apple != null && snake.getHead().intersects(apple, 20)) {
           
            snake.addTail();
            apple = null;
            points++;

        }
       //checking if apple is eaten then spawnApple() function create new apple and place on game board
        if (apple == null) {
            spawnApple();
        }
         //Function for checking the game status if its over showing game over message
        checkForGameOver();
    }

    // Reset the game and start again 
    @Override
    public void reset() {
        try {
            points = 0;
            apple = null;
            snake = new Snake(WIDTH / 2, HEIGHT / 2);
            setStatus(GameStatus.RUNNING);
        } catch (MyCustomFileNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Set game status it has three state Running , Paused , and Over
    public void setStatus(GameStatus newStatus) {
        switch (newStatus) {
            case RUNNING:
                timer = new Timer();
                timer.schedule(new GameLoop(), 0, DELAY);
                break;
            case PAUSED:
                timer.cancel();
            case GAME_OVER:
                timer.cancel();
                bestScore = points > bestScore ? points : bestScore;
                break;
        }

        status = newStatus;
    }

    // Toggle game pause
    private void togglePause() {
        setStatus(status == GameStatus.PAUSED ? GameStatus.RUNNING : GameStatus.PAUSED);
    }

    // Check for game over conditions
    private void checkForGameOver() {
        Point head = snake.getHead();
        
        boolean hitBoundary = head.getX() <= 20
                || head.getX() >= WIDTH + 10
                || head.getY() <= 40
                || head.getY() >= HEIGHT + 30;

        boolean ateItself = false;

        for (Point t : snake.getTail()) {
            ateItself = ateItself || head.equals(t);
        }

        if (hitBoundary || ateItself) {
            //checing if current game score is greater then best score then will save that score to text file 
            if(points> bestScore){
               updateScoreToFile(points); 
            }
            //setting the status of the game is over 
            setStatus(GameStatus.GAME_OVER);
   

        }
        // 
    }

    // Draw centered string which shows game is over message and press enter to start game again
    public void drawCenteredString(Graphics g, String text, Font font, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (WIDTH - metrics.stringWidth(text)) / 2;

        g.setFont(font);
        g.drawString(text, x, y);
    }

    // Game rendering  render function paint the game componets on GUI Panel
    private void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.setFont(FONT_M);



        Point p = snake.getHead();

        g2d.drawString("CURRENT PLAY SCORE: " + String.format("%02d", points), 20, 30);
    
        if(points>bestScore){
           g2d.drawString("BEST SCORE: " + String.format("%02d", points), 550, 30); 
        }else{
                g2d.drawString("BEST SCORE: " + String.format("%02d", bestScore), 550, 30);
        }

        if (apple != null) {
            if (didLoadCherryImage) {
                g2d.drawImage(image, apple.getX(), apple.getY(), 20, 20, null);
            } else {
                g2d.setColor(Color.BLACK);
                g2d.fillOval(apple.getX(), apple.getY(), 15, 15);
                g2d.setColor(Color.BLACK);
            }
        }

        if (status == GameStatus.GAME_OVER) {
            drawCenteredString(g2d, "Press  enter  to  start  again", FONT_M_ITALIC, 330);
            drawCenteredString(g2d, "GAME OVER", FONT_L, 300);
        }

        if (status == GameStatus.PAUSED) {
            g2d.drawString("Paused", 600, 14);
        }

        g2d.setColor(new Color( 34, 12, 218 ));
        g2d.fillRect(p.getX(), p.getY(), 10, 10);

        for (int i = 0, size = snake.getTail().size(); i < size; i++) {
            Point t = snake.getTail().get(i);

            g2d.fillRect(t.getX(), t.getY(), 10, 10);
        }

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(20, 40, WIDTH, HEIGHT);
    }

    // Spawn Apple  at random position on game board
    public void spawnApple() {
        apple = new Point((new Random()).nextInt(WIDTH - 60) + 20,
                (new Random()).nextInt(HEIGHT - 60) + 40);
    }

    
    /*Must read/write something to a File (Suggestion: If you can't think of
anything else, what about high scores?)*/
    //reading bestScore.txt file which save best score for later shows
    @Override
    public void readFile() throws MyCustomFileNotFoundException{
        BufferedReader br = null;
        try {
            // File path is passed as parameter
            File file = new File("bestScore.txt");
            br = new BufferedReader(new FileReader(file));
            // Declaring a string variable
            String st;
            try {
                // Condition holds true till
                // there is character in a string
                while ((st = br.readLine()) != null) {
                    bestScore = Integer.parseInt(st);
                }
            } catch (IOException ex) {
             throw new MyCustomFileNotFoundException("File Not Found");
            }
        } catch (FileNotFoundException ex) {
            throw new MyCustomFileNotFoundException("File Not Found");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //updateScoreToFile function udate the best score to text file
    public void updateScoreToFile(int updatedScore){
        try {
            String str = updatedScore+"";
            BufferedWriter writer = new BufferedWriter(new FileWriter("bestScore.txt"));
            writer.write(str);
            
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Game loop KeyListener which check whic key press like move up move down move left move right or enter and P 
    // paus and again play .
    private class KeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (status == GameStatus.RUNNING) {
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        snake.turn(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        snake.turn(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        snake.turn(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        snake.turn(Direction.DOWN);
                        break;
                }
            }

            if (status == GameStatus.NOT_STARTED) {
                setStatus(GameStatus.RUNNING);
            }

            if (status == GameStatus.GAME_OVER && key == KeyEvent.VK_ENTER) {
                reset();
            }

            if (key == KeyEvent.VK_P) {
                togglePause();
            }
        }
    }


    
    
    
    // Game play in loop
    private class GameLoop extends java.util.TimerTask {

        public void run() {
            update();
            repaint();
        }
    }
}



// Enumeration for game status
enum GameStatus {
    NOT_STARTED, RUNNING, PAUSED, GAME_OVER
}

// Enumeration for direction of snake
enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public boolean isX() {
        return this == LEFT || this == RIGHT;
    }

    public boolean isY() {
        return this == UP || this == DOWN;
    }
}
