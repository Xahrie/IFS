package IFS;

import java.io.File;
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

    public void eingaben(String ausgabe)
    {
        System.out.println(ausgabe);
        String outputPath = eingabe("Gib den Dateinamen fuer die Ausgabe an.");
        int iterations = 0;
        if(doesFileAlreadyExists(outputPath))
            eingaben("Dieser Pfad existiert bereits.");
        if(Main.steuerung.getOutputFile().getName().equals("default.txt"))
            while(iterations == 0)
            {
                try
                {
                    int i = Integer.parseInt(eingabe("Gib die Zahl der Durchlaeufe an."));
                    if(i > 0)
                    {
                        iterations = i;
                        Main.steuerung.setIterations(iterations);
                        break;
                    }
                } catch(NumberFormatException e)
                {
                    System.out.println("Du musst eine natuerliche Zahl angeben.");
                }
            }
        if(outputPath.contains("."))
            outputPath = outputPath.substring(0, outputPath.indexOf('.') + 1);
        outputPath = new StringBuilder(outputPath).append(".png").toString();
        Main.steuerung.setOutputFile(new File(
                "C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse Workspace\\IFS\\src\\dateien\\" +
                outputPath));
    }

    private boolean doesFileAlreadyExists(String path)
    {
        String pathName = path;
        if(path.contains("."))
            pathName = pathName.substring(0, pathName.indexOf('.') + 1);
        if(new File(pathName).exists())
            return true;
        else
            return false;
    }
}
