package View;

import Controller.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Charpignon Thibault on 18/03/2016.
 */
public class VersusPaintView extends View{

    public int percent_Paint_By_Man, percent_Paint_By_Girl;

    private StackPane root;

    private VersusPaintController c;

    public VersusPaintView(){
        super();
        c = new VersusPaintController(this);
        percent_Paint_By_Man=0;
        percent_Paint_By_Girl=0;
    }

    @Override
    public void start(Stage stage) {
        root = new StackPane();

    }
}
