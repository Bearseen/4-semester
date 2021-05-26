/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.CollisionPart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.SimpleMovingPart;
import dk.sdu.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author musta
 */

@ServiceProvider(service = IPostEntityProcessingService.class)
public class Collision implements IPostEntityProcessingService {
    
    private PositionPart posA;
    private PositionPart posB;
    private CollisionPart colliderPartA;
    private CollisionPart colliderPartB;
    private float dx;
    private float dy;
    
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
          for (Entity a : world.getEntities()) {
            for (Entity b : world.getEntities()) {
                if (collides(a, b)) {
                    
                    collide(a, b);
                    
                }
            }
        }
    }
    
    // entityDiff gets the position & position parts, and dx & dy of pos A & B.
    private void entityDiff(Entity a, Entity b){
        posA = a.getPart(PositionPart.class);
        posB = b.getPart(PositionPart.class);
        colliderPartA = a.getPart(CollisionPart.class);
        colliderPartB = b.getPart(CollisionPart.class);
        dx = posA.getX() - posB.getX();
        dy = posA.getY() - posB.getY();
    }
    
    private boolean collides(Entity a, Entity b) {
        entityDiff(a, b);
        if (a.equals(b) || 
            posA == null || 
            posB == null || 
            colliderPartA == null || 
            colliderPartB == null) {
            return false;
        }
        
        float width = (colliderPartA.getWidth() + colliderPartB.getWidth()) / 2;
        float height = (colliderPartA.getHeight() + colliderPartB.getHeight()) / 2;

        return Math.abs(dx) <= width && Math.abs(dy) <= height;
    }

    private void collide(Entity a, Entity b) {
        entityDiff(a, b);

        float overlapX = colliderPartA.getWidth() / 2 + colliderPartB.getWidth() / 2 - Math.abs(dx);
        float overlapY = colliderPartA.getHeight() / 2 + colliderPartB.getHeight() / 2 - Math.abs(dy);

        if (a.hasPart(MovingPart.class) || a.hasPart(SimpleMovingPart.class)) {
            if (overlapX >= overlapY) {
                if (dy > 0) {
                    posA.setY(posA.getY() + overlapY);
                } else {
                    posA.setY(posA.getY() - overlapY);

                }
            } else {
                if (dx > 0) {
                    posA.setX(posA.getX() + overlapX);
                } else {
                    posA.setX(posA.getX() - overlapX);
                }
            }

        }

    }

}
