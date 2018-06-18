package IFS;

import javafx.scene.shape.Line;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Steuerung ist eine Klasse die gleichnamige Instanzen beschreibt. Diese besitzen verschiedene
 * Eigenschaften. Die Steuerung speichert Tabellen und Berechnungsergebnisse verschiedener Farne,
 * die Anzahl der Iterationen zur Berechnung dieser Farne, die Ausgabedatei im Falle eines Exports
 * , die darzustellende Bilddatei im PNG-Format, die Skalierung der beiden Achsen des
 * Koordinatensystems, die groessten- und kleinsten Werte innerhalb des Koordinatensystems und das
 * Anzeigemenue, dass die berechneten Daten grafisch darstellt.
 * @version 1.3
 * @since 1.0
 */
public class Steuerung
{
    private List<Farndata> tables;
    private int iterations;
    private File outputFile;
    private BufferedImage image;
    private float scaleX;
    private float scaleY;
    private float xmax = 0;
    private float ymax = 0;
    private float xmin = 0;
    private float ymin = 0;
    private FX menue;

    /**
     * Die Steuerung-Funktion ruft diese Klasse auf und fuellt die Klassenvariablen auf.
     */
    public Steuerung()
    {
        this.tables = new ArrayList<Farndata>();
        this.iterations = 0;
        this.outputFile = new File("default.txt");
        this.menue = new FX();
    }

    /**
     * Die Execute-Funktion startet das eigentliche Programm. Beginnt aber zunaechst mit der
     * Abfrage der Eingabewerte. Daraufhin werden die Tabellen mit vorgegebenen Werten gefuettert.
     * Mit iterate werden dann die Farne aufgrund der eingegebenen- und vorgegebenen Daten
     * berechnet. Daraufhin beginnt der grafische Teil des Programms, also die Erstellung des
     * Bildes. Hier wird zudem auch JavaFX aufgerufen und gestartet, bzw. das Menue geoeffnet und
     * angezeigt. Damit ist auch die Berechnung abgeschlossen. Danach muss der Nutzer entscheiden,
     * wie fortgefahren werden soll. Im Falle eines Neustarts kommt der Parameter (boolean) zum
     * Einsatz, da im Falle eines Neustarts die Funktion IFS.Main.start() zum zweiten Mal
     * aufgerufen wuerde, was jedoch nicht zulaessig ist.
     * @param restarted
     */
    public void execute(boolean restarted)
    {
        InOut.instance().eingaben("");
        fillTables();
        iterate();
        graphicalSolution();
        if(!restarted)
            Main.launching();
        else
            menue.menue();
    }

    /**
     * Die Restart-Funktion wird beim Wunsch nach einem Neustart angewendet und hat nur die
     * Funktion, execute(boolean) erneut auszufuehren, mit dem hinweis, dass hier ein Neustart
     * vorliegt.
     */
    public void restart()
    {
        execute(true);
    }

    /**
     * Die GraphicalSolution-Funktion wird von execute(boolean) ausgefuehrt, um das Bild fuer die
     * JavaFX-Ausgabe zu erstellen und moeglicherweise fuer den spaeteren Export als PNG-Datei.
     * Dabei wird das BufferedImage aus createGraph() als Klassenvariable gesetzt. Die Funktion
     * drawGraph() ist nach der Fertigstellung des Programms ueberfluessig und entsprechend
     * markiert.
     */
    public void graphicalSolution()
    {
        drawGraph();
        this.image = createImage();
    }

    @PreDestroy
    private void drawGraph()
    {
        for(int i = 0; i < iterations; i++)
            for(int j = 0; j < 4; j++)
                System.out.println(
                        "X : " + tables.get(j).getGraph().getPoints().get(i).getX() + "...Y : " +
                        tables.get(j).getGraph().getPoints().get(i).getY());
    }

    /**
     * Die CreateImage-Funktion erstellt das Bild, welches fuer die JavaFX-Ausgabe erforderlich
     * ist. Dafuer wird erstmal das BufferedImage selber erstellt, ohne Inhalt zu besitzen.
     * Daraufhin bekommt es eine Graphics2D zur Bearbeitung der Flaeche. Die Funktion
     * createCoordinates(Graphics2D) erstellt x- und y-Achse des Koordinatensystems. Zuletzt werden
     * Punkte und Linien der berechneten Farne auf das Bild aufgetragen. Dies geschieht durch die
     * Instanzfunktionen der Klasse IFS.Farndata IFS.Farndata.getGraphics(Graphics2D, Color).
     * @return              fertiges BufferedImage zur Darstellung in JavaFX.
     */
    private BufferedImage createImage()
    {
        BufferedImage image =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                                   .getDefaultConfiguration().createCompatibleImage(960, 540);
        Graphics2D graphics = image.createGraphics();
        createCoordinates(graphics);
        tables.get(0).getGraphics(graphics, Color.CYAN);
        tables.get(1).getGraphics(graphics, Color.RED);
        tables.get(2).getGraphics(graphics, Color.GREEN);
        tables.get(3).getGraphics(graphics, Color.YELLOW);
        /*for(Farndata farndata : this.tables)
        {
            farndata.getGraphics(graphics, Color.WHITE);
        }*/
        return image;
    }

    /**
     * Die CreateCoordinates-Funktion erstellt die Achsen des Koordinatensystems auf der Graphics2D-
     * Oberflaeche, die als Parameter mitgegeben wird. Gefolgt davon wird die Skalierung der Achsen
     * berechnet und ebenfalls als Klassenvariable gespeichert. Dies geschieht sowohl fuer die eine
     * , als auch fuer die andere Achse. Zuletzt werden auf Grundlage der Minimal- und Maximalwerte
     * die Achsen gezeichnet.
     * @param graphics      Liefert die Zeichenoberflaeche
     */
    private void createCoordinates(Graphics2D graphics)
    {
        this.scaleX = 900 / (xmax + Math.abs(xmin));
        this.scaleY = 500 / (ymax + Math.abs(ymin));
        int diffx = (int) (900 * (Math.abs(xmin) / (Math.abs(xmin) + xmax)));
        int diffy = (int) (500 * (ymax / (ymax + Math.abs(ymin))));
        graphics.drawLine(30, 20 + diffy, 930, 20 + diffy);   // x-Achse
        graphics.drawLine(30 + diffx, 20, 30 + diffx, 520);     // y-Achse
        // TODO Achsen muessen noch beschriftet werden
    }

    /**
     * Die GetXOnGraph-Funktion hat die Aufgabe errechnete x-Werte so umzurechnen, dass sie fuer
     * das Koordinatensystem brauchbar sind und korrekt dargestellt werden koennen.
     * @param x             Liefert das mit Algorithmen errechnete x-Wert eines Punktes
     * @return              x-Wert, der fuer die Darstellung im Koordinatensystem geeignet ist
     */
    public double getXOnGraph(double x)
    {
        if(x >= 0)
            return (Math.abs(xmin) + x) * scaleX + 30;
        else
            return (Math.abs(xmin - x)) * scaleX + 30;
    }

    /**
     * Die GetYOnGraph-Funktion hat die Aufgabe errechnete y-Werte so umzurechnen, dass sie fuer
     * das Koordinatensystem brauchbar sind und korrekt dargestellt werden koennen.
     * @param y             Liefert das mit Algorithmen errechnete y-Wert eines Punktes
     * @return              y-Wert, der fuer die Darstellung im Koordinatensystem geeignet ist
     */
    public double getYOnGraph(double y)
    {
        if(y >= 0)
            return 520 - y * scaleY;
        else
            return 520 - (xmax + Math.abs(y)) * scaleY;
    }

    /**
     * Die CreateFile-Funktion soll zur Erstellung der PNG-Datei genutzt werden. Das ist Relevant,
     * wenn der Nutzer des Programms auf der JavaFX-Oberflaeche auf 'Exportieren' klickt. Diese
     * Funktion wird von IFS.FX.handle() aufgerufen. Da ja die moegliche Datei als Klassenvariable
     * gespeichert wird laesst diese sich hier aufrufen. So wird zunaechst die Datei erstellt, wenn
     * sie nicht bereits existiert, und das Bild wird ueber javax.imageio.ImageIO in die Datei
     * geschrieben. Da moeglicherweise die Datei bereits existieren koennte wird mit einem
     * Try-Catch die FileAlreadyExistsException abgefangen. Ist die Datei kein Bild, wuerde es
     * einen weiteren Fehler geben. Daher verwendet man dort zusaetzlich noch eine IOException.
     */
    public void createFile()
    {
        try
        {
            outputFile.createNewFile();
            ImageIO.write(this.image, "png", outputFile);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Die Iterate-Funktion fuehrt auf Grundlage der Formel aus IFS.Farndata.berechnung() die
     * Berechnung der Punkte aus. Die geschieht zunächst fuer jede einzelne Iteration innerhalb
     * jedes Farns. Die Ergebnisse der Punkte werden bei jedem Graph als Punkt hinzugefuegt. Diese
     * Funktion berechnet nun die Minimal- und Maximalwerte der Daten und speichert diese weiter
     * oben als Klassenvariablen. Zudem wird noch fuer jede Iteration die entsprechende Linie
     * errechnet, welcher die Punkte verbinden soll. Diese wird auch zu der Menge der Linien des
     * Graph des entsprechenden Farnes hinzugefuegt.
     */
    private void iterate()
    {
        for(Farndata f : this.tables)
        {
            for(int i = 0; i < this.iterations; i++)
            {
                Point point = f.berechnung();
                f.getGraph().addPoint(point);
                if(point.getY() > ymax)
                    ymax = point.getY();
                if(point.getX() > xmax)
                    xmax = point.getX();
                if(point.getX() < xmin)
                    xmin = point.getX();
                if(point.getY() < ymin)
                    ymin = point.getY();
            }
            for(int i = 1; i < this.iterations; i++)
            {
                Line line = new Line(f.getGraph().getPoints().get(i - 1).getX(),
                                     f.getGraph().getPoints().get(i - 1).getY(),
                                     f.getGraph().getPoints().get(i).getX(),
                                     f.getGraph().getPoints().get(i).getY());
                f.getGraph().addLine(line);
            }
        }
    }

    /**
     * Die FillTables-Funktion wird zu Beginn mit execute() aufgerufen. Diese Funktion ist dafuer
     * verantwortlich, die Parameter fuer die Berechnung zu fuellen. Davor werden jedoch noch die
     * unterschiedlichen Farne erstellt.
     */
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

    /**
     * Dieser Setter setzt die Anzahl der zu durchlaufenden Iterationen.
     * @param iterations    Anzahl der Iterationen
     */
    public void setIterations(int iterations)
    {
        this.iterations = iterations;
    }

    /**
     * Dieser Getter gibt die Ausgabedatei zurueck.
     * @return              File der Ausgabedatei
     */
    public File getOutputFile()
    {
        return outputFile;
    }

    /**
     * Dieser Setter setzt den File fuer die Ausgabedatei
     * @param file          File der Ausgabedatei
     */
    public void setOutputFile(File file)
    {
        this.outputFile = file;
    }

    /**
     * Dieser Getter gibt das BufferedImage des Bildes zurueck.
     * @return              BufferedImage des Bildes
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Dieser Getter gibt das FX das fuer das Menue zustaendig ist zurueck.
     * @return              FX fuer das Menue
     */
    public FX getMenue()
    {
        return menue;
    }
}