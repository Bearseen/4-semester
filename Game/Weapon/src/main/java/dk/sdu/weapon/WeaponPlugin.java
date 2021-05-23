/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.weapon;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.BulletPart;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.TimerPart;
import dk.sdu.common.data.entityparts.WeaponPart;
import dk.sdu.common.services.IGamePluginService;
import dk.sdu.commonbullet.Bullet;
import dk.sdu.commonbullet.BulletSPI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
    @ServiceProvider(service = BulletSPI.class)
public class WeaponPlugin implements IGamePluginService, BulletSPI {

    @Override
    public void start(GameData gameData, World world) {

    }
    
    @Override
    public Entity createBullet(Entity entity, GameData gameData) {
        PositionPart entityPosition = entity.getPart(PositionPart.class);
        WeaponPart weaponPart = entity.getPart(WeaponPart.class);
        
        Bullet bullet = new Bullet(600f, "bullet.png");
        PositionPart positionPart = new PositionPart(entityPosition.getX(), entityPosition.getY(), entityPosition.getRadians());
        BulletPart bulletPart = new BulletPart(entity.getID(), weaponPart.getDamage(), 5);
        
        bullet.add(bulletPart);
        bullet.add(positionPart);
        
        return bullet;
        
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {  
        world.removeEntity(bullet);
        }
    }
}
