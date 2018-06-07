package IFS;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Steuerung
{
    private List<Farndata> tables;
    private int iterations;
    private String outputPath;
    private Stage stage;

    public Steuerung()
    {
        this.tables = new ArrayList<Farndata>();
        this.iterations = 20;
        this.outputPath = "";
    }

    public void execute()
    {
        InOut.instance().eingaben();
        fillTables();
        iterate();
        Main.launching();
    }

    public void restart()
    {
        this.stage.hide();
        this.tables.clear();
        InOut.instance().eingaben();
        fillTables();
        iterate();
        this.stage.show();
        FX.instance().menue();
        drawGraph();
    }

    public void drawGraph()
    {
        //TODO
        for(int i = 0; i < iterations; i++)
            System.out.println(
                    "X : " + tables.get(0).getGraph().getPoints().get(i).getX() + "...Y : " +
                    tables.get(0).getGraph().getPoints().get(i).getY());
    }

    private void iterate()
    {
        for(Farndata f : this.tables)
        {
            for(int i = 0; i < this.iterations; i++)
                f.getGraph().addPoint(f.berechnung());
        }
    }

    private void fillTables()
    {
        for(int i = 0; i < 4; i++)
            this.tables.add(new Farndata());
        this.tables.get(0).addData(0f, 0f, 0f, 0.16f, 0f, 0f, 0.03f);
        this.tables.get(0).addData(0.197f, -0.026f, 0.226f, 0.197f, 0f, 1.6f, 0.11f);
        this.tables.get(0).addData(-0.155f, 0.283f, 0.26f, 0.237f, 0f, 0.44f, 0.13f);
        this.tables.get(0).addData(0.849f, 0.037f, -0.037f, 0.849f, 0f, 1.6f, 0.73f);

        this.tables.get(1).addData(0f, 0f, 0f, 0.2f, 0f, 0f, 0.03f);
        this.tables.get(1).addData(0.2f, -0.3f, 0.23f, -0.05f, 0f, 0.5f, 0.09f);
        this.tables.get(1).addData(-0.05f, 0.23f, -0.3f, -0.2f, 0f, 0.5f, 0.11f);
        this.tables.get(1).addData(0.85f, 0f, 0f, 0.85f, 0f, 0.7f, 0.77f);

        this.tables.get(2).addData(0f, 0f, 0f, 0.16f, 0f, 0f, 0.03f);
        this.tables.get(2).addData(0.2f, -0.26f, 0f, 0.33f, 0f, 1.6f, 0.07f);
        this.tables.get(2).addData(0.65f, 0.28f, 0f, 0.34f, 0f, 0.44f, 0.18f);
        this.tables.get(2).addData(-0.85f, 0.04f, 0f, 0.85f, 0f, 1.6f, 0.72f);

        this.tables.get(3).addData(0.8f, 0f, 0f, 0.85f, 0.09f, 0.18f, 0.78f);
        this.tables.get(3).addData(-0.1f, 0.2f, -0.4f, 0.2f, 0.51f, 0.28f, 0.09f);
        this.tables.get(3).addData(-0.09f, -0.2f, 0.4f, 0.13f, 0.5f, 0f, 0.1f);
        this.tables.get(3).addData(0f, 0f, 0f, 0.2f, 0.46f, 0.02f, 0.03f);
    }

    public int getIterations()
    {
        return iterations;
    }

    public void setIterations(int iterations)
    {
        this.iterations = iterations;
    }

    public void setOutputPath(String outputPath)
    {
        this.outputPath = outputPath;
    }

    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
}
