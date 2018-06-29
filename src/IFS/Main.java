package IFS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main ist die Klasse welche dafuer zustaendig ist, das Programm zu starten und sorgt auch fuer die
 * problemlose Ausfuehrung von JavaFX.
 *
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.0
 */
public class Main extends Application
{
    public static Stage stage;
    public static Steuerung steuerung;

    /**
     * Main startet das Programm und ruft eine neue Instanz der Steuerung auf. Deren Methode
     * execute(boolean) startet dann die Algorithmen.
     *
     * @param args Automatisch generiert (Main-Methode)
     */
    public static void main(String[] args)
    {
        steuerung = new Steuerung();
        steuerung.execute(false);
    }

    /**
     * Die Launching-Methode dient zum Starten der JavaFX-Methode.
     */
    public static void launching()
    {
        launch();
    }

    /**
     * Die Start-Methode wurde von Application implementiert. Diese Methode ist essentiell zur
     * Erstellung bzw. Nutzung von JavaFX.
     *
     * @param s Stage fuer das verwendete JavaFX
     */
    public void start(Stage s)
    {
        stage = s;
        try
        {
            Main.steuerung.setRoot(FXMLLoader.load(getClass().getResource("/IFS/fxml.fxml")));
            Main.stage.setScene(new Scene(Main.steuerung.getRoot()));
        } catch(IOException e)
        {
            e.printStackTrace();
        }
        s.show();
    }
}