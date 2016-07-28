/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kyle.emulator;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sleepymouse.microprocessor.Z80.Z80Core;
import net.sleepymouse.microprocessor.Z80.Z80Core.RegisterNames;

/**
 *
 * @author James
 */
public class CPULoop implements Runnable{

    private final Z80Core CPU;
    private boolean active;
    private boolean running;
    private boolean playing;
    private int cycle;
    private int delay;
    private int cycleDelay;
    
    public CPULoop(Z80Core c) {
        CPU = c;
        delay = 100;
        cycleDelay = 20000;
    }
    
    @Override
    public void run(){
        System.out.println("CPU Started");
        cycle = 0;
        running = true;
        active = true;
        playing = true;
        CPU.reset();
        while (running & !CPU.getHalt()) {            
            if (active & playing) {
                try {
                    CPU.executeOneInstruction();
                    if (cycle > cycleDelay) {
                        KyleEmulator.getEmulator().getStats().setPC(CPU.getRegisterValue(RegisterNames.PC));
                        KyleEmulator.getEmulator().getStats().setA(CPU.getRegisterValue(RegisterNames.A));
                        KyleEmulator.getEmulator().getStats().setA_ALT(CPU.getRegisterValue(RegisterNames.A_ALT));
                        KyleEmulator.getEmulator().getStats().setBC(CPU.getRegisterValue(RegisterNames.BC));
                        KyleEmulator.getEmulator().getStats().setBC_ALT(CPU.getRegisterValue(RegisterNames.BC_ALT));
                        KyleEmulator.getEmulator().getStats().setDE(CPU.getRegisterValue(RegisterNames.DE));
                        KyleEmulator.getEmulator().getStats().setDE_ALT(CPU.getRegisterValue(RegisterNames.DE_ALT));
                        KyleEmulator.getEmulator().getStats().setF(CPU.getRegisterValue(RegisterNames.F));
                        KyleEmulator.getEmulator().getStats().setF_ALT(CPU.getRegisterValue(RegisterNames.F_ALT));
                        KyleEmulator.getEmulator().getStats().setHL(CPU.getRegisterValue(RegisterNames.HL));
                        KyleEmulator.getEmulator().getStats().setHL_ALT(CPU.getRegisterValue(RegisterNames.HL_ALT));
                        KyleEmulator.getEmulator().getStats().setIX(CPU.getRegisterValue(RegisterNames.IX));
                        KyleEmulator.getEmulator().getStats().setIY(CPU.getRegisterValue(RegisterNames.IY));
                        KyleEmulator.getEmulator().getStats().setSP(CPU.getRegisterValue(RegisterNames.SP));
                        KyleEmulator.getEmulator().getStats().setLControl(KyleEmulator.getEmulator().getControls().IORead(1) & 0x000000ff);
                        KyleEmulator.getEmulator().getStats().setRControl(KyleEmulator.getEmulator().getControls().IORead(0) & 0x000000ff);
                        cycle = 0;
                    }else{
                        cycle ++;
                    }
                    Utils.halt(delay);
                } catch (Exception ex) {
                    Logger.getLogger(CPULoop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("CPU Exited");
    }
    
    public void busRequest(){
        active = false;
    }
    
    public void busRelease(){
        active  = true;
    }
    
    public void stop(){
        running  = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isActive() {
        return active;
    }

    public void setDebug(boolean selected) {
        if(selected){
            delay = 100000000;
            cycleDelay = -1;
        }else{
            delay = 100;
            cycleDelay = 20000;
        }
    }
    
}
