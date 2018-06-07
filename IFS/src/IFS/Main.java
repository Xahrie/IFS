package IFS;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    public static Steuerung steuerung;

    public static void main(String[] args)
    {
        steuerung = new Steuerung();
        steuerung.execute();
    }

    public void start(Stage s) throws Exception
    {
        steuerung.setStage(s);
        FX.instance().menue();
        s.show();
        steuerung.drawGraph();
    }

    public static void launching()
    {
        launch();
    }
}
