package schemeproducerconsumer.threads;

import java.util.ArrayList;
import java.util.List;
import schemeproducerconsumer.utils.Buffer;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */
public class ProducerConsumerManager {

    private List<Producer> producers;
    private List<Consumer> consumers;
    private Buffer buffer;
    private int producerNum, consumerNum, producerTime, consumerTime;

    public ProducerConsumerManager() {
        producers = new ArrayList<>();
        consumers = new ArrayList<>();
    }

    public void initialize(Buffer buffer, int producerNum, int consumerNum, int producerTime, int consumerTime) {
        producers.clear();
        consumers.clear();
        this.buffer = buffer;
        this.producerNum = producerNum;
        this.consumerNum = consumerNum;
        this.producerTime = producerTime;
        this.consumerTime = consumerTime;
        for (int i = 1; i <= producerNum; i++) {
            producers.add(new Producer(buffer, "Producer " + i, producerTime));
        }
        for (int i = 1; i <= consumerNum; i++) {
            consumers.add(new Consumer(buffer, "Consumer " + i, consumerTime));
        }
    }

    public void start() {
        for (Producer producer : producers) {
            producer.start();
        }
        for (Consumer consumer : consumers) {
            consumer.start();
        }
    }

    public void stop() {
        for (Producer producer : producers) {
            producer.stopThread();
        }
        for (Consumer consumer : consumers) {
            consumer.stopThread();
        }
    }
    
    public void pause() {
        for (Producer producer : producers) {
            producer.pauseThread();
        }
        for (Consumer consumer : consumers) {
            consumer.pauseThread();
        }
    }
    
    public void unPause(){
        for (Producer producer : producers) {
            producer.unPauseThread();
        }
        for (Consumer consumer : consumers) {
            consumer.unPauseThread();
        }
    }
}
