package schemeproducerconsumer;

import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import schemeproducerconsumer.visual.controllers.ErrorDialog;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class MainWindowController implements Initializable {
    @FXML
    private StackPane messagePane;
    @FXML
    private JFXTreeTableView onqueueTreeView;
    @FXML
    private JFXTreeTableView doneTreeView;
    @FXML
    private Label onqueueCountLabel;
    @FXML
    private Label doneCountLabel;
    
    @FXML
    private void runProgram(ActionEvent event){
        setQueueCounter(getQueueCounter()+1);
        setDoneCounter(getDoneCounterLabel()*2);
    }
    private void runErrorDialog(String message) {
        new ErrorDialog(message,messagePane).show();
    }
    public void setQueueCounter(int num){
        String str = "" + num;
        onqueueCountLabel.setText(str);
    }
    public void setDoneCounter(int num){
        String str = "" + num;
        doneCountLabel.setText(str);
    }
    public int getQueueCounter(){
        return Integer.parseInt(onqueueCountLabel.getText());
    }
    public int getDoneCounterLabel(){
        return Integer.parseInt(doneCountLabel.getText());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDoneCounter(1);
        setQueueCounter(1);
    }    
    
}
