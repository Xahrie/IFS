package IFS;

import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Graph
{
    private List<Point> points;
    private List<Line> lines;

    public Graph()
    {
        this.points = new ArrayList<Point>();
        this.lines = new ArrayList<Line>();
        this.lines = new ArrayList<Line>();
    }

    public void addPoint(Point point)
    {
        this.points.add(point);
    }

    public List<Point> getPoints()
    {
        return points;
    }

    public void addLine(Line line)
    {
        this.lines.add(line);
    }

    public List<Line> getLines()
    {
        return lines;
    }
}
