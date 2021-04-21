/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.common.services.IMapService;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.common.data.GameData;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
/**
 *
 * @author Villy
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IMapService.class)})

public class Map implements IMapService {
    
    private static OrthographicCamera cam;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    @Override
    public void create(GameData gameData) {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("assets/images/Map2.tmx", TiledMap.class);
        manager.finishLoading();
        
        map = manager.get("assets/images/Map2.tmx", TiledMap.class);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(100, 100 * (h / w));

        float unitScale = 1 / 8f;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);


        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.update();

    }

    @Override
    public void render() {
        handleInput();
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera and render
        cam.update();
        renderer.setView(cam);
        renderer.render();
    }
  
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
        }
        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);
        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }
}
