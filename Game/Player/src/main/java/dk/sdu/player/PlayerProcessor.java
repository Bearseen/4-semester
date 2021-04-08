/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.player;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.GameKeys;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.commonplayer.Player;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author musta
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class PlayerProcessor implements IEntityProcessingService  {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
            
          
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);

            updateShape(player);
    }
    
        
    }
    private void updateShape(Entity entity) {
            float[] shapex = new float[4];
            float[] shapey = new float[4];
            PositionPart positionPart = entity.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            float radians = positionPart.getRadians();
            

            shapex[0] = (float) (x + Math.cos(radians - 2 * 3.1415f / 4) * (entity.getRadius()-2*3.1415f));
            shapey[0] = (float) (y + Math.sin(radians - 2 * 3.1415f / 4) * (entity.getRadius()-2*3.1415f));

            shapex[1] = (float) (x + Math.cos(radians + 2 * 3.1415f / 4) * (entity.getRadius()-2*3.1415f));
            shapey[1] = (float) (y + Math.sin(radians + 2 * 3.1415f / 4) * (entity.getRadius()-2*3.1415f));

            shapex[2] =(float)  (x + Math.cos(radians + 3 * 3.1415f / 4) * entity.getRadius());
            shapey[2] =(float)  (y + Math.sin(radians + 3 * 3.1415f / 4) * entity.getRadius());

            shapex[3] =(float)  (x + Math.cos(radians - 3 * 3.1415f / 4) * entity.getRadius());
            shapey[3] =(float)  (y + Math.sin(radians - 3 * 3.1415f / 4) * entity.getRadius());

            entity.setShapeX(shapex);
            entity.setShapeY(shapey);
        }

}