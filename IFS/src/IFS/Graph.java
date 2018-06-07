package IFS;

import java.util.ArrayList;
import java.util.List;

public class Graph
{
    private List<Point> points;

    public Graph()
    {
        this.points = new ArrayList<Point>();
    }

    public void addPoint(Point point)
    {
        this.points.add(point);
    }

    public List<Point> getPoints()
    {
        return points;
    }
}
