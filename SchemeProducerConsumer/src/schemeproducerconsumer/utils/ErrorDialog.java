package schemeproducerconsumer.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class ErrorDialog {
    private JFXDialog dialog;
    
    public ErrorDialog(String errorMessage, StackPane stackPane){
        JFXDialogLayout errorDialogLayout = new JFXDialogLayout();
        errorDialogLayout.setHeading(new Label("Error"));
        errorDialogLayout.setBody(new Text(errorMessage));
        
        dialog = new JFXDialog(stackPane,errorDialogLayout, JFXDialog.DialogTransition.CENTER);
        
        JFXButton okButton = new JFXButton("OK");
        okButton.setButtonType(JFXButton.ButtonType.FLAT);
        okButton.setCursor(Cursor.HAND);
        okButton.setDefaultButton(true);
        okButton.setPadding(new Insets(10));
        okButton.setStyle("-fx-text-fill: #00acc1;");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event1) {
                close();
            }
        });
        errorDialogLayout.setActions(okButton);
    }
    public void show(){
        dialog.show();
    }
    public void close(){
        dialog.close();
    }
}
