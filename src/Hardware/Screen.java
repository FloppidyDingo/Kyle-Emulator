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
        for(int x = 0; x < 160; x ++){
            for(int y = 0; y < 128; y ++){
                int color = (memory[(y * 160) + x]);
                img.setRGB(x, y, convert8_32(color));
            }
        }
        g.drawImage(img, 0, 0, this.getSize().width, this.getSize().height, this);
    }

    public Screen() {
        img = new BufferedImage(160, 128, BufferedImage.TYPE_USHORT_565_RGB);
        timer = new Timer(1000/30, (ActionEvent e) -> {
            KyleEmulator.getEmulator().getLoop().busRequest();
            action();
            KyleEmulator.getEmulator().getLoop().busRelease();
            if (!KyleEmulator.getEmulator().getGui().getDebugMode()) {
                KyleEmulator.getEmulator().getCpu().setNMI();
            }
        });
    }
    
    private void action(){
        this.repaint();
    }
    
    private int convert8_32(int rgb) { // conerts 8 bit color to 32 bit color
        int r = ((rgb & 0b11100000) >> 5);
        int g = ((rgb & 0b00011100) >> 2);
        int b = ((rgb & 0b00000011));
        r = (int)(1023 * ((float)r / 7));
        g = (int)(4095 * ((float)g / 7));
        b = (int)(1023 * ((float)b / 3));
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
