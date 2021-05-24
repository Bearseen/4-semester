package dk.sdu.core.main;

import dk.sdu.core.gamestates.GameState;
import dk.sdu.core.gamestates.MenuState;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.RangedWeaponPart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;
import dk.sdu.common.services.IPostEntityProcessingService;
import dk.sdu.core.managers.AssetsHandler;
import dk.sdu.core.managers.GameInputProcessor;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dk.sdu.common.assets.Tile;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.core.gamestates.PlayState;
import java.util.Stack;


public class Game implements ApplicationListener {

    public static OrthographicCamera cam;
    
    private final Lookup lookup = Lookup.getDefault();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;

    private final GameData gameData = new GameData();
    private World world = new World();
    private ShapeRenderer sr;
    private AssetsHandler assetsHandler;
    private SpriteBatch spriteBatch;
    
    private Stack<GameState> gameStates;

    private BitmapFont font;
    private int score;
    private String scoreName;
    private Stage stage;
    private Table table;
    private Label label;
    
    private RangedWeaponPart rangedWeaponPart; // skal måske instantieres

    @Override
    public void create() {
        assetsHandler = new AssetsHandler();
        spriteBatch = new SpriteBatch();
        sr = new ShapeRenderer();

        this.gameStates = new Stack<>();
                
        /** ToDo: SET FONT
         *
         * Burde hente font fra fonts folder: "Core/src/Main/resources/fonts/"
         * Ikke sikker på om den henter fonten, derfor har jeg udkommenteret det...
         * */
        //FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace Bold.ttf")); // todo: hent font
        //font = gen.generateFont(20);
        
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();
        
        gameStates.push(new MenuState(this));
        //gameStates.push(new PlayState(this));
//        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
        
        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

//        for (IGamePluginService plugin : result.allInstances()) {
//            plugin.start(gameData, world);
//            gamePlugins.add(plugin);
//        }
    }

    @Override
    public void render() {
//        clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
//        clear screen to white
//        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        gameData.getKeys().updateMouse(Gdx.input.getX(), gameData.getDisplayHeight() - Gdx.input.getY());


//        update();
//        draw();
        try {
        this.gameStates.peek().render();
    } catch(Exception e){
        
    }
    }
//    private void draw() {
//        try {
//            spriteBatch.begin();
//            
//            for (Entity tiles : world.getEntities(Tile.class)){
//                assetsHandler.drawEntity(tiles, spriteBatch);
//            }
//            for (Entity entity : world.getEntities()){
//                if (!entity.getClass().equals(Tile.class)) {
//                    assetsHandler.drawEntity(entity, spriteBatch);
//                }
//            }
// 
//        } catch (Exception e){
//            System.out.println(e);
//            
//        } finally {
//            spriteBatch.end();
//            
//            for (Entity entity : world.getEntities()) {
//                if (entity.hasPart(LifePart.class)) {
//                assetsHandler.drawHealth(entity);
//                }
//            }
//        }
//        
//    }
//    private void update() {
//        // Update
//        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
//            entityProcessorService.process(gameData, world);
//        }
//
//        // Post Update
//        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
//            postEntityProcessorService.process(gameData, world);
//        }
//    }

    private void draw() {
        try {
            spriteBatch.begin();
            
            for (Entity tiles : world.getEntities(Tile.class)){
                assetsHandler.drawEntity(tiles, spriteBatch);
            }
            for (Entity entity : world.getEntities()){
                if (!entity.getClass().equals(Tile.class)) {
                    assetsHandler.drawEntity(entity, spriteBatch);
                }
            }
            
            /**
             * ToDo: Draw Ammo Counter:
             *
             * Bliver ikke drawet, måske drawes det bag tiles?
             **/
            font.draw(spriteBatch, /*Integer.toString(rangedWeaponPart.getAmmo())*/"ammo: 5", 10, 10);
 
        } catch (Exception e){
            System.out.println(e);
            
        } finally {
            spriteBatch.end();   
        }
        
//        for (Entity entity : world.getEntities()) {
//            sr.setColor(1, 1, 1, 1);
//
//            sr.begin(ShapeRenderer.ShapeType.Line);
//
//            float[] shapex = entity.getShapeX();
//            float[] shapey = entity.getShapeY();
//
//            for (int i = 0, j = shapex.length - 1;
//                    i < shapex.length;
//                    j = i++) {
//
//                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
//            }
//
//            sr.end();
//        }
    }
   
    @Override
    public void resize(int width, int height) {
  
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }

    };
    
    public World getWorld() {
        return world;
    }
    
      public AssetsHandler getAssetsHandler() {
        return assetsHandler;
    }
    
    public SpriteBatch getSpriteBatch(){
        return spriteBatch;
    }
    
    public Stack<GameState> getGameStates() {
        return gameStates;
    }
    
    public GameData getGameData() {
        return gameData;
    }
    
    public LookupListener getLookupListener() {
        return lookupListener;
    }
    
    public List<IGamePluginService> getGamePlugins() {
        return gamePlugins;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public Lookup.Result<IGamePluginService> getResult() {
        return result;
    }
   
}
