package schemeproducerconsumer.visual.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import schemeproducerconsumer.MainWindowController;
import schemeproducerconsumer.utils.ErrorDialog;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class DevelopersWindowController implements Initializable {
    @FXML
    private StackPane messagePane;
    @FXML
    private JFXButton mainPageButton;
    
    @FXML
    private void showMainPage(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("MainWindowView.fxml"));
            Stage stage = (Stage) mainPageButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (Exception e){
            e.printStackTrace();
            runErrorDialog("Internal Error");
        }
    }
    
    public void runErrorDialog(String message) {
        new ErrorDialog(message,messagePane).show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
