/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hardware;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;
import kyle.emulator.KyleEmulator;

/**
 *
 * @author James
 */
public class Screen extends JPanel{
    private volatile int[] memory;
    private final BufferedImage img;
    private final Timer timer;
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        memory = KyleEmulator.getEmulator().getDsp().process();
        int index = 0;
        for(int x = 319; x > -1; x--){
            for(int y = 0; y < 240; y ++){
                int color = (memory[index] + (memory[index + 1] * 256));
                img.setRGB(x, y, convert16_32(color));
                index += 2;
            }
        }
        g.drawImage(img, 0, 0, this.getSize().width, this.getSize().height, this);
    }

    public Screen() {
        img = new BufferedImage(320, 240, BufferedImage.TYPE_USHORT_565_RGB);
        timer = new Timer(1000/30, (ActionEvent e) -> {
            KyleEmulator.getEmulator().getLoop().busRequest();
            action();
            KyleEmulator.getEmulator().getLoop().busRelease();
            KyleEmulator.getEmulator().getCpu().setNMI();
        });
    }
    
    private void action(){
        this.repaint();
    }
    
    private int convert16_32(int rgb) { // conerts 16 bit color to 32 bit color
        int r = ((rgb & 0b1111100000000000) >> 11);
        int g = ((rgb & 0b0000011111100000) >> 5);
        int b = ((rgb & 0b0000000000011111));
        r = (int)(1023 * ((float)r / 31));
        g = (int)(4095 * ((float)g / 63));
        b = (int)(1023 * ((float)b / 31));
        return ((r << 22) | (g << 10) | b);
    }
    
    public void init(){
        timer.start();
    }
    
    public void stop(){
        timer.stop();
    }
    
    public void reset(){
        stop();
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
        init();
    }
}
