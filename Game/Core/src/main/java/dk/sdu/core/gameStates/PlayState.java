/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.common.tile.Tile;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.GameKeys;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.WeaponPart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;
import dk.sdu.common.services.IHighscoreProcessingService;
import dk.sdu.common.services.IPostEntityProcessingService;
import dk.sdu.common.services.IWavePluginService;
import dk.sdu.core.main.Game;
import dk.sdu.core.managers.AssetsHandler;
import dk.sdu.core.managers.GameInputProcessor;
import dk.sdu.core.managers.HighscoreHandler;
import dk.sdu.core.managers.WaveHandler;
import java.util.Collection;
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
    private WaveHandler waveHandler;
    private final Lookup lookup; 
    private boolean paused = false;
    private Stack<GameState> gameStates;
    private Game game;
    private Lookup.Result<IGamePluginService> result;
    
    private HighscoreHandler highscoreHandler;
    
   // private int score = 10;

    BitmapFont font;


    public PlayState(Game game) { 
        super(game);
        
            font = new BitmapFont();
            
            this.game = game;
            this.spriteBatch = game.getSpriteBatch();
            this.gameData = game.getGameData();
            this.world = game.getWorld();
            this.assetsHandler = game.getAssetsHandler();
            this.waveHandler = new WaveHandler();
            this.lookup = game.getLookup();
            this.paused = false;
            this.gameStates = game.getGameStates();
            
            this.highscoreHandler = new HighscoreHandler(0);

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
    
    private void pause() {

        if (this.gameData.getKeys().isDown(GameKeys.ESCAPE)) {
//            this.gameData.getInput().setKeyStatus(Input.ESCAPE, false);
            this.gameData.getKeys().updateKeys();
            this.paused = true;
            System.out.println("Pause Game");
            this.game.getGameStates().push(new SettingsState(this.game));
        }
    }
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    private void wave() {
        highscore();
        Collection<? extends IWavePluginService> waves = lookup.lookupAll(IWavePluginService.class);

        boolean endWave = false;
        for (IWavePluginService wave : waves) {
            if (wave.stopWave(gameData, world)) {
                endWave = true;
                break;
            }
        }

        if (endWave) {
            this.waveHandler.setNextWave();

            for (IWavePluginService wave : waves) {
                wave.startWave(this.gameData, this.world, this.waveHandler.getCurrentWave());
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
            game.getGameStates().push(new GameOverState(game, highscoreHandler.getScore()));
            
            Collection<? extends IHighscoreProcessingService> highscores = lookup.lookupAll(IHighscoreProcessingService.class);
            for (IHighscoreProcessingService highscore : highscores) {
                highscore.resetScore();

            }
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
            wave();
            highscore();
            pause();
            endGame();
        }
    }
    
    public void highscore(){
        Collection<? extends IHighscoreProcessingService> highscores = lookup.lookupAll(IHighscoreProcessingService.class);
        for(IHighscoreProcessingService highscore : highscores){
                highscoreHandler.setScore(highscore.highscoreProcess(gameData, world));
            
        }
        spriteBatch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.setScale(3);
        font.draw(spriteBatch, "Score: "+String.valueOf(highscoreHandler.getScore()), 25, 100);
        spriteBatch.end();
    }
    
}