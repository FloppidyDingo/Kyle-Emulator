/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kyle.emulator;

import GUI.*;
import Hardware.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sleepymouse.microprocessor.Z80.Z80Core;

/**
 *
 * @author James
 */
public class KyleEmulator {
    
    private Controls controls;
    private Memory memory;
    private CPULoop loop;
    private Z80Core cpu;
    private static KyleEmulator emulator;
    private DSP dsp;
    private Thread CPUThread;
    private MainFrame gui;
    private GUI.Error error;
    private CPUStats stats;
    private String biosUrl;
    private boolean Started;
    
    public void initialize(String[] args){
        memory = new Memory();
        controls = new Controls();
        cpu = new Z80Core(memory, controls);
        loop = new CPULoop(cpu);
        dsp = new DSP();
        error = new GUI.Error();
        try {
            File f = new File("config.ini");
            if(!f.exists()){
                f.createNewFile();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                    bw.write("null");
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_UP));//UP key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_DOWN));//Down key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_LEFT));//Left key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_RIGHT));//Right key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_X));//A key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_C));//B key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_Z));//Start key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_V));//Select key
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_A));//Left Trigger
                    bw.newLine();
                    bw.write(Integer.toString(KeyEvent.VK_S));//Right Trigger
                    bw.newLine();
                }
            }
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String s = br.readLine();
                if(!"null".equals(s)){
                    File bios = new File(s);
                    biosUrl = s;
                    if(bios.exists()){
                        memory.loadBIOS(bios);
                    }else{
                        error.setMessage("BIOS file at " + s + " not found!");
                        error.setVisible(true);
                    }
                    
                }
                controls.setUP(Integer.parseInt(br.readLine()));
                controls.setDOWN(Integer.parseInt(br.readLine()));
                controls.setLEFT(Integer.parseInt(br.readLine()));
                controls.setRIGHT(Integer.parseInt(br.readLine()));
                controls.setA(Integer.parseInt(br.readLine()));
                controls.setB(Integer.parseInt(br.readLine()));
                controls.setSTART(Integer.parseInt(br.readLine()));
                controls.setSELECT(Integer.parseInt(br.readLine()));
                controls.setLTRIG(Integer.parseInt(br.readLine()));
                controls.setRTRIG(Integer.parseInt(br.readLine()));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KyleEmulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KyleEmulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            String url = args[0];
            memory.loadROM(new File(url));
        }catch (Exception e){}
        stats = new CPUStats();
        gui = new MainFrame();
        gui.begin();
    }
    
    public void startEmulation(){
        if (!Started) {
            CPUThread = new Thread(loop);
            CPUThread.start();
            Started = true;
        }else{
            loop.setPlaying(true);
        }
        dsp.init();
        memory.writeByte(0xB491, 0xFF);
    }
    
    public Controls getControls() {
        return controls;
    }

    public Memory getMemory() {
        return memory;
    }

    public CPULoop getLoop() {
        return loop;
    }

    public Z80Core getCpu() {
        return cpu;
    }

    public DSP getDsp() {
        return dsp;
    }

    public MainFrame getGui() {
        return gui;
    }

    public GUI.Error getError() {
        return error;
    }

    public CPUStats getStats() {
        return stats;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {}
        emulator = new KyleEmulator();
        emulator.initialize(args);
    }
    
    public static KyleEmulator getEmulator(){
        return emulator;
    }

    public String getBiosUrl() {
        return biosUrl;
    }
    
}
