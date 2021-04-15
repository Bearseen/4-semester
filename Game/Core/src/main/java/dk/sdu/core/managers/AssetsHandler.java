/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.entityparts.PositionPart;
/**
 *
 * @author Samuel
 */
public class AssetsHandler {
    public final AssetManager assetManager;
    private ShapeRenderer sr;

    public AssetsHandler() {
        assetManager = new AssetManager();
        loadAssets();
        assetManager.finishLoading();
        this.sr = new ShapeRenderer();
    }

    private void loadAssets() {
        assetManager.load("assets/grass.png", Texture.class);
        System.out.println("Test");
        assetManager.load("assets/tree.png", Texture.class);
        assetManager.load("assets/wall.png", Texture.class);
        //Loader all assets png - ADD HERE
    }

    public Texture getAsset(String name) {
        return assetManager.get(name, Texture.class);
    }

    public void drawEntity(Entity entity, SpriteBatch spriteBatch) {
        if (entity.getImage() != null) {
            Texture texture = this.assetManager.get("assets/" + entity.getImage());
            Sprite sprite = new Sprite(texture);
            PositionPart position = entity.getPart(PositionPart.class);

            sprite.setScale(0.35f);
            if (position.getRadians() > Math.PI / 2 || position.getRadians() < -(Math.PI / 2)) {
                sprite.flip(true, false);
            }

            float x = position.getX() - sprite.getWidth() / 2;
            float y = position.getY() - sprite.getHeight() / 2;
            sprite.setPosition(x, y);
            sprite.draw(spriteBatch);

        }
    }

//    public void drawHealth(Entity entity) {
//        Health health = entity.getComponent(Health.class);
//        PositionPart position = entity.getComponent(PositionPart.class);
//        Texture texture = this.assetManager.get("assets/" + entity.getImage());
//
//        float totalBarWidth = 50;
//        float width = (float) health.getHealth() / (float) health.getMaxHealth() * totalBarWidth;
//        this.sr.begin(ShapeType.Filled);
//        this.sr.setColor(Color.GREEN);
//        this.sr.rect(position.getX() - 50 / 2, position.getY() + texture.getHeight() * 0.35f / 2 + 15, width, 5);
//        this.sr.end();
//
//    }
    
}
