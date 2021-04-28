/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.weapon;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.RangedWeaponPart;
import dk.sdu.common.data.entityparts.TimerPart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.commonbullet.Bullet;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Samuel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class WeaponProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            
            movingPart.setUp(true);
            
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);

        }
    }
    
}
