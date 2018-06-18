package IFS;

/**
 * Parameters ist eine Klasse die gleichnamige Instanzen beschreibt. Diese besitzen verschiedene
 * Eigenschaften. Die Parameters speichert Parameter, die zur Berechnung der Farne dienen. Diese
 * Parameter tragen die Namen a, b, c, d, e, f und p fuer die Wahrscheinlichkeit. Diese werden aus
 * den Tabellen uebernommen und eingelesen.
 */
public class Parameters
{
    private float a, b, c, d, e, f, p;

    /**
     * Die Parameters-Funktion ruft diese Klasse auf und fuellt die einzelnen Parameter zur
     * Berechnung auf.
     */
    public Parameters(float a, float b, float c, float d, float e, float f, float p)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.p = p;
    }

    /**
     * Dieser Getter gibt a zurueck.
     * @return              Parameter a
     */
    public float getA()
    {
        return a;
    }

    /**
     * Dieser Getter gibt b zurueck.
     * @return              Parameter b
     */
    public float getB()
    {
        return b;
    }

    /**
     * Dieser Getter gibt c zurueck.
     * @return              Parameter c
     */
    public float getC()
    {
        return c;
    }

    /**
     * Dieser Getter gibt d zurueck.
     * @return              Parameter d
     */
    public float getD()
    {
        return d;
    }

    /**
     * Dieser Getter gibt e zurueck.
     * @return              Parameter e
     */
    public float getE()
    {
        return e;
    }

    /**
     * Dieser Getter gibt f zurueck.
     * @return              Parameter f
     */
    public float getF()
    {
        return f;
    }

    /**
     * Dieser Getter gibt p zurueck.
     * @return              Parameter p
     */
    public float getP()
    {
        return p;
    }
}
