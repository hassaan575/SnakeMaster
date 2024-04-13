import java.io.IOException;

/*Must have at least one class that creates a custom exception that
makes sense for your game.*/
public class MyCustomFileNotFoundException extends IOException {

    public MyCustomFileNotFoundException(String message) {
        super(message);
        System.out.print(message);

    }
}
