/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kyle.emulator;

/**
 *
 * @author James
 */
public class Utils {
    
    public static boolean inRange(int input, int lowerBound, int upperBound){
        return (input == lowerBound) | (input == upperBound) | ((input < upperBound) & (input > lowerBound));
    }
    
    public static void halt(long nanos){
        long elapsed;
        final long startTime = System.nanoTime();
        do {
            elapsed = System.nanoTime() - startTime;
        } while (elapsed < nanos);
    }
}