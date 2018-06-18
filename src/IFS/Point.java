package IFS;

/**
 * Point ist eine Klasse die stark der Klasse java.awt.Point aehnelt. Tatsaechlich speichert diese
 * auch dieselben Werte. Point ist eine Klasse welche gleichnamige Instanzen beschreibt. Wir finden
 * jedoch in java.awt.Point keine Verwendung, da wir in unseren Berechnungen keine ganzzahligen
 * Punkte, sondern Punkte mit Koordinaten vom Datentyp float verwenden wollen. Die Point-Klasse
 * beschreibt Objekte mit den float-Eigenschaften x und y. Diese Variablen stehen für die Position
 * der Punkte im Koordinatensystem.
 */
public class Point
{
    private float x, y;

    /**
     * Die Point-Funktion ruft diese Klasse auf und fuellt die x und y Werte eines Punktes auf.
     * Da die Koordinaten sofort bekannt sind, muss die Funktion nicht ueberladen werden.
     * @param x             x-Koordinate auf dem Graph
     * @param y             y-Koordinate auf dem Graph
     */
    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Dieser Getter gibt x zurueck.
     * @return              x-Koordinate auf dem Graph
     */
    public float getX()
    {
        return x;
    }

    /**
     * Dieser Getter gibt y zurueck.
     * @return              y-Koordinate auf dem Graph
     */
    public float getY()
    {
        return y;
    }
}
