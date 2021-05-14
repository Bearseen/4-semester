package dk.sdu.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
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
import dk.sdu.common.assets.Tile;
import dk.sdu.common.data.entityparts.LifePart;


public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    
    private final Lookup lookup = Lookup.getDefault();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;

    private final GameData gameData = new GameData();
    private World world = new World();
    private ShapeRenderer sr;
    private AssetsHandler assetsHandler;
    private SpriteBatch spriteBatch;

    @Override
    public void create() {
        assetsHandler = new AssetsHandler();
        spriteBatch = new SpriteBatch();
        sr = new ShapeRenderer();
        
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }

    @Override
    public void render() {
        // clear screen to black
//        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        update();
        draw();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

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
 
        } catch (Exception e){
            System.out.println(e);
            
        } finally {
            spriteBatch.end();
            
            for (Entity entity : world.getEntities()) {
                if (entity.hasPart(LifePart.class)) {
                assetsHandler.drawHealth(entity);
                }
            }
        }
        
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
}
