/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemeproducerconsumer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author cesar
 */
public class MainWindowController implements Initializable {
    @FXML
    private StackPane messagePane;
    @FXML
    private JFXChipView onqueueChipView;
    
    @FXML
    private void runErrorDialog(ActionEvent event) {
        JFXDialogLayout errorDialogLayout = new JFXDialogLayout();
        errorDialogLayout.setHeading(new Label("Error"));
        errorDialogLayout.setBody(new Text("Something went wrong"));
        
        JFXDialog errorDialog = new JFXDialog(messagePane,errorDialogLayout, JFXDialog.DialogTransition.CENTER);
        
        JFXButton okButton = new JFXButton("Ok");
        okButton.setButtonType(JFXButton.ButtonType.FLAT);
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event1) {
                errorDialog.close();
            }
        });
        errorDialogLayout.setActions(okButton);
        
        errorDialog.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
