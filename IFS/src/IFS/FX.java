package IFS;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class FX implements EventHandler<ActionEvent>
{
    private Button export = new Button("Exportieren");
    private Button close = new Button("Programm beenden");
    private Button restart = new Button("Programm neustarten");

    public FX()
    {
    }

    public void menue()
    {
        VBox parent = new VBox();

        Label label = new Label("Benutzermenue");
        parent.getChildren().add(label);

        Label output = new Label("Hier soll irgendwann einmal das Diagramm hinein gesetzt werden" +
                                 ", dass dann exportiert werden koennen soll.");
        parent.getChildren().add(output);

        /*if(Main.steuerung.getOutputFile().exists())
        {
            System.out.print(Main.steuerung.getOutputFile().getPath().substring(
                    Main.steuerung.getOutputFile().getPath().indexOf("dateien\\")));

            //Image image = new Image(Main.steuerung.getOutputFile().getPath().substring(
            //        Main.steuerung.getOutputFile().getPath().indexOf("dateien\\")));
            //Image image = new Image((Main.steuerung.getOutputFile().getPath().substring(
            //      Main.steuerung.getOutputFile().getPath().indexOf("dateien\\"))));
            //Image image2 = new Image(Main.steuerung.getOutputFile().getPath());
            Image image = new Image("dateien\\image.png");
            ImageView view = new ImageView(image);
            parent.getChildren().add(view);
        }*/

        Image image = SwingFXUtils.toFXImage(Main.steuerung.getImage()
                , null);
        ImageView view = new ImageView(image);
        parent.getChildren().add(view);

        export.setOnAction(this);
        parent.getChildren().add(export);

        close.setOnAction(this);
        parent.getChildren().add(close);

        restart.setOnAction(this);
        parent.getChildren().add(restart);

        Scene scene = new Scene(parent);
        Main.stage.setScene(scene);
    }

    @Override
    public void handle(ActionEvent event)
    {
        if(event.getSource().equals(restart))
        {
            Main.steuerung = new Steuerung();
            Main.steuerung.restart();
        }
        else if(event.getSource().equals(export))
            Main.steuerung.createFile();
        else if(event.getSource().equals(close))
            System.exit(0);
    }
}
