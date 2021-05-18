/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.managers;

/**
 *
 * @author Samuel
 */
public class WaveHandler {
    private int currentWave;

    public WaveHandler() {
        this.currentWave = 0;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public void setNextWave() {
        currentWave += 1;
    }
}
