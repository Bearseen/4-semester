/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.gameStates;

import dk.sdu.core.managers.ModuleHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dk.sdu.core.managers.GameInputProcessor;
import dk.sdu.core.main.Game;
import java.util.Stack;

/**
 *
 * @author Samuel
 */
public class SettingsState extends GameState{
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture imageSetting;
    private Table mainTable;

    private CheckBox aI;
    private CheckBox player;
    private CheckBox enemy;
    private CheckBox sheep;

    private Texture menuTexture;
    private TextureRegion menuTextureRegion;
    private TextureRegionDrawable menuTexRegionDrawable;
    private ImageButton menuButton;

    private Stack<GameState> gameStates;
    private Game game;

    private ModuleHandler moduleHandler;

    private void addModules() {
        for (Component component : ModuleHandler.getComponents()) {
            String buttonText = component.getName();
            CheckBox button = new CheckBox(buttonText, skin);

            if (component.isActive()) {
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        Gdx.graphics.setContinuousRendering(button.isChecked());
                        ModuleHandler.unloadComponent(component);
                    }
                });
            } else {
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        Gdx.graphics.setContinuousRendering(button.isChecked());
                        ModuleHandler.loadComponent(component);
                    }
                });
            }

            mainTable.add(button);
            mainTable.row();
        }

    }

    public SettingsState(Game game) {
        super(game);

        this.moduleHandler = ModuleHandler.getInstance();

        this.gameStates = game.getGameStates();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin();
        batch = new SpriteBatch();
        imageSetting = new Texture("skins/settingsLogo.png");
        mainTable = new Table();
        addModules();

        menuTexture = new Texture(Gdx.files.internal("skins/returnButton.png"));
        menuTextureRegion = new TextureRegion(menuTexture);
        menuTexRegionDrawable = new TextureRegionDrawable(menuTextureRegion);
        menuButton = new ImageButton(menuTexRegionDrawable);
        if (playStateOn()) {
            menuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Return to game button clicked");
                    game.getGameStates().pop();
                    ((PlayState) (game.getGameStates().peek())).setPaused(false);
                    Gdx.input.setInputProcessor(new GameInputProcessor(game.getGameData()));
                    dispose();
                }
            });
        } else {
            menuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Return to menu button clicked");
                    game.getGameStates().pop();
                    game.getGameStates().push(new MenuState(game));
                    dispose();
                }
            });
        }

        mainTable.add(menuButton);
        mainTable.row();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    private boolean playStateOn() {
        Stack<GameState> gameStates = game.getGameStates();
        if (!gameStates.isEmpty()) {
            if (gameStates.get(gameStates.size() - 1).getClass() == PlayState.class) {
                return true;
            }
        }
        return false;
    }

    public void skin() {
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 3, (int) Gdx.graphics.getHeight() / 13, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));
        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.up = skin.newDrawable("background", Color.WHITE);
        checkBoxStyle.fontColor = Color.DARK_GRAY;
        checkBoxStyle.font = skin.getFont("default");
        skin.add("default", checkBoxStyle);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        batch.draw(imageSetting, 50, 590);
        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
