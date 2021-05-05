/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.GameKeys;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.RangedWeaponPart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.commonbullet.BulletSPI;
import dk.sdu.commonplayer.Player;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


/**
 *
 * @author Samuel & Mustafa
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class PlayerProcessor implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            RangedWeaponPart rangedWeaponPart = player.getPart(RangedWeaponPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
            
            float diffX = GameKeys.mouse_X - positionPart.getX();
            float diffY = GameKeys.mouse_Y - positionPart.getY();
            
            positionPart.setRadians((float) Math.atan2(diffY, diffX));

            // shooting
            if (gameData.getKeys().isDown(GameKeys.MOUSE_LEFT) || gameData.getKeys().isDown(GameKeys.SPACE)) {               
                rangedWeaponPart.setIsAttacking(true);
                System.out.println("Left click");
            }

            // reload weapon
            if (gameData.getKeys().isDown(GameKeys.R)){
                rangedWeaponPart.setIsAttacking(false);
                rangedWeaponPart.setAmmo(5);
            }
            
            
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);
            rangedWeaponPart.process(gameData, player);
            
        }                
    }
    
}