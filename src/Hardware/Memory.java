/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hardware;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import kyle.emulator.Utils;
import net.sleepymouse.microprocessor.IMemory;

/**
 *
 * @author James
 */
public class Memory implements IMemory{
    private final int[] RAM = new int[32768];
    private final int[] BIOS = new int[8192];
    private final int[] ROM = new int[4202496];
    private boolean legacyCartridge;
    
    @Override
    public synchronized int readByte(int address) {
        int i = 0;
        if(Utils.inRange(address, 0x0000, 0x1FFF)){//BIOS space
            i = BIOS[address];
        }
        if(Utils.inRange(address, 0x2000, 0x7FFF)){//ROM header space
            address = address - 24576;
            if(Utils.inRange(address, 0x2000, 0x3FFF)){
                i = ROM[address];
            }
            if(Utils.inRange(address, 0x4000, 0x7FFF)){
                i = ROM[(ROM[0x7FFF] * 16384) + address];
            }
            if((address == 0x7FFF) & legacyCartridge){
                i = 0;
            }else if((address == 0x7FFF) & !legacyCartridge){
                i = ROM[address];
            }
        }
        if(Utils.inRange(address, 0x8000, 0xFFFF)){//RAM space
            address = address - 32768;
            i = RAM[address];
        }
        return i & 0x000000ff;
    }

    @Override
    public synchronized int readWord(int address){
        int i = 0;
        if(Utils.inRange(address, 0x0000, 0x1FFF)){
            i = BIOS[address] + (BIOS[address + 1] * 256);
        }
        if(Utils.inRange(address, 0x2000, 0x7FFF)){
            address = address - 24576;
            if(Utils.inRange(address, 0x2000, 0x3FFF)){
                i = ROM[address] + (ROM[address + 1] * 256);
            }
            if(Utils.inRange(address, 0x4000, 0x7FFF)){
                i = ROM[(ROM[0x7FFF] * 16384) + address] + (ROM[(ROM[0x7FFF] * 16384) + address + 1] * 256);
            }
        }
        if(Utils.inRange(address, 0x8000, 0xFFFF)){
            address = address - 32768;
            i = RAM[address] + (RAM[address + 1] * 256);
        }
        return i & 0x0000ffff;
    }

    @Override
    public synchronized void writeByte(int address, int data) {
        if(Utils.inRange(address, 0x8000, 0xFFFF)){
            address = address - 32768;
            RAM[address] = data;
        }
    }

    @Override
    public synchronized void writeWord(int address, int data) {
        int D = (byte) (data & 0xFF);
        if(Utils.inRange(address, 0x8000, 0xFFFF)){
            address = address - 32768;
            RAM[address] = D;
        }
        if(address == 0x7FFF){
            ROM[address] = D;
        }
        address += 1;
        D = (byte) ((data >> 8) & 0xFF);
        if(Utils.inRange(address, 0x8000, 0xFFFF)){
            RAM[address] = D;
        }
        if(address == 0x7FFF){
            ROM[address] = D;
        }
    }
    public void loadBIOS(File f){
        try {
            Path path = Paths.get(f.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);
            for (int i = 0; i < 8192; i++) {
                BIOS[i] = data[i] & 0x000000ff;
            }
        } catch (IOException ex) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadROM(File f){
        try {
            Path path = Paths.get(f.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);
            for (int i = 0; i < data.length; i++) {
                ROM[i] = data[i] & 0x000000ff;
            }
        } catch (IOException ex) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int[] getRAM() {
        return RAM;
    }

    public int[] getBIOS() {
        return BIOS;
    }

    public int[] getROM() {
        return ROM;
    }
    
}
