package IFS;

import javafx.scene.shape.Line;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Farndata ist eine Klasse, welche sich instanziieren laesst. Ein Objekt dieses Typs speichert alle
 * wichtigen Informationen ueber einen Farn. So also eine Liste der Parameter a - f und p, die ueber
 * Parameters(float, float, float, float, float, float, float) gefuellt werden kann. Zudem die Daten
 * des Graphs, also die Punkte und zugehoerigen Linien. Zuletzt wird aber auch noch ein Punkt als
 * Startwert gespeichert, also der Ausgangspunkt des Farns.
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.0
 */
public class Farndata
{
    private List<Parameters> data;
    private Graph graph;
    private Point start;
    private byte number;

    /**
     * Die Farndata-Methode ist der Konstruktor der Klasse. Er fuellt die drei Parameter des neuen
     * Objektes auf.
     */
    public Farndata()
    {
        this.data = new ArrayList<Parameters>();
        this.graph = new Graph();
        this.start = new Point(1_000, 1_000);
    }

    /**
     * Die AddData-Methode implementiert eine Zeile einer neuen Tabelle mit Parametern zur
     * Berechnung der Farne. Dabei wird diese Zeile mit Werten als neues Parameters-Objekt erstellt
     * und in die (data)-ArrayList eingesetzt.
     * @param a Parameter a
     * @param b Parameter b
     * @param c Parameter c
     * @param d Parameter d
     * @param e Parameter e
     * @param f Parameter f
     * @param p Parameter p
     */
    public void addData(float a, float b, float c, float d, float e, float f, float p)
    {
        this.data.add(new Parameters(a, b, c, d, e, f, p));
    }

    /**
     * Die Berechnung-Methode berechnet auf Grundlage der Parameter aus der Liste (data) mit einer
     * zufaelligen Auswahl aus dem vorherigen Punkt einen neuen Punkt. Zunaechst Setzt man einen
     * Wert (val) auf 0. Dann wird ein Random bestimmt. Auf Grundlage der Wahrscheinlichkeiten (p)
     * aus (data) ermittelt (val) so die erfolderlichen sechs Parameter. Daraufhin findet mit diesen
     * Werten die Berechnung des neuen Punktes statt. Dieser Punkt wird dann wieder als Startpunkt
     * in Farndata.start gespeichert und zudem an die Steuerung uebergeben.
     * @return neuer Punkt des Farns
     */
    public Point berechnung()
    {
        float val = 0;
        double r = Math.random();
        for(int i = 0; i < 4; i++)
        {
            val += this.data.get(i).getP();
            if(val > r)
            {
                val = i;
                number = (byte) val;
                break;
            }
        }
        this.start.setX((data.get((int) val).getA() + data.get((int) val).getC()) *
                        this.start.getX() + data.get((int) val).getE());
        this.start.setY((data.get((int) val).getB() + data.get((int) val).getD()) *
                        this.start.getY() + data.get((int) val).getF());
        return new Point(this.start.getX(), this.start.getY());
    }

    /**
     * Die GetGraphics-Methode visualisiert die Punkte und Linien aus dem in (graph) gespeicherten
     * Graph auf der Graphics2D Zeichenoberflaeche, die mit in Steuerung.image liegt. Erst wird die
     * Farbe bestimmt. Dann wird hier fuer jeden Punkt und jede Linie aus (graph) der exakte Punkt
     * auf der Oberflaeche in Pixeln berechnet und bei den Punkten als leerer Kreis und bei den
     * Linien als 2DLinie dargestellt. Damit wir Linien mit gebrochenen Zahlen als Koordinaten
     * verwenden koennen, nutzen wir Line2D. Damit man auch in kleinen Bereichen noch etwas erkennt,
     * werden nur die ersten 10 Punkte abgebildet. Die uebrigen Punkte lassen sich durch die Linien
     * zumindest erahnen, da die Enden der Linien genau auf den Punkten liegen.
     * @param graphics Graphics2D Zeichenoberflaeche, die zur Darstellung in JavaFX dient
     * @param color    Farbe, in der Punkte und Linien dargestellt werden sollen
     */
    public void getGraphics(Graphics2D graphics, Color color)
    {
        int i = 0;
        for(Point point : this.graph.getPoints())
        {
            i++;
            graphics.setColor(color);
            if(i <= 10)
                graphics.drawOval((int) Main.steuerung.getXOnGraph(point.getX() - 1),
                                  (int) Main.steuerung.getYOnGraph(point.getY() + 1), 3, 3);
        }
        for(Line line : this.graph.getLines())
            graphics.draw(new Line2D.Double(Main.steuerung.getXOnGraph(line.getStartX()),
                                            Main.steuerung.getYOnGraph(line.getStartY()),
                                            Main.steuerung.getXOnGraph(line.getEndX()),
                                            Main.steuerung.getYOnGraph(line.getEndY())));
    }

    /**
     * @return Graph mit gespeicherten Linien und Punkten
     */
    public Graph getGraph()
    {
        return graph;
    }

    /**
     * @return Nummer der verwendeten Zeile
     */
    public byte getNumber()
    {
        return number;
    }
}