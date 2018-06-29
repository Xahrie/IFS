package IFS;

import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Graph ist eine Klasse, welche sich instanziieren laesst. Objekte dieses Typs speichern zwei
 * Listen. Eine mit Punkten und eine mit Linien. Da werden die Daten gespeichert, die bei
 * Farndata.getGraphics(Graphics2D, Color) visualisiert werden sollen.
 *
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.0
 */
public class Graph
{
    private List<Line> lines;
    private List<Point> points;

    /**
     * Die Graph-Methode ist der Konstruktor der Klasse. Dieser fuellt die Variablen der Klasse so
     * auf, dass neue ArrayLists entstehen.
     */
    public Graph()
    {
        this.points = new ArrayList<Point>();
        this.lines = new ArrayList<Line>();
    }

    /**
     * @param point Neuer Punkt mit 2 Koordinaten wird zu (points) hinzugefuegt
     */
    public void addPoint(Point point)
    {
        this.points.add(point);
    }

    /**
     * @return alle Punkte dieses Graphs aus (points)
     */
    public List<Point> getPoints()
    {
        return points;
    }

    /**
     * @param line Neue Linie mit 2 Punkten wird zu (lines) hinzugefuegt
     */
    public void addLine(Line line)
    {
        this.lines.add(line);
    }

    /**
     * @return alle Linien dieses Graphs aus (lines)
     */
    public List<Line> getLines()
    {
        return lines;
    }
}