package Hardware;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Audio {
    
    private final int sampleRate;
    private int volume;
    private Clip clip;
    private final AudioFormat af; 
    private final byte[] buf;

    public Audio() {
        this.sampleRate = 8000;
        this.volume = 127;
        this.af = new AudioFormat(sampleRate, 8, 2, true, false);
        buf = new byte[16000];
    }
    
    public void tone(int freq){
        if(freq == 0){
            return;
        }
        int frequency = freq;
        if ( clip!=null ) {
            clip.stop();
            clip.close();
        } else {
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int frame = 0;
        for(int i=0; i<8000; i++){
            if(frame > (8000 / frequency * 10)){
                buf[i*2] = (byte)volume;
                frame = 0;
            }else{
                buf[i*2] = 0;
            }
            frame ++;
            buf[(i*2)+1] = buf[i*2];
        }
        try {
            AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(buf), af, buf.length/2 );
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void noTone(){
        if(clip == null){
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        clip.stop();
        clip.close();
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    
}