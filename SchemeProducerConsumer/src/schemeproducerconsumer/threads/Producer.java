package schemeproducerconsumer.threads;

import schemeproducerconsumer.utils.Buffer;
import schemeproducerconsumer.utils.RandomSchemeGenerator;
import schemeproducerconsumer.utils.SchemeArithmeticFunction;

/**
 * @author César Arturo González Pérez
 * @github arturogonzalezp
 */
public class Producer extends Thread {

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

    public void stopThread() {
        System.out.println("Stopping " + name + "...");
        stop = true;
    }

    public void pauseThread() {
        System.out.println("Pausing " + name + "...");
        pause = true;
    }

    public void unPauseThread() {
        System.out.println("Unpausing " + name + "...");
        pause = false;
    }

    @Override
    public void run() {
        System.out.println("Running " + name + "...");
        while (!stop) {
            try {
                if (!pause) {
                    SchemeArithmeticFunction function = this.generator.getRandomFunction();
                    this.buffer.produce(function, name);
                }
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
