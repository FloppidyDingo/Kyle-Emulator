/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hardware;

import kyle.emulator.AudioThread;
import kyle.emulator.KyleEmulator;

/**
 *
 * @author James
 */
public class DSP{
    
    private final int[] color;
    private final int[] tileBuffer;
    private final int[] RAM;
    private final int[] memory;
    private int location;
    private final Thread audioThread;
    private final AudioThread audio;
    private int CX;
    private int CY;

    public DSP() {
        this.color = new int[16];
        this.tileBuffer = new int[8192];
        this.memory = new int[20480];
        this.RAM = new int[2048];
        audio = new AudioThread();
        audioThread = new Thread(audio);
        audioThread.start();
    }

    public int[] process() {
        //audio.setFreq(readData(0xE821) * 20); // read audio data from RAM
        CX = KyleEmulator.getEmulator().getMemory().readWord(0x8001);
        CY = KyleEmulator.getEmulator().getMemory().readWord(0x8003);
        int i;
        for (i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
        for (i = 0; i < 16; i++) { // load color pallette
            int c = readData(0xB580 + i);
            color[i] = c;
        }
        for (i = 0; i < 8192; i++) { // buffer tiles
            tileBuffer[i] = readData(0x9580 + i);
        }
        for (i = 0; i < 512; i++) { // render background layer
            RAM[i] = readData(0x8100 + i);
        }
        for(i = 0; i < 512; i++){
            if(RAM[i] == 0xAA){
                writeTile(RAM[i + 1], RAM[i + 2], RAM[i + 3]);
            }
            i += 3;
        }
        for (i = 0; i < 2048; i++) { // render first half of Map RAM
            RAM[i] = readData(0x8300 + i);
        }
        
        for (i = 0; i < 2048; i++) { // render second half of Map RAM
            RAM[i] = readData(0x8B00 + i);
        }
        
        for (i = 0; i < 128; i++) { // read Sprite RAM
            RAM[i] = readData(0x9300 + i);
        }
        for(i = 0; i < 128; i++){
            if(RAM[i] == 0xAA){
                writeTile(RAM[i + 1], RAM[i + 2], RAM[i + 3]);
            }
            i += 3;
        }
        for (i = 0; i < 512; i++) { // read foreground layer
            RAM[i] = readData(0x9380 + i);
        }
        for(i = 0; i < 512; i++){
            if(RAM[i] == 0xAA){
                writeTile(RAM[i + 1], RAM[i + 2], RAM[i + 3]);
            }
            i += 3;
        }
        return memory;
    }
    
    private int readData(int address){
        return KyleEmulator.getEmulator().getMemory().readByte(address);
    }

    private void writeTile(int x, int y, int tile) {
        x -= 4;
        y -= 4;
        for(int x2 = 0; x2 < 4; x2++){
            for(int y2 = 0; y2 < 8; y2++){
                if ((y + y2 < 128) & (x + x2 < 160)) {
                    memory[((y + y2) * 160) + (x + x2)] = color[lowNyb(tileBuffer[(tile * 32) + (y2 * 4) + (x2)])];
                    memory[((y + y2) * 160) + (x + 1 + x2)] = color[highNyb(tileBuffer[(tile * 32) + (y2 * 4) + (x2)])];
                }
            }
        }
    }

    private int lowByte(int color) {
        return (color & 0x00FF);
    }

    private int highByte(int color) {
        return ((color & 0xFF00) >> 8);
    }
    
    private int lowNyb(int d){
        return d & 0x0F;
    }
    
    private int highNyb(int d){
        return (d & 0xF0) >> 4;
    }
    
    public void stop(){
        audio.setActive(false);
    }
    
    public void init(){
        audio.setActive(true);
    }
    
    public void reset(){
        stop();
        for (int i = 0; i < color.length; i++) {
            color[i] = 0;
        }
        for (int i = 0; i < tileBuffer.length; i++) {
            tileBuffer[i] = 0;
        }
        for (int i = 0; i < RAM.length; i++) {
            RAM[i] = 0;
        }
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
        init();
    }
}
