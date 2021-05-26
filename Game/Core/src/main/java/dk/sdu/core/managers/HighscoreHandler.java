/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.managers;

/**
 *
 * @author Villy
 */
public class HighscoreHandler {
    
    private int score;
    
    public HighscoreHandler(int score){
        this.score = 0;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
}
