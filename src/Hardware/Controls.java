/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hardware;

import java.awt.event.KeyEvent;
import net.sleepymouse.microprocessor.IBaseDevice;

/**
 *
 * @author James
 */
public class Controls implements IBaseDevice{
    private byte leftControl = (byte)0xFF;
    private byte rightControl = (byte)0xFF;
    private boolean reading = false;
    private int UP;
    private int DOWN;
    private int LEFT;
    private int RIGHT;
    private int A;
    private int B;
    private int START;
    private int SELECT;
    private int LTRIG;
    private int RTRIG;
    
    @Override
    public int IORead(int address) {
        if((address&1) == 0){//even
            return rightControl;
        }else{//odd
            return leftControl;
        }
    }

    @Override
    public void IOWrite(int address, int data) {
        
    }

    public byte getLeftControl() {
        return leftControl;
    }

    public void setLeftControl(byte leftControl) {
        this.leftControl = leftControl;
    }

    public byte getRightControl() {
        return rightControl;
    }

    public void setRightControl(byte rightControl) {
        this.rightControl = rightControl;
    }

    public boolean isReading() {
        return reading;
    }

    public void setReading(boolean reading) {
        this.reading = reading;
    }

    public void keyReleased(KeyEvent evt) {
        int input = evt.getKeyCode();
        if(input == A){
            rightControl |= (1<<3);
        }else if(input == B){
            rightControl |= (1<<2);
        }
        else if(input == START){
            rightControl |= (1<<1);
        }
        else if(input == SELECT){
            rightControl |= (1<<0);
        }
        else if(input == LEFT){
            leftControl |= (1<<3);
        }
        else if(input == RIGHT){
            leftControl |= (1<<2);
        }
        else if(input == UP){
            leftControl |= (1<<1);
        }
        else if(input == DOWN){
            leftControl |= (1<<0);
        }
        else if(input == LTRIG){
            leftControl |= (1<<4);
        }
        else if(input == RTRIG){
            rightControl |= (1<<4);
        }
    }

    public void keyPressed(KeyEvent evt) {
        int input = evt.getKeyCode();
        if(input == A){
            rightControl &= ~(1<<3);
        }else if(input == B){
            rightControl &= ~(1<<2);
        }
        else if(input == START){
            rightControl &= ~(1<<1);
        }
        else if(input == SELECT){
            rightControl &= ~(1<<0);
        }
        else if(input == LEFT){
            leftControl &= ~(1<<3);
        }
        else if(input == RIGHT){
            leftControl &= ~(1<<2);
        }
        else if(input == UP){
            leftControl &= ~(1<<1);
        }
        else if(input == DOWN){
            leftControl &= ~(1<<0);
        }
        else if(input == LTRIG){
            leftControl &= ~(1<<4);
        }
        else if(input == RTRIG){
            rightControl &= ~(1<<4);
        }
    }

    public int getUP() {
        return UP;
    }

    public void setUP(int UP) {
        this.UP = UP;
    }

    public int getDOWN() {
        return DOWN;
    }

    public void setDOWN(int DOWN) {
        this.DOWN = DOWN;
    }

    public int getLEFT() {
        return LEFT;
    }

    public void setLEFT(int LEFT) {
        this.LEFT = LEFT;
    }

    public int getRIGHT() {
        return RIGHT;
    }

    public void setRIGHT(int RIGHT) {
        this.RIGHT = RIGHT;
    }

    public int getA() {
        return A;
    }

    public void setA(int A) {
        this.A = A;
    }

    public int getB() {
        return B;
    }

    public void setB(int B) {
        this.B = B;
    }

    public int getSTART() {
        return START;
    }

    public void setSTART(int START) {
        this.START = START;
    }

    public int getSELECT() {
        return SELECT;
    }

    public void setSELECT(int SELECT) {
        this.SELECT = SELECT;
    }

    public int getLTRIG() {
        return LTRIG;
    }

    public void setLTRIG(int LTRIG) {
        this.LTRIG = LTRIG;
    }

    public int getRTRIG() {
        return RTRIG;
    }

    public void setRTRIG(int RTRIG) {
        this.RTRIG = RTRIG;
    }
    
}
