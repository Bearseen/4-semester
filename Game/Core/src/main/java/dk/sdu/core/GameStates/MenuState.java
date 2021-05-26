/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dk.sdu.core.main.Game;
import dk.sdu.common.data.GameData;
import java.util.Stack;

/**
 *
 * @author Samuel
 */
public class MenuState extends GameState {
    private Stage stage;
    private Table menuTable;

    private Texture startTexture;
    private Texture settingsTexture;
    private Texture exitTexture;

    private TextureRegion startTextureRegion;
    private TextureRegion settingsTextureRegion;
    private TextureRegion exitTextureRegion;

    private TextureRegionDrawable startTexRegionDrawable;
    private TextureRegionDrawable settingsTexRegionDrawable;
    private TextureRegionDrawable exitTexRegionDrawable;

    private ImageButton startButton;
    private ImageButton settingsButton;
    private ImageButton exitButton;

    private SpriteBatch batch;
    private Texture gameLogo;
    private Texture groupLogo;
    private GameData gameData;

    public MenuState(Game game) {
        super(game);
        System.out.println("Starting game menu");
        stage = new Stage(new ScreenViewport());

        gameLogo = new Texture("skins/gameLogo.png");
        groupLogo = new Texture("skins/groupLogo.png");

        menuTable = new Table();

        this.batch = game.getSpriteBatch();
        this.gameData = game.getGameData();
        
//        stage.addActor(startButton);
                

        startTexture = new Texture(Gdx.files.internal("skins/startButton.png"));
        startTextureRegion = new TextureRegion(startTexture);
        startTexRegionDrawable = new TextureRegionDrawable(startTextureRegion);
        startButton = new ImageButton(startTexRegionDrawable);
        startButton.setSize(90, 27);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Start game button clicked");
                game.getGameStates().pop();
                game.getGameStates().push(new PlayState(game));
                dispose();
            }
        });        
        menuTable.add(startButton);
        menuTable.row();
        
        settingsTexture = new Texture(Gdx.files.internal("skins/settingsButton.png"));
        settingsTextureRegion = new TextureRegion(settingsTexture);
        settingsTexRegionDrawable = new TextureRegionDrawable(settingsTextureRegion);
        settingsButton = new ImageButton(settingsTexRegionDrawable);
        settingsButton.setSize(300, 54);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Settings button clicked");
                game.getGameStates().pop();
                game.getGameStates().push(new SettingsState(game));
            }
        });
        menuTable.add(settingsButton);
        menuTable.row().space(50);

        exitTexture = new Texture(Gdx.files.internal("skins/exitButton.png"));
        exitTextureRegion = new TextureRegion(exitTexture);
        exitTexRegionDrawable = new TextureRegionDrawable(exitTextureRegion);
        exitButton = new ImageButton(exitTexRegionDrawable);
        exitButton.setSize(102, 27);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked");
                System.exit(0);
            }
        });
        menuTable.add(exitButton);
        menuTable.setFillParent(true);
        menuTable.row().space(50);
        menuTable.toFront();
        stage.addActor(menuTable);
        
//        menuTable.debug();
//        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        
        batch.begin();
        batch.draw(gameLogo, 47 , 47);
        batch.draw(groupLogo, 20 , 20);
        batch.end();
        
        Gdx.input.setInputProcessor(stage);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    
}
