package schemeproducerconsumer;

import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private void runErrorDialog(ActionEvent event) {
        ErrorDialog errorDialog = new ErrorDialog("Something went wrong",messagePane);
        errorDialog.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
