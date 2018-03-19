package schemeproducerconsumer.utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class SchemeArithmeticFunctionWrapper extends RecursiveTreeObject<SchemeArithmeticFunctionWrapper>{
    public StringProperty display, producerThreadName, consumerThreadName;
    public SchemeArithmeticFunction function;
    
    public SchemeArithmeticFunctionWrapper(String display, String producerThreadName, String consumerThreadName, SchemeArithmeticFunction function){
        this.display = new SimpleStringProperty(display);
        this.function = function;
        this.producerThreadName = new SimpleStringProperty(producerThreadName);
        this.consumerThreadName = new SimpleStringProperty(consumerThreadName);
    }
}
