package IFS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FX implements EventHandler<ActionEvent>
{
    private Button export = new Button("Exportieren");
    private Button close = new Button("Programm beenden");
    private Button restart = new Button("Programm neustarten");

    public static FX instance()
    {
        return new FX();
    }

    public void menue()
    {
        VBox parent = new VBox();

        Label label = new Label("Benutzermenue");
        parent.getChildren().add(label);

        Label output = new Label(
                "Hier soll irgendwann einmal das Diagramm hinein gesetzt werden, dass dann e" +
                "xportiert werden koennen soll.");
        parent.getChildren().add(output);

        Image img = new Image("image.png");
        ImageView imgView = new ImageView(img);
        StackPane root = new StackPane();
        root.getChildren().add(imgView);

        export.setOnAction(this);
        parent.getChildren().add(export);

        close.setOnAction(this);
        parent.getChildren().add(close);

        restart.setOnAction(this);
        parent.getChildren().add(restart);

        Scene scene = new Scene(parent);
        Main.steuerung.getStage().setScene(scene);
    }

    @Override
    public void handle(ActionEvent event)
    {
        if(event.getSource().equals(restart))
        {
            Main.steuerung.restart();
        }
        else if(event.getSource().equals(export))
            System.out.println("Exportiere");
        else if(event.getSource().equals(close))
            System.exit(0);
    }
}
