package IFS;

import java.util.Scanner;

public class InOut
{
    public static InOut instance()
    {
        return new InOut();
    }

    public String eingabe(String task)
    {
        System.out.println(task);
        return new Scanner(System.in).nextLine();
    }

    public void eingaben()
    {
        Main.steuerung.setOutputPath(eingabe("Gib den Dateinamen fuer die Ausgabe an."));
        Main.steuerung.setIterations(Integer.parseInt(eingabe("Gib die Zahl der Durchlaeufe an.")));
    }
}
