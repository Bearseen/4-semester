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
import dk.sdu.common.data.entityparts.CollisionPart;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.RangedWeaponPart;
import dk.sdu.common.data.entityparts.TimerPart;
import dk.sdu.common.data.entityparts.WeaponPart;
import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.commonbullet.Bullet;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
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
        
        if (rangedWeaponPart.isIsAttacking() && !emptyMagazine(rangedWeaponPart)) {
            
            System.out.println(rangedWeaponPart.getShotTimer());
            if (rangedWeaponPart.getShotTimer() <= 0) {
                rangedWeaponPart.setShotTimer(rangedWeaponPart.getShotCooldown());
                WeaponPlugin w = new WeaponPlugin();
                world.addEntity(w.createBullet(entity, gameData));
                decreaseAmmo(rangedWeaponPart);
            }
            
            System.out.println(rangedWeaponPart.getAmmo());
            rangedWeaponPart.process(gameData, entity);

            // draw ammo counter

        }
        rangedWeaponPart.setIsAttacking(false);
    }
    
    private void updateBullet(GameData gameData, World world, Bullet bullet) {
        BulletPart bulletPart = bullet.getPart(BulletPart.class);
        PositionPart positionPart = bullet.getPart(PositionPart.class);
        
        if (bulletPart == null && positionPart == null) {
            return;
        }

        assert bulletPart != null;
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
        
        weaponHit(bullet, world);
        
    }
    
    private void weaponHit(Bullet bullet, World world) {
        
        for (Entity entity : world.getEntities()) {
            BulletPart bulletPart = bullet.getPart(BulletPart.class);
            if (entity.getID().equals(bulletPart.getSourceId())) {
                continue;
            }
            
            if (entity.hasPart(CollisionPart.class) && entity.hasPart(PositionPart.class)) {
                PositionPart positionPart = entity.getPart(PositionPart.class);
                CollisionPart collider = entity.getPart(CollisionPart.class);
                PositionPart projectilePosition = bullet.getPart(PositionPart.class);
                
                float x = projectilePosition.getX();
                float y = projectilePosition.getY();
                
                float x1 = positionPart.getX() - collider.getWidth() / 2;
                float x2 = positionPart.getX() + collider.getWidth() / 2;
                float y1 = positionPart.getY() - collider.getHeight() / 2;
                float y2 = positionPart.getY() + collider.getHeight() / 2;
                
                if (x > x1 && x < x2 && y > y1 && y < y2) {
                    world.removeEntity(bullet);
                    if (entity.hasPart(LifePart.class)) {
                        LifePart lifePart = entity.getPart(LifePart.class);
                        lifePart.damage(bulletPart.getDamage());
                    }
                }
            }
        }
    }
    
    private void decreaseAmmo(RangedWeaponPart part){
        part.setAmmo(part.getAmmo()-1);
    }
    
    private boolean emptyMagazine(RangedWeaponPart part){
        boolean empty = false;
        if(part.getAmmo() <= 0){
            empty = true;
        }
        
        return empty;
    }
    
}
