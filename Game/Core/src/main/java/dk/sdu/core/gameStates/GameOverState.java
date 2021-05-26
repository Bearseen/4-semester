/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.gameStates;

import dk.sdu.core.gameStates.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dk.sdu.core.main.Game;
import dk.sdu.common.services.IGamePluginService;
import dk.sdu.common.services.IHighscoreProcessingService;
import dk.sdu.core.managers.HighscoreHandler;
import java.util.Collection;

/**
 *
 * @author Samuel
 */
public class GameOverState extends GameState {

    private Texture restartTexture;
    private Texture menuTexture;

    private TextureRegion restartTextureRegion;
    private TextureRegion menuTextureRegion;

    private TextureRegionDrawable restartTexRegionDrawable;
    private TextureRegionDrawable menuTexRegionDrawable;

    private ImageButton restartButton;
    private ImageButton menuButton;

    private SpriteBatch batch;
    private Texture imageGameOver;
    
    private Stage stage;
    private Table menuTable;
    
    BitmapFont font;

    int finalScore;
   
    public GameOverState(Game game, int finalscore) {
        super(game);
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        imageGameOver = new Texture("skins/gameOverLogo.png");
        
        this.finalScore = finalscore;
    
        menuTable = new Table();

        restartTexture = new Texture(Gdx.files.internal("skins/restartButton.png"));
        restartTextureRegion = new TextureRegion(restartTexture);
        restartTexRegionDrawable = new TextureRegionDrawable(restartTextureRegion);
        restartButton = new ImageButton(restartTexRegionDrawable);
//        restartButton.setSize(180, 54);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Reset game button clicked");
                while (!game.getGameStates().isEmpty()) {
                    game.getGameStates().pop();
                }

                for (IGamePluginService plugin : game.getGamePlugins()) {
                    plugin.stop(game.getGameData(), game.getWorld());
                }
                game.getGameStates().push(new PlayState(game));

            }
        });
        menuTable.add(restartButton);
        menuTable.row().space(20);

        menuTexture = new Texture(Gdx.files.internal("skins/menuButton.png"));
        menuTextureRegion = new TextureRegion(menuTexture);
        menuTexRegionDrawable = new TextureRegionDrawable(menuTextureRegion);
        menuButton = new ImageButton(menuTexRegionDrawable);
        //settingButton.setSize(300, 54);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Menu button clicked");
                while (!game.getGameStates().isEmpty()) {
                    game.getGameStates().pop();
                }

                for (IGamePluginService plugin : game.getGamePlugins()) {
                    plugin.stop(game.getGameData(), game.getWorld());
                }
                game.getGameStates().push(new MenuState(game));
            }
        });
        menuTable.add(menuButton);
        menuTable.row().space(20);
        //stage.addActor(settingButton);
        font = new BitmapFont();
        font.setScale(3);
        LabelStyle textStyle = new LabelStyle();
        textStyle.font = font;
        
        Label label = new Label("You received a score of: "+String.valueOf(finalScore), textStyle);
        
        menuTable.add(label);
        menuTable.row();

                
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
        batch.draw(imageGameOver, 150, 550);
        batch.end();
    }
    


}
