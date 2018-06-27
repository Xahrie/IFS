package IFS;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {

    @FXML
    private void inputAction(ActionEvent event) {
        TextField field;
        ComboBox comboBox;
        if(((Button) event.getSource()).getText().equals("Einlesen"))
        {
            Label label = (Label) Main.steuerung.getMenue().getRoot().lookup("#ausgewifs");
            field = (TextField) Main.steuerung.getMenue().getRoot().lookup("#ifsdatei");
            if(label != null)
                if(new File(Main.steuerung.fileToTXT(field.getText())).exists())
                {
                    try
                    {
                        Main.steuerung.generateData(new File(Main.steuerung.fileToTXT(field.getText()
                        )));
                    } catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                    label.setText("ausgewählt: " + Main.steuerung.fileToTXT(field.getText()));
                }

        } else if(((Button) event.getSource()).getText().equals("Speichern"))
        {
            Label label = (Label) Main.steuerung.getMenue().getRoot().lookup("#ausgewit");
            field = (TextField) Main.steuerung.getMenue().getRoot().lookup("#iterations");
            if(label != null)
            {
                if(InOut.instance().isANumber(field.getText()))
                {
                    Main.steuerung.setIterations(Integer.parseInt(field.getText()));
                    label.setText("ausgewählt: " + field.getText());
                }
                else
                    label.setText("natürliche Zahl zwischen 1 und 63.250 angeben");
            }

        } else if(((Button) event.getSource()).getText().equals("Hinzufügen"))
        {
            Button button =  (Button) Main.steuerung.getMenue().getRoot().lookup("#hinzu");
            Label label = (Label) Main.steuerung.getMenue().getRoot().lookup("#ausgewfar");
            comboBox = (ComboBox) Main.steuerung.getMenue().getRoot().lookup("#farbe");
            Main.steuerung.getColors().add(comboBox.getSelectionModel().getSelectedItem()
                                                   .toString());
            label.setText(label.getText() + " " +
                          comboBox.getSelectionModel().getSelectedItem().toString());
            if(Main.steuerung.getColors().size() == 4)
                button.setText("Löschen");

        } else if(((Button) event.getSource()).getText().equals("Löschen"))
        {
            Button button =  (Button) Main.steuerung.getMenue().getRoot().lookup("#hinzu");
            Label label = (Label) Main.steuerung.getMenue().getRoot().lookup("#ausgewfar");
            Main.steuerung.getColors().clear();
            if(label != null)
                label.setText("ausgewählt: ");
            button.setText("Hinzufügen");

        } else if(((Button) event.getSource()).getText().equals("Farn generieren"))
        {
            if(((Label) Main.steuerung.getMenue().getRoot().lookup("#ausgewit")).toString().equals
                    ("ausgewählt: "))
                Main.steuerung.iterate(false);
            else
                Main.steuerung.iterate(true);
            Main.steuerung.graphicalSolution();
            ((ImageView) Main.steuerung.getMenue().getRoot().lookup("#image")).setImage
                    (SwingFXUtils.toFXImage(Main.steuerung.getImage(), null));
        } else if(((Button) event.getSource()).getText().equals("Programm beenden"))
        {
            System.exit(0);
        } else if(((Button) event.getSource()).getText().equals("Exportieren"))
        {
            field = (TextField) Main.steuerung.getMenue().getRoot().lookup("#exportdatei");
            if(field != null)
                if(!new File("C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse " +
                        "Workspace\\IFS\\src\\tests\\" + InOut.instance().fileStringToPath(field.getText())).exists())
                {
                    Main.steuerung.setOutputFile(new File("C:\\Users\\Martin Zeyner\\Dropbox\\Development\\Eclipse " +
                            "Workspace\\IFS\\src\\tests\\" + InOut.instance().fileStringToPath(field.getText())));
                    Main.steuerung.createFile();
                }
        }
    }
}