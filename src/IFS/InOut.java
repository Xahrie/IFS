package IFS;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.Scanner;

/**
 * InOut ist eine Klasse, deren Zustaendigkeit es ist, Benutzereigaben zu steuern. Diese Klasse ist
 * zwar nicht in der Lage Objekte zu erstellen, aber besitzt eine Instanzmethode.
 */
@Resource
public class InOut
{
    private String outputPath;

    /**
     * Die InOut-Funktion ist eine Instanzvariable, welche den Nutzen hat, dass man problemlos auf
     * die Funktionen dieser Klasse zurueckgreifen kann, ohne ein Objekt der Klasse erschaffen zu
     * muessen. Damit geht man der Verwendung von static-Methoden aus dem Weg.
     * @return              Instanz der Klasse InOut
     */
    public static InOut instance()
    {
        return new InOut();
    }

    /**
     * Die Eingabe-Funktion dient einer Texteingabe/Abfrage aus der Kosole des Programms. Damit
     * koennen unter anderem Benutzereingaben getaetigt werden.
     * @param task          Nachricht an den Nutzer, die vor der Abfrage ausgegeben werden soll
     * @return              Texteingabe des Nutzers
     */
    private String eingabe(String task)
    {
        System.out.println(task);
        return new Scanner(System.in).nextLine();
    }

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
                    System.out.println("Du musst eine natuerliche Zahl angeben, die zwischen 1 und " +
                                       "63.250 liegt.");
                }
            }
        if(outputPath.contains("."))
            outputPath = outputPath.substring(0, outputPath.indexOf('.') + 1);
            outputPath = outputPath + ".png";
        Main.steuerung.setOutputFile(new File(
                "C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse Workspace\\IFS\\src\\dateien\\" +
                outputPath));
    }

    private boolean doesFileAlreadyExists()
    {
        String pathName = this.outputPath;
        if(this.outputPath.contains("."))
            pathName = pathName.substring(0, pathName.indexOf('.') + 1);
        return new File(pathName).exists();
    }
}
