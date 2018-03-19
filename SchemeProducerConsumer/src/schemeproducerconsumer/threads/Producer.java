package schemeproducerconsumer.threads;

import schemeproducerconsumer.utils.Buffer;
import schemeproducerconsumer.utils.RandomSchemeGenerator;
import schemeproducerconsumer.utils.SchemeArithmeticFunction;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */

public class Producer  extends Thread {
    private Buffer buffer;
    private String name;
    private Integer time;
    private boolean stop;
    private boolean pause;
    private RandomSchemeGenerator generator;
    
    public Producer(Buffer buffer, String name, Integer time) {
        this.buffer = buffer;
        this.name = name;
        this.time = time;
        this.stop = false;
        this.pause = false;
        this.generator = new RandomSchemeGenerator();
    }
    
    @Override
    public void run() {
       System.out.println("Running " + name + "...");
       while (!stop) {
           SchemeArithmeticFunction function = this.generator.getRandomFunction();
           try {
               this.buffer.produce(function, name);
               Thread.sleep(time);
           } catch(InterruptedException e) {
               e.printStackTrace();
           }
       }
    }  
}