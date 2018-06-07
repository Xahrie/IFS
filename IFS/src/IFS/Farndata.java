package IFS;

import java.util.ArrayList;
import java.util.List;

public class Farndata
{
    private List<Parameters> data;
    private Graph graph;
    private float x, y;

    public Farndata()
    {
        this.data = new ArrayList<Parameters>();
        this.graph = new Graph();
        this.x = 1000;
        this.y = 1000;
    }

    public void addData(float a, float b, float c, float d, float e, float f, float p)
    {
        this.data.add(new Parameters(a, b, c, d, e, f, p));
    }

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
                break;
            }
        }
        this.x = (data.get((int) val).getA() + data.get((int) val).getC()) * this.x +
                 data.get((int) val).getE();
        this.y = (data.get((int) val).getB() + data.get((int) val).getD()) * this.y +
                 data.get((int) val).getF();
        return new Point(this.x, this.y);
    }

    public Graph getGraph()
    {
        return graph;
    }
}