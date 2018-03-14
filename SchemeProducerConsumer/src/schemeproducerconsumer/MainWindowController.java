package schemeproducerconsumer;

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
    private JFXTreeTableView onqueueTreeView;
    @FXML
    private JFXTreeTableView doneTreeView;
    @FXML
    private Label onqueueCountLabel;
    @FXML
    private Label doneCountLabel;
    @FXML
    private JFXSlider bufferNumSlider;
    @FXML
    private JFXSlider consumerNumSlider;
    @FXML
    private JFXSlider producerNumSlider;
    @FXML
    private JFXTextField consumerTimeInput;
    @FXML
    private JFXTextField producerTimeInput;
    @FXML
    private Label bufferLabel, consumerLabel, producerLabel;
    
    @FXML
    private void runProgram(ActionEvent event){
        setQueueCounter(getProducerNum());
        setDoneCounter(getConsumerNum());
        runErrorDialog("Buffer size: " + getBufferNum());
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
