package IFS;

/**
 * Tests ist die Klasse welche dafuer zustaendig ist, die in der Aufgabe angegebenen Tests
 * durchzufuehren.
 *
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v2.0
 */
public class Tests
{
    /**
     * Die Instance-Methode fungiert als Konstruktor der Klasse. Darueber koennen weitere Methoden
     * der Klasse aufgerufen werden.
     *
     * @return Instanz der Klasse
     */
    public static Tests instance()
    {
        return new Tests();
    }

    /**
     * Bei diesem Test sollen alle Punkte die gleiche Farbe tragen.
     */
    public void testEins()
    {
        Main.steuerung.getColors().clear();
        Main.steuerung.getColors().add("pink");
    }

    /**
     * Bei diesem Test soll fuer jede Regel (Zeile in der Tabelle) eine unterschiedliche Farbe
     * verwendet werden.
     */
    public void testZwei()
    {
        Main.steuerung.getColors().clear();
        Main.steuerung.getColors().add("pink");
        Main.steuerung.getColors().add("weiß");
        Main.steuerung.getColors().add("cyan");
        Main.steuerung.getColors().add("grün");
    }

}
