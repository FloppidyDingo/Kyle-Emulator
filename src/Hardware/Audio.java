package Hardware;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.MixerMono;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.WhiteNoise;

public class Audio {
    
    private final MixerMono mixer;
    private final Synthesizer synth;
    private final SquareOscillator channelA;
    private final SquareOscillator channelB;
    private final WhiteNoise channelC;
    private final LineOut line;
    private double volume;

    public Audio() {
        synth = JSyn.createSynthesizer();
        channelA = new SquareOscillator();
        channelB = new SquareOscillator();
        channelC = new WhiteNoise();
        line = new LineOut();
        mixer = new MixerMono(3);
        synth.add(line);
        synth.add(channelA);
        synth.add(channelB);
        synth.add(channelC);
        channelA.output.connect(0, mixer.input, 0);
        channelB.output.connect(0, mixer.input, 0);
        channelC.output.connect(0, mixer.input, 0);
        mixer.output.connect(0, line.input, 0);
        mixer.output.connect(0, line.input, 1);
        channelA.frequency.set(0);
        channelB.frequency.set(0);
        channelC.amplitude.set(0);
        synth.start();
        line.start();
    }
    
    public void toneA(int freq){
        if(freq != 0){
            channelA.frequency.set(freq);
        }else{
            channelA.frequency.set(0);
        }
    }
    
    public void toneB(int freq){
        if(freq != 0){
            channelB.frequency.set(freq);
        }else{
            channelB.frequency.set(0);
        }
    }
    
    public void noise(boolean active){
        if(active){
            channelC.amplitude.set(10);
        }else{
            channelC.amplitude.set(0);
        }
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        mixer.amplitude.set(volume / 10);
    }
    
}