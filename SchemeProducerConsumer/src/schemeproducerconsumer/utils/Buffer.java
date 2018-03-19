package schemeproducerconsumer.utils;

import java.util.LinkedList;
import java.util.Queue;
import schemeproducerconsumer.MainWindowController;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class Buffer {
    private Queue<SchemeArithmeticFunctionWrapper> buffer;
    private Integer bufferSize;
    private MainWindowController controller;
    
    public Buffer(Integer bufferSize, MainWindowController controller){
        buffer = new LinkedList<>();
        this.bufferSize = bufferSize;
        this.controller = controller;
    }
    public synchronized SchemeArithmeticFunctionWrapper consume(String threadName) throws InterruptedException {
        while(buffer.isEmpty()) {
            wait();
        }
        SchemeArithmeticFunctionWrapper product = buffer.remove();
        controller.deleteProducerFromList(product);
        notifyAll();
        return controller.insertToConsumerTable(product.function, product.producerThreadName.getValue(), threadName);
    }
    
    public synchronized void produce(SchemeArithmeticFunction product, String threadName) throws InterruptedException {
        while(buffer.size() == bufferSize) {
            wait();
        }
        buffer.add(controller.insertToProducerTable(product, threadName));
        notifyAll();
    }
}
