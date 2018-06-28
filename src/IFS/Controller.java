package IFS;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Die Controller-Klasse ist die Steuerklasse fuer das FXML. Diese steuert Eingaben in Feldern und
 * die Events fuer die Buttons.
 */
public class Controller
{
    @FXML
    // fx:id="farbe"
    private ComboBox<String> farbe;

    /**
     * Die Instance-Methode fungiert als Konstruktor der Klasse. Darueber koennen weitere Methoden
     * der Klasse aufgerufen werden.
     * @return Instanz der Klasse
     */
    public static Controller instance()
    {
        return new Controller();
    }

    /**
     * Die InputAction-Methode steuert die Events fuer das Betaetigen der Buttons.
     * @param event Event fuer das Klicken auf einen Button
     * @author Steve Woywod
     * @author Martin Zeyner
     * @since v2.0
     */
    @FXML
    private void inputAction(ActionEvent event)
    {
        TextField field;
        ComboBox comboBox;
        switch(((Button) event.getSource()).getText())
        {
            case "Einlesen":
            {
                Label label = (Label) Main.steuerung.getRoot().lookup("#ausgewifs");
                field = (TextField) Main.steuerung.getRoot().lookup("#ifsdatei");
                if(label.getText().equals("ausgewählt: "))
                {
                    if(new File(Main.steuerung.fileToTXT(
                            "C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse " +
                            "Workspace\\IFS\\src\\IFS\\" + field.getText())).exists())
                    {
                        try
                        {
                            Main.steuerung.generateData(new File(Main.steuerung.fileToTXT(
                                    "C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse " +
                                    "Workspace\\IFS\\src\\IFS\\" + field.getText())));
                        } catch(IOException e)
                        {
                            e.printStackTrace();
                        } catch(NumberFormatException e)
                        {
                            e.printStackTrace();
                            System.out.println("Darstellung nicht moeglich");
                            Main.steuerung.setCustom(null);
                        }
                        label.setText("ausgewählt: " + Main.steuerung.fileToTXT(field.getText()));
                    }
                }


                break;
            }
            case "Speichern":
            {
                Label label = (Label) Main.steuerung.getRoot().lookup("#ausgewit");
                field = (TextField) Main.steuerung.getRoot().lookup("#iterations");
                if(label != null)
                {
                    Main.steuerung.setIterations(0);
                    if(Main.steuerung.isANumber(field.getText()))
                    {
                        Main.steuerung.setIterations(Integer.parseInt(field.getText()));
                        label.setText("ausgewählt: " + field.getText());
                    } else
                        label.setText("natürliche Zahl zwischen 1 und 63.250 angeben");
                }

                break;
            }
            case "Hinzufügen":
            {
                Button button = (Button) Main.steuerung.getRoot().lookup("#hinzu");
                Label label = (Label) Main.steuerung.getRoot().lookup("#ausgewfar");
                comboBox = (ComboBox) Main.steuerung.getRoot().lookup("#farbe");
                Main.steuerung.getColors()
                              .add(comboBox.getSelectionModel().getSelectedItem().toString());
                label.setText(label.getText() + " " +
                              comboBox.getSelectionModel().getSelectedItem().toString());
                if(Main.steuerung.getColors().size() == 4)
                    button.setText("Löschen");

                break;
            }
            case "Löschen":
            {
                Button button = (Button) Main.steuerung.getRoot().lookup("#hinzu");
                Label label = (Label) Main.steuerung.getRoot().lookup("#ausgewfar");
                Main.steuerung.getColors().clear();
                if(label != null)
                    label.setText("ausgewählt: ");
                button.setText("Hinzufügen");

                break;
            }
            case "Farn generieren":
                Main.steuerung.execute(true);
                if(((Label) Main.steuerung.getRoot().lookup("#ausgewifs")).getText()
                                                                          .equals("ausgewählt: "))
                    Main.steuerung.iterate(false);
                else
                {
                    if(Main.steuerung.getCustom().equals(null))
                        ((Label) Main.steuerung.getRoot().lookup("#ausgewifs"))
                                .setText("Fehler beim " + "Laden");
                    Main.steuerung.iterate(true);
                }
                Main.steuerung.graphicalSolution();
                ((ImageView) Main.steuerung.getRoot().lookup("#image"))
                        .setImage(SwingFXUtils.toFXImage(Main.steuerung.getImage(), null));
                break;
            case "Programm beenden":
                System.exit(0);
            case "Exportieren":
                field = (TextField) Main.steuerung.getRoot().lookup("#exportdatei");
                if(field != null)
                    if(!new File("C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse " +
                                 "Workspace\\IFS\\src\\tests\\" +
                                 Main.steuerung.fileToPNG(field.getText())).exists())
                    {
                        Main.steuerung.setOutputFile(new File(
                                "C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse " +
                                "Workspace\\IFS\\src\\tests\\" +
                                Main.steuerung.fileToPNG(field.getText())));
                        Main.steuerung.createFile();
                    }
                break;
        }
    }

    /**
     * Die Initialize-Methode initialisiert nach der Ausfuehrung die Checkbox fuer die Auswahl der
     * Farben.
     */
    @FXML
    private void initialize()
    {
        assert farbe != null :
                "fx:id=\"farbe\" was not injected: check your FXML file " + "'fxml.fxml'.";
        farbe.getItems()
             .addAll("blau", "cyan", "dunkelgrau", "gelb", "grün", "hellgrau", "magenta", "orange",
                     "pink", "rot", "weiß");
    }
}