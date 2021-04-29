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
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.RangedWeaponPart;
import dk.sdu.common.data.entityparts.TimerPart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.commonbullet.Bullet;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
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
        
        for (Entity entity : world.getEntities()) {
            if (entity.hasPart(RangedWeaponPart.class)) {
                System.out.println("Found rangedWeaponPart" + entity.toString());
                updateRangedWeapon(gameData, world, entity);
            }
        }
        
        for (Entity entity : world.getEntities(Bullet.class)) {
            Bullet bullet = (Bullet) entity;
            updateBullet(gameData, world, bullet);  
        }     
    }
        
//        for (Entity bullet : world.getEntities(Bullet.class)) {
//
//            PositionPart positionPart = bullet.getPart(PositionPart.class);
//            MovingPart movingPart = bullet.getPart(MovingPart.class);
//            TimerPart timerPart = bullet.getPart(TimerPart.class);
//            
//            movingPart.setUp(true);
//            
//            if (timerPart.getExpiration() < 0) {
//                world.removeEntity(bullet);
//            }
//            timerPart.process(gameData, bullet);
//            movingPart.process(gameData, bullet);
//            positionPart.process(gameData, bullet);
//
//        }
//    }
    private void updateRangedWeapon(GameData gameData, World world, Entity entity) {
        RangedWeaponPart rangedWeaponPart = entity.getPart(RangedWeaponPart.class);
        if (rangedWeaponPart.isIsAttacking() != false) {
            System.out.println(rangedWeaponPart.getShotTimer());
            if (rangedWeaponPart.getShotTimer() <= 0) {
                rangedWeaponPart.setShotTimer(rangedWeaponPart.getShotCooldown());
                WeaponPlugin w = new WeaponPlugin();
                world.addEntity(w.createBullet(entity, gameData));
                System.out.println("Bullet added");
            }
            rangedWeaponPart.process(gameData, entity);
            rangedWeaponPart.setIsAttacking(false);
        }
    }
    
    private void updateBullet(GameData gameData, World world, Bullet bullet) {
        BulletPart bulletPart = bullet.getPart(BulletPart.class);
        PositionPart positionPart = bullet.getPart(PositionPart.class);
        
        if (bulletPart == null && positionPart == null) {
            return;
        }
        
        if (bulletPart.getRemove()) {
            world.removeEntity(bullet);
            return;
        }
        
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();
        float speed = bullet.getSpeed();
        
        positionPart.setX(x + (float) cos(radians) * speed * dt);
        positionPart.setY(y + (float) sin(radians) * speed * dt);
        
        bulletPart.process(gameData, bullet);
        
//        projectileHit(bullet, world);
        
    }
    
}
