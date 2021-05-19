/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.managers;

/**
 *
 * @author tarik
 */
public class HighscoreHandler {
    
    private int score;
    
    public HighscoreHandler(){
        this.score = 0;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void addScore(int score){
        this.score += score;
    }
    
}
