package IFS;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Steuerung ist eine Klasse die gleichnamige Instanzen beschreibt. Diese besitzen verschiedene
 * Eigenschaften. Die Steuerung speichert Tabellen und Berechnungsergebnisse verschiedener Farne,
 * die Anzahl der Iterationen zur Berechnung dieser Farne, die Ausgabedatei im Falle eines Exports,
 * die darzustellende Bilddatei im PNG-Format, die Skalierung der beiden Achsen des
 * Koordinatensystems, die groessten- und kleinsten Werte innerhalb des Koordinatensystems und das
 * Anzeigemenue, dass die berechneten Daten grafisch darstellt.
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.0
 */
public class Steuerung
{
    private List<Farndata> tables;
    private Farndata custom;
    private int iterations;
    private File outputFile;
    private BufferedImage image;
    private float scaleX;
    private float scaleY;
    private float xMax = 0;
    private float yMax = 0;
    private float xMin = 0;
    private float yMin = 0;
    private Parent root;
    private List<String> colors;

    /**
     * Die Steuerung-Methode ruft diese Klasse auf und fuellt die Klassenvariablen auf.
     */
    public Steuerung()
    {
        this.iterations = 0;
        this.outputFile = new File("default.txt");
        this.colors = new ArrayList<String>();
    }

    /**
     * Die Execute-Methode startet das eigentliche Programm. Beginnt aber zunaechst mit der Abfrage
     * der Eingabewerte. Daraufhin werden die Tabellen mit vorgegebenen Werten gefuettert. Mit
     * iterate werden dann die Farne aufgrund der eingegebenen- und vorgegebenen Daten berechnet.
     * Daraufhin beginnt der grafische Teil des Programms, also die Erstellung des Bildes. Hier wird
     * zudem auch JavaFX aufgerufen und gestartet, bzw. das Menue geoeffnet und angezeigt. Damit ist
     * auch die Berechnung abgeschlossen. Danach muss der Nutzer entscheiden, wie fortgefahren
     * werden soll. Im Falle eines Neustarts kommt der Parameter (boolean) zum Einsatz, da im Falle
     * eines Neustarts die Methode IFS.Main.start() zum zweiten Mal aufgerufen wuerde, was jedoch
     * nicht zulaessig ist.
     */
    public void execute(boolean restart)
    {
        this.tables = new ArrayList<Farndata>();
        fillTables();
        if(!restart)
        {
            Main.launching();
            try
            {
                root = FXMLLoader.load(getClass().getResource("/IFS/fxml.fxml"));
                Main.stage.setScene(new Scene(root));
            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dabei wird das BufferedImage aus createGraph() als Klassenvariable gesetzt. Die Methode
     * drawGraph() ist nach der Fertigstellung des Programms ueberfluessig und entsprechend
     * markiert.
     */
    public void graphicalSolution()
    {
        test((byte) 0);
        this.image = createImage();
    }

    /**
     * Die CreateImage-Methode erstellt das Bild, welches fuer die JavaFX-Ausgabe erforderlich ist.
     * Dafuer wird  erst einmal das BufferedImage selber erstellt, ohne Inhalt zu besitzen.
     * Daraufhin bekommt es eine Graphics2D zur Bearbeitung der Flaeche. Die Methode
     * createCoordinates(Graphics2D) erstellt x- und y-Achse des Koordinatensystems. Zuletzt werden
     * Punkte und Linien der berechneten Farne auf das Bild aufgetragen. Dies geschieht durch die
     * Instanzmethoden der Klasse IFS.Farndata IFS.Farndata.getGraphics(Graphics2D, Color).
     * @return fertiges BufferedImage zur Darstellung in JavaFX.
     */
    private BufferedImage createImage()
    {
        BufferedImage image =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                                   .getDefaultConfiguration().createCompatibleImage(960, 540);
        Graphics2D graphics = image.createGraphics();
        createCoordinates(graphics);
        if(this.custom == null)
        {
            assert false;
            if(colors.size() == 4)
            {
                tables.get(0).getGraphics(graphics, getColorFromString(
                        colors.get(tables.get(0).getNumber())));
                tables.get(1).getGraphics(graphics, getColorFromString(
                        colors.get(tables.get(1).getNumber())));
                tables.get(2).getGraphics(graphics, getColorFromString(
                        colors.get(tables.get(2).getNumber())));
                tables.get(3).getGraphics(graphics, getColorFromString(
                        colors.get(tables.get(3).getNumber())));
            } else if(colors.size() == 1)
            {
                tables.get(0).getGraphics(graphics, getColorFromString(colors.get(0)));
                tables.get(1).getGraphics(graphics, getColorFromString(colors.get(0)));
                tables.get(2).getGraphics(graphics, getColorFromString(colors.get(0)));
                tables.get(3).getGraphics(graphics, getColorFromString(colors.get(0)));
            } else
            {
                tables.get(0).getGraphics(graphics, Color.CYAN);
                tables.get(1).getGraphics(graphics, Color.RED);
                tables.get(2).getGraphics(graphics, Color.GREEN);
                tables.get(3).getGraphics(graphics, Color.YELLOW);
            }
        } else
        {
            if(colors.size() == 4)
                custom.getGraphics(graphics, getColorFromString(colors.get(custom.getNumber())));
            else if(colors.size() == 1)
                custom.getGraphics(graphics, getColorFromString(colors.get(0)));
            else
                custom.getGraphics(graphics, Color.GREEN);
        }
        return image;
    }

    /**
     * Die CreateCoordinates-Methode erstellt die Achsen des Koordinatensystems auf der Graphics2D-
     * Oberflaeche, die als Parameter mitgegeben wird. Gefolgt davon wird die Skalierung der Achsen
     * berechnet und ebenfalls als Klassenvariable gespeichert. Dies geschieht sowohl fuer die eine,
     * als auch fuer die andere Achse. Zuletzt werden auf Grundlage der Minimal- und Maximalwerte
     * die Achsen gezeichnet.
     * @param graphics Liefert die Zeichenoberflaeche
     */
    private void createCoordinates(Graphics2D graphics)
    {
        this.scaleX = 900 / (xMax + Math.abs(xMin));
        this.scaleY = 500 / (yMax + Math.abs(yMin));
        int diffX = (int) (900 * (Math.abs(xMin) / (Math.abs(xMin) + xMax)));
        int diffY = (int) (500 * (yMax / (yMax + Math.abs(yMin))));
        graphics.drawLine(30, 20 + diffY, 930, 20 + diffY);   // x-Achse
        graphics.drawLine(30 + diffX, 20, 30 + diffX, 520);   // y-Achse
        // TODO Achsen muessen noch beschriftet werden
    }

    /**
     * Die GetXOnGraph-Methode hat die Aufgabe errechnete x-Werte so umzurechnen, dass sie fuer das
     * Koordinatensystem brauchbar sind und korrekt dargestellt werden koennen.
     * @param x Liefert das mit Algorithmen errechnete x-Wert eines Punktes
     * @return x-Wert, der fuer die Darstellung im Koordinatensystem geeignet ist
     */
    public double getXOnGraph(double x)
    {
        if(x >= 0)
            return (Math.abs(xMin) + x) * scaleX + 30;
        else
            return (Math.abs(xMin - x)) * scaleX + 30;
    }

    /**
     * Die GetYOnGraph-Methode hat die Aufgabe errechnete y-Werte so umzurechnen, dass sie fuer das
     * Koordinatensystem brauchbar sind und korrekt dargestellt werden koennen.
     * @param y Liefert das mit Algorithmen errechnete y-Wert eines Punktes
     * @return y-Wert, der fuer die Darstellung im Koordinatensystem geeignet ist
     */
    public double getYOnGraph(double y)
    {
        if(y >= 0)
            return 520 - y * scaleY;
        else
            return 520 - (xMax + Math.abs(y)) * scaleY;
    }

    /**
     * Die CreateFile-Methode soll zur Erstellung der PNG-Datei genutzt werden. Das ist Relevant,
     * wenn der Nutzer des Programms auf der JavaFX-Oberflaeche auf 'Exportieren' klickt. Diese
     * Methode wird von IFS.FX.handle() aufgerufen. Da ja die moegliche Datei als Klassenvariable
     * gespeichert wird laesst diese sich hier aufrufen. So wird zunaechst die Datei erstellt, wenn
     * sie nicht bereits existiert, und das Bild wird ueber javax.imageio .ImageIO in die Datei
     * geschrieben. Ist die Datei kein Bild, wuerde es einen weiteren Fehler geben. Daher man
     * verwendet man dort zusaetzlich noch eine IOException.
     * @throws IOException wenn die Datei bereits existiert, gibt es einen Fehler
     */
    public void createFile()
    {
        try
        {
            ImageIO.write(this.image, "png", outputFile);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Die Iterate-Methode fuehrt auf Grundlage der Formel aus IFS.Farndata.berechnung() die
     * Berechnung der Punkte aus. Die geschieht zunaechst fuer jede einzelne Iteration innerhalb
     * jedes Farns. Die Ergebnisse der Punkte werden bei jedem Graph als Punkt hinzugefuegt. Diese
     * Methode berechnet nun die Minimal- und Maximalwerte der Daten und speichert diese weiter oben
     * als Klassenvariablen. Zudem wird noch fuer jede Iteration die entsprechende Linie errechnet,
     * welcher die Punkte verbinden soll. Diese wird auch zu der Menge der Linien des Graph des
     * entsprechenden Farnes hinzugefuegt.
     */
    public void iterate(boolean customData)
    {
        if(iterations != 0)
        {
            if(!customData)
            {
                for(Farndata f : this.tables)
                {
                    calculatePoints(f);
                    for(int i = 1; i < this.iterations; i++)
                    {
                        Line line = new Line(f.getGraph().getPoints().get(i - 1).getX(),
                                             f.getGraph().getPoints().get(i - 1).getY(),
                                             f.getGraph().getPoints().get(i).getX(),
                                             f.getGraph().getPoints().get(i).getY());
                        f.getGraph().addLine(line);
                    }
                }

            } else
            {
                calculatePoints(custom);
                for(int i = 1; i < this.iterations; i++)
                {
                    Line line = new Line(custom.getGraph().getPoints().get(i - 1).getX(),
                                         custom.getGraph().getPoints().get(i - 1).getY(),
                                         custom.getGraph().getPoints().get(i).getX(),
                                         custom.getGraph().getPoints().get(i).getY());
                    custom.getGraph().addLine(line);
                }
            }
        }
    }

    /**
     * Die Calculate-Points Methode hat die Aufgabe, die Punkte auf dem Graph zu berechnen und dort
     * zu speichern. Es werden dort auch Minimal- und Maximalwerte bestimmt.
     * @param data
     */
    private void calculatePoints(Farndata data)
    {
        for(int i = 0; i < this.iterations; i++)
        {
            Point point = data.berechnung();
            data.getGraph().addPoint(point);
            if(point.getY() > yMax)
                yMax = point.getY();
            if(point.getX() > xMax)
                xMax = point.getX();
            if(point.getX() < xMin)
                xMin = point.getX();
            if(point.getY() < yMin)
                yMin = point.getY();
        }
    }

    /**
     * Die FillTables-Methode wird zu Beginn mit execute() aufgerufen. Diese Methode ist dafuer
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
     * Die GenerateData-Methode hat die Aufgabe, ein Farndata-Objekt aus der Input-TXT Datei zu
     * erstellen. Dabei liest ein BufferedReader Zeile fuer Zeile und sucht sich aus den Zeilen die
     * Zahlen heraus. Diese werden dann zur Tabelle dieses Farns hinzugefuegt.
     * @param file Input Datei mit den Daten
     * @throws IOException           Fehler beim Lesen der Dateien oder wenn diese nicht existiert
     * @throws NumberFormatException Falls es nicht moeglich ist, einen Float zu generieren
     */
    public void generateData(File file) throws IOException, NumberFormatException
    {
        this.custom = new Farndata();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getPath()));
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < 4; i++)
        {
            List<Float> values = new ArrayList<Float>();
            line.append(bufferedReader.readLine());
            for(int j = 0; j < 6; j++)
            {
                values.add(Float.valueOf(line.substring(0, line.indexOf(" "))));
                line.delete(0, line.indexOf(" ") + 1);
            }
            values.add(Float.valueOf(line.toString()));
            custom.addData(values.get(0), values.get(1), values.get(2), values.get(3),
                           values.get(4), values.get(5), values.get(6));
            line = new StringBuilder();
        }
        bufferedReader.close();
    }

    /**
     * In der FileToTXT-Methode wird die Eingabe der Input Datei so umgewandelt, dass es sich um
     * einen TXT-Dateipfad handelt. Dabei wird der String genommen und alles bis zum '.' gekuerzt .
     * Dann wird ein '.txt' angehangen. Ist kein . vorhanden muss auch nichts gekuerzt werden.
     * @param fileString Eingabe des Nutzers, wie die Datei heisst
     * @return korrekter Dateipfad
     */
    public String fileToTXT(String fileString)
    {
        String outputPath = fileString;
        if(fileString.contains("."))
            outputPath = outputPath.substring(0, outputPath.indexOf('.'));
        outputPath = outputPath + ".txt";
        return outputPath;
    }

    /**
     * In der FileToPNG-Methode wird die Eingabe der zu erstellenden Datei so umgewandelt, dass es
     * sich um einen PNG-Dateipfad handelt. Dabei wird der String genommen und alles bis zum '.'
     * gekuerzt. Dann wird ein '.png' angehangen. Ist kein . vorhanden muss auch nichts gekuerzt
     * werden.
     * @param fileString Eingabe des Nutzers, wie die Datei heissen soll
     * @return korrekter Dateipfad
     */
    public String fileToPNG(String fileString)
    {
        String outputPath = fileString;
        if(fileString.contains("."))
            outputPath = outputPath.substring(0, outputPath.indexOf('.'));
        outputPath = outputPath + ".png";
        return outputPath;
    }

    /**
     * Die Test-Methode ruft die beiden Tests auf.
     * @param kind Art des Tests (Optionen: 0 -> kein Test; 1 -> Test 1; 2 -> Test 2)
     */
    private void test(byte kind)
    {
        if(kind == 1)
            Tests.instance().testEins();
        else if(kind == 2)
            Tests.instance().testZwei();
    }

    /**
     * In der IsANumber-Methode wird geprueft, ob es sich bei der eingegebenen Zahl wirklich um eine
     * Zahl, oder etwas anderes handelt.
     * @param number
     * @return
     */
    public boolean isANumber(String number)
    {
        try
        {
            int i = Integer.parseInt(number);
            if(i > 0 && i <= 63_250)
                return true;
        } catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Die GetColorFromString-Methode wandelt die Zeichenketten, welche die Farben in colors
     * (ArrayList<String>) speichert, in Farben vom Datentyp java.awt.Color um. Die koennen dann zur
     * Darstellung genutzt werden.
     * @param color Farbe als String
     * @return Farbe als Color
     */
    private Color getColorFromString(String color)
    {
        switch(color)
        {
            case "blau":
                return Color.BLUE;
            case "cyan":
                return Color.CYAN;
            case "dunkelgrau":
                return Color.DARK_GRAY;
            case "grün":
                return Color.GREEN;
            case "hellgrau":
                return Color.LIGHT_GRAY;
            case "magenta":
                return Color.MAGENTA;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "rot":
                return Color.RED;
            case "weiß":
                return Color.WHITE;
            case "gelb":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }

    /**
     * @param iterations Anzahl der Iterationen
     */
    public void setIterations(int iterations)
    {
        this.iterations = iterations;
    }

    /**
     * @param file File der Ausgabedatei
     */
    public void setOutputFile(File file)
    {
        this.outputFile = file;
    }

    /**
     * @return BufferedImage des Bildes
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * @return Getter der Farben fuer das Diagramm
     */
    public List<String> getColors()
    {
        return colors;
    }

    /**
     * @return Getter fuer den Root fuer das Zugreifen auf das FXML
     */
    public Parent getRoot()
    {
        return this.root;
    }

    /**
     * @param root Root fuer das FXML
     */
    public void setRoot(Parent root)
    {
        this.root = root;
    }

    /**
     * @param custom Setter fuer die Tabelle des benutzerdefinierten Farns
     */
    public void setCustom(Farndata custom)
    {
        this.custom = custom;
    }

    /**
     * @return Getter fuer die Tabelle des benutzerdefinierten Farns
     */
    public Farndata getCustom()
    {
        return custom;
    }
}