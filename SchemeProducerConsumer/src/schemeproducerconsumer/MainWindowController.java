package schemeproducerconsumer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import schemeproducerconsumer.exceptions.InvalidSchemeOperation;
import schemeproducerconsumer.threads.ProducerConsumerManager;
import schemeproducerconsumer.utils.Buffer;
import schemeproducerconsumer.utils.SchemeArithmeticFunction;
import schemeproducerconsumer.utils.SchemeArithmeticFunctionWrapper;
import schemeproducerconsumer.utils.ErrorDialog;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class MainWindowController implements Initializable {
    @FXML
    private StackPane messagePane;
    @FXML
    private JFXTreeTableView producerTreeView, consumerTreeView;
    @FXML
    private Label producerCountLabel, consumerCountLabel, bufferLabel, consumerLabel, producerLabel;
    @FXML
    private JFXSlider bufferNumSlider, consumerNumSlider, producerNumSlider;
    @FXML
    private JFXTextField consumerTimeInput, producerTimeInput;
    @FXML
    private JFXButton startButton, pauseButton, stopButton, developersButton; 
    
    private ObservableList<SchemeArithmeticFunctionWrapper> producerTableList, consumerTableList;
    private ProducerConsumerManager pcManager;
    private boolean paused;
    
    @FXML
    private void startProgram(ActionEvent event){
        changeInputStates(true);
        Buffer buffer = new Buffer(getBufferSliderNum(), this);
        pcManager.initialize(buffer, getProducerSliderNum(), getConsumerSliderNum(), getProducerTime(), getConsumerTime());
        pcManager.start();
        paused = false;
    }
    @FXML
    private void pauseProgram(ActionEvent event){
        if(!paused){
            pauseButton.setText("Continue");
            paused = true;
            pcManager.pause();
        }else{
            pauseButton.setText("Pause");
            paused = false;
            pcManager.unPause();
        }
    }
    @FXML
    private void stopProgram(ActionEvent event){
        changeInputStates(false);
        pcManager.stop();
        paused = false;
        producerTableList.clear();
        consumerTableList.clear();
        updateProducerLabel();
        updateConsumerLabel();
    }
    @FXML
    private void showDevelopers(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("visual/views/DevelopersWindow.fxml"));
            Stage stage = (Stage) developersButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (Exception e){
            runErrorDialog("Internal Error");
        }
    }
    private void changeInputStates(Boolean state){
        bufferNumSlider.setDisable(state);
        consumerNumSlider.setDisable(state);
        producerNumSlider.setDisable(state);
        consumerTimeInput.setDisable(state);
        producerTimeInput.setDisable(state);
        startButton.setDisable(state);
        stopButton.setDisable(!state);
        pauseButton.setDisable(!state);
        developersButton.setDisable(state);
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
        producerColumn.setPrefWidth(100);
        producerColumn.setSortable(false);
        producerColumn.setEditable(false);
        producerColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String> param) {
                return param.getValue().getValue().display;
            }
        });
        
        JFXTreeTableColumn<SchemeArithmeticFunctionWrapper,String> producerThreadColumn = new JFXTreeTableColumn<>("Producer");
        producerThreadColumn.setPrefWidth(100);
        //producerThreadColumn.setSortable(false);
        producerThreadColumn.setEditable(false);
        producerThreadColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String> param) {
                return param.getValue().getValue().producerThreadName;
            }
        });
        
        producerTableList = FXCollections.observableArrayList();
        final TreeItem<SchemeArithmeticFunctionWrapper> producerRoot = new RecursiveTreeItem<>(producerTableList,(param) -> {
            return param.getChildren();
        });
        producerTreeView.getColumns().setAll(producerColumn,producerThreadColumn);
        producerTreeView.setRoot(producerRoot);
        producerTreeView.setShowRoot(false);
        updateProducerLabel();
    }
    private void initializeConsumerTable(){
        JFXTreeTableColumn<SchemeArithmeticFunctionWrapper,String> consumerColumn = new JFXTreeTableColumn<>("Results");
        consumerColumn.setPrefWidth(150);
        consumerColumn.setSortable(false);
        consumerColumn.setEditable(false);
        consumerColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String> param) {
                return param.getValue().getValue().display;
            }
        });
        JFXTreeTableColumn<SchemeArithmeticFunctionWrapper,String> producerThreadColumn = new JFXTreeTableColumn<>("Producer");
        producerThreadColumn.setPrefWidth(100);
        //consumerThreadColumn.setSortable(false);
        producerThreadColumn.setEditable(false);
        producerThreadColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String> param) {
                return param.getValue().getValue().producerThreadName;
            }
        });
        
        JFXTreeTableColumn<SchemeArithmeticFunctionWrapper,String> consumerThreadColumn = new JFXTreeTableColumn<>("Consumer");
        consumerThreadColumn.setPrefWidth(100);
        //consumerThreadColumn.setSortable(false);
        consumerThreadColumn.setEditable(false);
        consumerThreadColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SchemeArithmeticFunctionWrapper, String> param) {
                return param.getValue().getValue().consumerThreadName;
            }
        });
        
        consumerTableList = FXCollections.observableArrayList();
        final TreeItem<SchemeArithmeticFunctionWrapper> consumerRoot = new RecursiveTreeItem<>(consumerTableList,(param) -> {
            return param.getChildren();
        });
        consumerTreeView.getColumns().setAll(consumerColumn, producerThreadColumn, consumerThreadColumn);
        consumerTreeView.setRoot(consumerRoot);
        consumerTreeView.setShowRoot(false);
        updateConsumerLabel();
    }
    private void initializeTimeInputs(){
        producerTimeInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if(newValue.equals("") || newValue.equals("0")){
                    producerTimeInput.setText("1000");
                }else if (!newValue.matches("\\d*")) {
                    producerTimeInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        consumerTimeInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if(newValue.equals("") || newValue.equals("0")){
                    consumerTimeInput.setText("1000");
                }else if (!newValue.matches("\\d*")) {
                    consumerTimeInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    private void updateProducerLabel(){
        setProducerCounter(producerTableList.size());
    }
    private void updateConsumerLabel(){
        setConsumerCounter(consumerTableList.size());
    }
    private void setProducerCounter(int num){
        String str = "" + num;
        producerCountLabel.setText(str);
    }
    private void setConsumerCounter(int num){
        String str = "" + num;
        consumerCountLabel.setText(str);
    }
    public void runErrorDialog(String message) {
        new ErrorDialog(message,messagePane).show();
    }
    public int getBufferSliderNum(){
        return (int) bufferNumSlider.getValue();
    }
    public int getProducerSliderNum(){
        return (int) producerNumSlider.getValue();
    }
    public int getConsumerSliderNum(){
        return (int) consumerNumSlider.getValue();
    }
    public int getProducerTime(){
        return Integer.parseInt(producerTimeInput.getText());
    }
    public int getConsumerTime(){
        return Integer.parseInt(consumerTimeInput.getText());
    }
    public SchemeArithmeticFunctionWrapper insertToProducerTable(SchemeArithmeticFunction function, String producerThreadName){
        SchemeArithmeticFunctionWrapper returnObj = new SchemeArithmeticFunctionWrapper(function.getFunctionString(), producerThreadName, "", function);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                producerTableList.add(returnObj);
                updateProducerLabel();
            }
        });
        return returnObj;
    }
    public SchemeArithmeticFunctionWrapper insertToConsumerTable(SchemeArithmeticFunction function, String producerThreadName, String consumerThreadName){
        try {
            Double result = function.getResult();
            SchemeArithmeticFunctionWrapper returnObj = new SchemeArithmeticFunctionWrapper(function.getFunctionString() + " = " + result, producerThreadName, consumerThreadName, function);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    consumerTableList.add(returnObj);
                    updateConsumerLabel();
                }
            });
            return returnObj;
        } catch (InvalidSchemeOperation ex) {
            runErrorDialog(ex.getMessage());
        }
        return null;
    }
    public void deleteProducerFromList(SchemeArithmeticFunctionWrapper producer){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boolean removed = producerTableList.remove(producer);
                if(removed)
                    updateProducerLabel();
            }
        });
    }
    public void deleteConsumerFromList(SchemeArithmeticFunctionWrapper consumer){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boolean removed = consumerTableList.remove(consumer);
                    if(removed)
                    updateConsumerLabel();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeSliders();
        initializeTimeInputs();
        initializeProducerTable();
        initializeConsumerTable();
        pcManager = new ProducerConsumerManager();
        paused = false;
    }    
}
