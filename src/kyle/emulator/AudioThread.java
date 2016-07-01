/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kyle.emulator;

import Hardware.Audio;

/**
 *
 * @author James
 */
public class AudioThread implements Runnable{
    private int prevFreq;
    private int freq;
    private static final Audio audio = new Audio();
    private boolean running;
    private boolean active;
    
    @Override
    public void run() {
        running = true;
        active = true;
        while (running) {            
            if (active) {
                if (freq == 0) {
                    audio.noTone();
                } else if (prevFreq != freq) {
                    audio.tone(freq);
                    prevFreq = freq;
                }
            }
        }
        audio.noTone();
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }
    
    public void stop(){
        running = false;
        audio.noTone();
    }
    
    public synchronized void setVolume(int v){
        audio.setVolume(v);
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
        if(!active){
            audio.noTone();
        }else{
            audio.tone(freq);
        }
    }
    
}
