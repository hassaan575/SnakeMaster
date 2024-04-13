import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

//Must incorporate at least one of the following
public class BackgroundSoundPlay extends Thread{
  
    
    private Player playerBackground; 




    public void play() {
 try {   
      BufferedInputStream buffer = new BufferedInputStream(
    new FileInputStream("background.mp3"));
     playerBackground = new Player(buffer);
     playerBackground.play();
     

 }
 catch (Exception e) {

     System.out.println(e);
 }

    }


     @Override
     public void run(){
         play();
         while(true){
             if(playerBackground!=null){
             if(playerBackground.isComplete()){
                 try {  
                     playerBackground.play();
                 } catch (JavaLayerException ex) {
                     Logger.getLogger(BackgroundSoundPlay.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             
             }
             try {
                 Thread.sleep(30000);
             } catch (InterruptedException ex) {
                 Logger.getLogger(BackgroundSoundPlay.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     }
 

}
