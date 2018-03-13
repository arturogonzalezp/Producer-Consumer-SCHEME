/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producerconsumerscheme.visuals;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author cesar
 */
public class MainWindow extends JFrame{
    public MainWindow(){
        super("Producer Cosumers Program");
        this.setup();
    }
    private void setup(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}
