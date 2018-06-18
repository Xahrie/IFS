package IFS;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.Scanner;

/**
 * InOut ist eine Klasse, deren Zustaendigkeit es ist, Benutzereigaben zu steuern. Diese Klasse ist
 * zwar nicht in der Lage Objekte zu erstellen, aber besitzt eine Instanzmethode.
 * @author Steve Woywod
 * @author Martin Zeyner
 * @since v1.0
 */
@Resource
public class InOut
{
    private String outputPath;

    /**
     * Die InOut-Methode ist eine Instanzvariable, welche den Nutzen hat, dass man problemlos auf
     * die Methoden dieser Klasse zurueckgreifen kann, ohne ein Objekt der Klasse erschaffen zu
     * muessen. Damit geht man der Verwendung von static-Methoden aus dem Weg.
     * @return Instanz der Klasse InOut
     */
    public static InOut instance()
    {
        return new InOut();
    }

    /**
     * Die Eingabe-Methode dient einer Texteingabe/Abfrage aus der Konsole des Programms. Damit
     * koennen unter anderem Benutzereingaben getaetigt werden.
     * @param task Nachricht an den Nutzer, die vor der Abfrage ausgegeben werden soll
     * @return Texteingabe des Nutzers
     */
    private String eingabe(String task)
    {
        System.out.println(task);
        return new Scanner(System.in).nextLine();
    }

    /**
     * Die Eingaben-Methode dient dazu, die Benutzereingaben, die zu Beginn des Programms getaetigt
     * werden abzufragen, zu speichern und zu verarbeiten, sowie ggf. bei unzulaessigen Eingaben
     * darauf hinzuweisen. Hierbei wird zunaechst der Parameter (ausgabe) ausgegeben. Im Anschluss
     * beginnt die Eingabe des Dateinamens und daraufhin der Anzahl der Iterationen. Existiert der
     * Pfad bereits, muss die Eingabe wiederholt werden. Wenn nicht, dann geht es zur anderen
     * Abfrage. Dann muss die Anzahl der Durchlaeufe angegeben werden. Befindet diese sich
     * ausserhalb des moeglichen Bereiches (1 - 63.250), so muss auch diese Eingabe wiederholt
     * werden. Ist diese Eingabe gueltig, wird diese Eingabe als Wert in die Steuerung .iterations
     * gesetzt. Im Anschluss wird noch aus der Eingabe fuer den Speicherort ein gueltiger Pfad
     * kreiert, der auf .png endet. Diese Datei wird dann erstellt und in Steuerung.outputFile
     * gesetzt.
     * @param ausgabe Textausgabe in die Konsole direkt zu Beginn der Methode
     * @throws NumberFormatException Anzahl der Iterationen muss ein Integer sein (parseInt)
     */
    @PostConstruct
    public void eingaben(String ausgabe)
    {
        System.out.println(ausgabe);
        outputPath = eingabe("Gib den Dateinamen fuer die Ausgabe an.");
        int iterations = 0;
        if(doesFileAlreadyExists())
            eingaben("Dieser Pfad existiert bereits.");
        if(Main.steuerung.getOutputFile().getName().equals("default.txt"))
            while(iterations == 0)
            {
                try
                {
                    int i = Integer.parseInt(eingabe("Gib die Zahl der Durchlaeufe an."));
                    if(i > 0 && i <= 63_250)
                    {
                        iterations = i;
                        Main.steuerung.setIterations(iterations);
                        break;
                    }
                } catch(NumberFormatException e)
                {
                    System.out.println(
                            "Du musst eine natuerliche Zahl angeben, die zwischen 1 und " +
                            "63.250 liegt.");
                }
            }
        //TODO Das funktioniert nur auf meinem PC so.
        Main.steuerung.setOutputFile(new File(
                "C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse " +
                "Workspace\\IFS\\src\\tests\\" + outputPath));
    }

    /**
     * In der FileStringToPath-Methode wird die Eingabe der zu erstellenden Datei so umgewandelt,
     * dass es sich um einen PNG-Dateipfad handelt. Dabei wird der String genommen und alles bis zum
     * '.' gekuerzt. Dann wird ein '.png' angehangen. Ist kein . vorhanden muss auch nichts gekuerzt
     * werden.
     * @param fileString Eingabe des Nutzers, wie die Datei heissen soll
     * @return korrekter Dateipfad
     */
    private String FileStringToPath(String fileString)
    {
        if(fileString.contains("."))
            outputPath = outputPath.substring(0, outputPath.indexOf('.'));
        outputPath = outputPath + ".png";
        return outputPath;
    }

    /**
     * In der DoesFileAlreadyExists-Methode wird abgefragt, ob der hier angegebene Dateipfad schon
     * existiert.
     * @return Ja, wenn diese Datei schon vorhanden ist
     */
    private boolean doesFileAlreadyExists()
    {
        return new File(FileStringToPath(this.outputPath)).exists();
    }
}