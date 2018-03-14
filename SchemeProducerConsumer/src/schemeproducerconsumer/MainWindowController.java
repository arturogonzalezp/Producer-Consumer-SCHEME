package schemeproducerconsumer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private JFXTreeTableView onqueueTreeView, doneTreeView;
    @FXML
    private Label onqueueCountLabel, doneCountLabel, bufferLabel, consumerLabel, producerLabel;
    @FXML
    private JFXSlider bufferNumSlider, consumerNumSlider, producerNumSlider;
    @FXML
    private JFXTextField consumerTimeInput, producerTimeInput;
    @FXML
    private JFXButton startButton, stopButton; 
    
    @FXML
    private void runProgram(ActionEvent event){
        setQueueCounter(getProducerNum());
        setDoneCounter(getConsumerNum());
        runErrorDialog("Buffer size: " + getBufferNum());
        changeInputStates(true);
    }
    @FXML
    private void stopProgram(ActionEvent event){
        changeInputStates(false);
    }
    private void changeInputStates(Boolean state){
        bufferNumSlider.setDisable(state);
        consumerNumSlider.setDisable(state);
        producerNumSlider.setDisable(state);
        consumerTimeInput.setDisable(state);
        producerTimeInput.setDisable(state);
        startButton.setDisable(state);
        stopButton.setDisable(!state);
    }
    public void runErrorDialog(String message) {
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
    public int getBufferNum(){
        return (int) bufferNumSlider.getValue();
    }
    public int getProducerNum(){
        return (int) producerNumSlider.getValue();
    }
    public int getConsumerNum(){
        return (int) consumerNumSlider.getValue();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bufferNumSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                bufferNumSlider.setValue(newValue.intValue());
                bufferLabel.setText("Buffers (" + ((int)bufferNumSlider.getValue()) + ")");
            }
        });
        consumerNumSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                consumerNumSlider.setValue(newValue.intValue());
                consumerLabel.setText("Consumers (" + ((int) consumerNumSlider.getValue()) + ")");
            }
        });
        producerNumSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                producerNumSlider.setValue(newValue.intValue());
                producerLabel.setText("Producers (" + ((int)producerNumSlider.getValue()) + ")");
            }
        });
    }    
    
}
