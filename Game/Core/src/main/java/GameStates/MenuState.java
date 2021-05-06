/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

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
    private Texture settingTexture;
    private Texture exitTexture;

    private TextureRegion startTextureRegion;
    private TextureRegion settingTextureRegion;
    private TextureRegion exitTextureRegion;

    private TextureRegionDrawable startTexRegionDrawable;
    private TextureRegionDrawable settingTexRegionDrawable;
    private TextureRegionDrawable exitTexRegionDrawable;

    private ImageButton startButton;
    private ImageButton settingButton;
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

        startTexture = new Texture(Gdx.files.internal("skins/startButton.png"));
        startTextureRegion = new TextureRegion(startTexture);
        startTexRegionDrawable = new TextureRegionDrawable(startTextureRegion);
        startButton = new ImageButton(startTexRegionDrawable);
        startButton.setSize(180, 54);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Start game button clicked");
                game.getGameStates().pop();
                game.getGameStates().push(new PlayState(game));
                dispose();

            }
        });
        menuTable.add(startButton);
        menuTable.row().space(50);
        //stage.addActor(startButton);

//        settingTexture = new Texture(Gdx.files.internal("skin/settingButton.png"));
//        settingTextureRegion = new TextureRegion(settingTexture);
//        settingTexRegionDrawable = new TextureRegionDrawable(settingTextureRegion);
//        settingButton = new ImageButton(settingTexRegionDrawable);
//        settingButton.setSize(300, 54);
//        settingButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Settings button clicked");
//                game.getGameStates().pop();
//                game.getGameStates().push(new Load(game));
//            }
//        });
//        menuTable.add(settingButton);
//        menuTable.row();
//        //stage.addActor(settingButton);

        exitTexture = new Texture(Gdx.files.internal("skins/exitButton.png"));
        exitTextureRegion = new TextureRegion(exitTexture);
        exitTexRegionDrawable = new TextureRegionDrawable(exitTextureRegion);
        exitButton = new ImageButton(exitTexRegionDrawable);
        exitButton.setSize(204, 54);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked");
                System.exit(0);
            }
        });
        menuTable.add(exitButton);
        menuTable.setFillParent(true);
        //menuTable.debug();
        stage.addActor(menuTable);
        //stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        batch.draw(gameLogo, 105, 600);
        batch.draw(groupLogo, 45, 10);
        batch.end();
    }
    
}
