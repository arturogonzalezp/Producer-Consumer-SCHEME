package schemeproducerconsumer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import schemeproducerconsumer.exceptions.InvalidSchemeOperation;
import schemeproducerconsumer.utils.SchemeArithmeticFunction;
import schemeproducerconsumer.utils.SchemeArithmeticFunctionWrapper;
import schemeproducerconsumer.utils.SchemeDiv;
import schemeproducerconsumer.utils.SchemeMultiply;
import schemeproducerconsumer.utils.SchemeSub;
import schemeproducerconsumer.utils.SchemeSum;
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
    
    private ObservableList<SchemeArithmeticFunctionWrapper> producerTableList, consumerTableList;
    //private TreeItem<SchemeArithmeticFunctionWrapper> producerRoot;
    
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
    private void initializeSliders(){
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
    private void initializeProducerTable(){
        JFXTreeTableColumn<SchemeArithmeticFunctionWrapper,String> producerColumn = new JFXTreeTableColumn<>("Functions");
        producerColumn.setPrefWidth(198);
        producerColumn.setResizable(false);
        producerColumn.setSortable(false);
        producerColumn.setEditable(false);
        producerColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String> param) {
                return param.getValue().getValue().display;
            }
        });
        producerTableList = FXCollections.observableArrayList();
        final TreeItem<SchemeArithmeticFunctionWrapper> producerRoot = new RecursiveTreeItem<>(producerTableList,(param) -> {
            return param.getChildren();
        });
        onqueueTreeView.getColumns().setAll(producerColumn);
        onqueueTreeView.setRoot(producerRoot);
        onqueueTreeView.setShowRoot(false);
    }
    public void initializeConsumerTable(){
        JFXTreeTableColumn<SchemeArithmeticFunctionWrapper,String> consumerColumn = new JFXTreeTableColumn<>("Results");
        consumerColumn.setPrefWidth(198);
        consumerColumn.setResizable(false);
        consumerColumn.setSortable(false);
        consumerColumn.setEditable(false);
        consumerColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String> param) {
                return param.getValue().getValue().display;
            }
        });
        consumerTableList = FXCollections.observableArrayList();
        final TreeItem<SchemeArithmeticFunctionWrapper> consumerRoot = new RecursiveTreeItem<>(consumerTableList,(param) -> {
            return param.getChildren();
        });
        doneTreeView.getColumns().setAll(consumerColumn);
        doneTreeView.setRoot(consumerRoot);
        doneTreeView.setShowRoot(false);
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
    public SchemeArithmeticFunctionWrapper insertToProducerTable(SchemeArithmeticFunction function){
        SchemeArithmeticFunctionWrapper returnObj = new SchemeArithmeticFunctionWrapper(function.getFunctionString(), function);
        producerTableList.add(returnObj);
        return returnObj;
    }
    public SchemeArithmeticFunctionWrapper insertToConsumerTable(SchemeArithmeticFunction function){
        try {
            Double result = function.getResult();
            SchemeArithmeticFunctionWrapper returnObj = new SchemeArithmeticFunctionWrapper(function.getFunctionString() + " = " + result, function);
            consumerTableList.add(returnObj);
            return returnObj;
        } catch (InvalidSchemeOperation ex) {
            runErrorDialog(ex.getMessage());
        }
        return null;
    }
    public boolean deleteProducerFromList(SchemeArithmeticFunctionWrapper producer){
        return producerTableList.remove(producer);
    }
    public boolean deleteConsumerFromList(SchemeArithmeticFunctionWrapper consumer){
        return consumerTableList.remove(consumer);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeSliders();
        initializeProducerTable();
        initializeConsumerTable();
    }    
}
