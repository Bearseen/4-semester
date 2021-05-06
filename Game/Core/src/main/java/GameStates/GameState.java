/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import dk.sdu.core.main.Game;

/**
 *
 * @author Samuel
 */
public abstract class GameState {
    private Game game;

    public GameState(Game game) {
        this.game = game;
    }

    public abstract void dispose();

    public abstract void render();

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
