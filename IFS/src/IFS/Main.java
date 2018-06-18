package IFS;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main ist die Klasse welche dafuer zustaendig ist, das Programm zu starten und sorgt auch fuer
 * die problemlose Ausfuehrung von JavaFX.
 * @version 1.3
 * @since 1.0
 */
public class Main extends Application
{
    public static Steuerung steuerung;
    public static Stage stage;

    /**
     * Main startet das Programm und ruft eine neue Instanz der Steuerung auf. Deren Funktion
     * execute(boolean) startet dann die Algorithmen.
     * @param args
     */
    public static void main(String[] args)
    {
        steuerung = new Steuerung();
        steuerung.execute(false);
    }

    /**
     * Launching dient zum Starten der JavaFX-Funktion.
     */
    public static void launching()
    {
        launch();
    }

    /**
     * Start wurde von Application implementiert. Diese Funktion ist essentiell zur Erstellung bzw.
     * Nutzung von JavaFX.
     * @param s
     */
    public void start(Stage s)
    {
        stage = s;
        steuerung.getMenue().menue();
        Main.steuerung.setIterations(0);
        s.show();
    }
}