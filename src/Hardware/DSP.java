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
    private final int[] buffer;
    private final byte[] VRAM;
    private final int[] memory;
    private int location;
    private final Thread audioThread;
    private final AudioThread audio;

    public DSP() {
        this.color = new int[15];
        this.buffer = new int[4096];
        this.VRAM = new byte[9600];
        this.memory = new int[153600];
        audio = new AudioThread();
        audioThread = new Thread(audio);
        audioThread.start();
    }

    public int[] process() {
        audio.setFreq(readData(0xE821) * 20); // read audio data from RAM
        int i;
        int i2;
        for (i = 1; i < 31; i++) { //load color pallette
            int a = readData(0xB481 + i);
            i++;
            int b = readData(0xB481 + i);
            int c = (((b & 0xFF) << 8) | (a & 0xFF));
            color[(i / 2) - 1] = c;
        }
        for (i = 0; i < 4096; i++) { //buffer first 32 tiles
            buffer[i] = readData(0x9481 + i);
        }
        for (i = 0; i < 10; i++) { //read background layer
            for (i2 = 0; i2 < 10; i2++) {
                writeTile(i2 * 10, 1 * 10, readData(0x8000 + ((i * 10) + i2)));
            }
        }
        for (i = 0; i < 682; i++) { // read Map RAM
            if (readData(0x8080 + (i * 6)) != 0) {
                int x = readData(0x8080 + (i * 6) + 1) + (readData(0x8080 + (i * 6) + 2) * 256);
                int y = readData(0x8080 + (i * 6) + 3) + (readData(0x8080 + (i * 6) + 4) * 256);
                writeTile(x, y, readData(0x8080 + (i * 6) + 5));
            }
        }
        for (i = 0; i < 170; i++) { // read Sprite RAM
            if (readData(0x9081 + (i * 6)) != 0) {
                int x = readData(0x9081 + (i * 6) + 1) + (readData(0x9081 + (i * 6) + 2) * 256);
                int y = readData(0x9081 + (i * 6) + 3) + (readData(0x9081 + (i * 6) + 4) * 256);
                writeTile(x, y, readData(0x9081 + (i * 6) + 5));
            }
        }
        for (i = 0; i < 9600; i++) { //read overlay layer
            int d = readData(0xB4A1 + i);
            if ((d & 0x0F) != 0) {
                VRAM[i] = (byte) ((d & 0x0F) + (VRAM[i] & 0xF0));
            }
            if ((d & 0xF0) != 0) {
                VRAM[i] = (byte) ((d & 0xF0) + (VRAM[i] & 0x0F));
            }
        }
        draw();
        return memory;
    }
    
    private int readData(int address){
        return KyleEmulator.getEmulator().getMemory().readByte(address);
    }

    private void writeTile(int x, int y, int tile) {
        int col;
        int ref;
        if(tile < 32){
            int i;
            int i2;
            for (i = 0; i < 8; i++) { // X pixel
                for (i2 = 0; i2 < 16; i2++) { // Y pixel
                    ref = ((y + i) * 80) + (x + i2);
                    col = buffer[(tile * 256) + ((i2 * 8) + i)];
                    if ((col & 0x0F) != 0) {
                        VRAM[ref] = (byte)((col & 0x0F) + (VRAM[ref] & 0xF0));
                    }
                    if ((col & 0xF0) != 0) {
                        VRAM[ref] = (byte)((col & 0xF0) + (VRAM[ref] & 0x0F));
                    }
                }
            }
        }else{
            int i;
            int i2;
            for (i = 0; i < 8; i++) { // X pixel
                for (i2 = 0; i2 < 16; i2++) { // Y pixel
                    if (((i + x) < 160) & ((i2 + y) < 120)) {
                        ref = ((y + i) * 80) + (x + i2);
                        col = readData((tile * 256) + ((i2 * 8) + i));
                        if ((col & 0x0F) != 0) {
                            VRAM[ref] = (byte)((col & 0x0F) + (VRAM[ref] & 0xF0));
                        }
                        if ((col & 0xF0) != 0) {
                            VRAM[ref] = (byte)((col & 0xF0) + (VRAM[ref] & 0x0F));
                        }
                    }
                }
            }
        }
    }

    private void draw() {
        int i;
        int i2;
        int i3;
        for (i2 = 0; i2 < 120; i2++) {
            for (i3 = 0; i3 < 2; i3++) {
                for (i = 79; i != -1; i--) {
                    int ref = VRAM[(i2 * 80) + i];
                    int c;
                    if((lowNyb(ref) - 1) == -1){
                        c = 0;
                    }else{
                        c = color[lowNyb(ref) - 1];
                    }
                    writeData(lowByte(c));
                    writeData(highByte(c));
                    writeData(lowByte(c));
                    writeData(highByte(c));
                    i--;
                    if((highNyb(ref) - 1) == -1){
                        c = 0;
                    }else{
                        c = color[highNyb(ref) - 1];
                    }
                    writeData(lowByte(c));
                    writeData(highByte(c));
                    writeData(lowByte(c));
                    writeData(highByte(c));
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
    
    private void writeData(int d) {
        memory[location] = d;
        location++;
        if (location == 153600) {
            location = 0;
        }
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
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = 0;
        }
        for (int i = 0; i < VRAM.length; i++) {
            VRAM[i] = 0;
        }
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
        init();
    }
}
