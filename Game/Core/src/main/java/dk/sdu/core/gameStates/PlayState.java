/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.common.assets.Tile;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;
import dk.sdu.common.services.IPostEntityProcessingService;
import dk.sdu.core.main.Game;
import dk.sdu.core.managers.AssetsHandler;
import dk.sdu.core.managers.GameInputProcessor;
import java.util.Stack;
import org.openide.util.Lookup;

/**
 *
 * @author Samuel
 */
public class PlayState extends GameState{
    private SpriteBatch spriteBatch;
    private GameData gameData;
    private World world;
    private AssetsHandler assetsHandler;
//    private WaveManager waveManager;
    private final Lookup lookup; 
    private boolean paused = false;
    private Stack<GameState> gameStates;
    private Game game;
    private Lookup.Result<IGamePluginService> result;

    public PlayState(Game game) { 
        super(game);
            this.game = game;
            this.spriteBatch = game.getSpriteBatch();
            this.gameData = game.getGameData();
            this.world = game.getWorld();
            this.assetsHandler = game.getAssetsHandler();
    //        this.waveManager = new WaveManager();
            this.lookup = game.getLookup();
            this.paused = false;
            this.gameStates = game.getGameStates();

            for (IGamePluginService plugin : game.getResult().allInstances()) {
                plugin.start(gameData, world);
                game.getGamePlugins().add(plugin);
            }
            Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
    }



    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : lookup.lookupAll(IEntityProcessingService.class)) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : lookup.lookupAll(IPostEntityProcessingService.class)) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        spriteBatch.begin();

        for (Entity entity : world.getEntities(Tile.class)) {
            assetsHandler.drawEntity(entity, this.spriteBatch);
        }

        for (Entity entity : world.getEntities()) {
            if (!entity.getClass().equals(Tile.class)) {
                assetsHandler.drawEntity(entity, this.spriteBatch);

            }
        }

        spriteBatch.end();

        for (Entity entity : world.getEntities()) {
            if (entity.hasPart(LifePart.class)) {
                assetsHandler.drawHealth(entity);
            }
        }
    }
    
    private void endGame() {
        if (this.gameData.isEndGame()) {
            System.out.println("Game ending");
//            this.gameData.getKeys().resetKeys();
            this.gameData.setEndGame(false);
            for (IGamePluginService plugin : game.getGamePlugins()) {
                plugin.stop(game.getGameData(), game.getWorld());
            }

            game.getGameStates().pop();
            game.getGameStates().push(new GameOverState(game));
        }
    }
    
    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        if (this.paused == false) {
            update();
            draw();
//            wave();
//            pause();
            endGame();
        }
    }
}