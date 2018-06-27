package IFS;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main ist die Klasse welche dafuer zustaendig ist, das Programm zu starten und sorgt auch fuer die
 * problemlose Ausfuehrung von JavaFX.
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.0
 */

/*
    TODO Verwendung des JavaFX-Tools (FXML)
    TODO Eingabe der Parameter ueber JavaFX
    TODO Testprogramme erstellen
    TODO IFS-Tabelle aus Datei lesen
    TODO Farben der Farne waehlen
 */
public class Main extends Application
{
    public static Steuerung steuerung;
    public static Stage stage;

    /**
     * Main startet das Programm und ruft eine neue Instanz der Steuerung auf. Deren Methode
     * execute(boolean) startet dann die Algorithmen.
     * @param args Automatisch generiert (Main-Methode)
     */
    public static void main(String[] args)
    {
        steuerung = new Steuerung();
        steuerung.execute();
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
     * @param s Stage fuer das verwendete JavaFX
     */
    public void start(Stage s)
    {
        stage = s;
        try
        {
            steuerung.getMenue().menue();
        } catch(IOException e)
        {
            e.printStackTrace();
        }
        s.show();
    }
}