package IFS;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * FX ist eine Klasse, die das FX-Menue beschreibt. Es handelt sich um eine Klasse, von der
 * Instanzen erstellt werden koennen. Diese implementiert zudem den javafx.event.EventHandler.
 * Objekte von FX besitzen jeweils 3 Eigenschaften. Button (export), der fuer das Exportieren des
 * Graphs als PNG-Datei zustaendig ist, Button (close), der fuer das Schliessen des Programms
 * verantwortlich ist und Button (restart), der fuer den Neustart des Programms verantwortlich ist.
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.1
 */
public class FX implements EventHandler<ActionEvent>
{
    private Button export = new Button("Exportieren");
    private Button close = new Button("Programm beenden");
    private Button restart = new Button("Programm neustarten");
    private Parent root;

    /**
     * Die FX-Methode bildet den Konstruktor der Klasse. Das entstandene Objekt speichert die
     * Buttons und kann das Menue ueber FX.menue() aufrufen.
     */
    public FX()
    {
    }

    /**
     * Die Menue-Methode ruft erstellt das Menue fuer JavaFX. Dieses wird als Scene in die Stage
     * Main.stage gespeichert. Zunaechst wird dafuer eine VBox erstellt. Daraufhin wird ein Label
     * erstellt, welches dann in die VBox (parent) kommt. Das Label ist eine Art Titel des Menues .
     * Als naechstes wird das Bild aus Steuerung.image FX-faehig gemacht (Umwandlung in FXImage)
     * ueber SwingFXUtils und ueber einen ImageView zur VBox hinzugefuegt. Dann werden noch die drei
     * Buttons zur VBox hinzugefuegt und die VBox wird zu einer Scene hinzugefuegt die dann zuletzt
     * noch in der Stage gesetzt wird.
     */
    public void menue() throws IOException
    {

        root = FXMLLoader.load(getClass().getResource("/IFS/fxml.fxml"));
        Main.stage.setScene(new Scene(root));

        /*VBox parent = new VBox();

        Label label = new Label("Benutzermenue");
        parent.getChildren().add(label);



        Image image = SwingFXUtils.toFXImage(Main.steuerung.getImage(), null);
        ImageView view = new ImageView(image);
        parent.getChildren().add(view);

        export.setOnAction(this);
        parent.getChildren().add(export);

        close.setOnAction(this);
        parent.getChildren().add(close);

        restart.setOnAction(this);
        parent.getChildren().add(restart);

        Scene scene = new Scene(parent);
        Main.stage.setScene(scene);*/
    }

    /**
     * Die Handle-Methode regelt das Event, wenn auf die Buttons geklickt wurde um abzufragen,
     * welcher Button genutzt wurde. Ist es Button restart, wird die Steuerung erneut ausgefuehrt,
     * ein neues Objekt davon erstellt und die Methode Steuerung.restart() ausgefuehrt. Beim Button
     * export, wird die Methode Steuerung.createFile() aufgerufen, die den Exportvorgang regelt. Und
     * der Button close beendet das Programm einfach.
     * @param event Genaue Informationen ueber das Event um zu wissen welcher Button benutzt wurde
     */
    @Override
    public void handle(ActionEvent event)
    {
        if(event.getSource().equals(restart))
        {
            Main.steuerung = new Steuerung();
        } else if(event.getSource().equals(export))
            Main.steuerung.createFile();
        else if(event.getSource().equals(close))
            System.exit(0);
    }

    public Parent getRoot()
    {
        return this.root;
    }
}