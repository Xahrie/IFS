package IFS;

/**
 * Point ist eine Klasse die stark der Klasse java.awt.Point aehnelt. Tatsaechlich speichert diese
 * auch dieselben Werte. Point ist eine Klasse welche gleichnamige Instanzen beschreibt. Wir finden
 * jedoch in java.awt.Point keine Verwendung, da wir in unseren Berechnungen keine ganzzahligen
 * Punkte, sondern Punkte mit Koordinaten vom Datentyp float verwenden wollen. Die Point-Klasse
 * beschreibt Objekte mit den float-Eigenschaften x und y. Diese Variablen stehen fuer die Position
 * der Punkte im Koordinatensystem.
 *
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.0
 */
public class Point
{
    private float x, y;

    /**
     * Die Point-Methode ruft diese Klasse auf und fuellt die x und y Werte eines Punktes auf. Da
     * die Koordinaten sofort bekannt sind, muss die Methode nicht ueberladen werden.
     *
     * @param x x-Koordinate auf dem Graph
     * @param y y-Koordinate auf dem Graph
     */
    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return x-Koordinate auf dem Graph
     */
    public float getX()
    {
        return x;
    }

    /**
     * @param x x-Koordinate auf dem Graph
     */
    public void setX(float x)
    {
        this.x = x;
    }

    /**
     * @return y-Koordinate auf dem Graph
     */
    public float getY()
    {
        return y;
    }

    /**
     * @param y y-Koordinate auf dem Graph
     */
    public void setY(float y)
    {
        this.y = y;
    }
}