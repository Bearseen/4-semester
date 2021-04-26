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
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author musta
 */

@ServiceProvider(service = IPostEntityProcessingService.class)
public class Collision implements IPostEntityProcessingService {

    
    
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
          for (Entity a : world.getEntities()) {
            for (Entity b : world.getEntities()) {
                if (collides(a, b)) {
                    System.out.println("test1");
                    collide(a, b);
                    System.out.println("test2");
                }
            }
        }
    }

    
    private boolean collides(Entity a, Entity b) {
        if (a.equals(b)) {
            return false;
        }
        PositionPart posA = a.getPart(PositionPart.class);
        PositionPart posB = b.getPart(PositionPart.class);
        CollisionPart boxA = a.getPart(CollisionPart.class);
        CollisionPart boxB = b.getPart(CollisionPart.class);

        if (posA == null || posB == null || boxA == null || boxB == null) {
            return false;
        }

        float w = (boxA.getWidth() + boxB.getWidth()) / 2;
        float h = (boxA.getHeight() + boxB.getHeight()) / 2;
        float dx = posA.getX() - posB.getX();
        float dy = posA.getY() - posB.getY();

        return Math.abs(dx) <= w && Math.abs(dy) <= h;
    }

    private void collide(Entity entityA, Entity entityB) {
        PositionPart positionA = entityA.getPart(PositionPart.class);
        PositionPart positionB = entityB.getPart(PositionPart.class);
        CollisionPart colliderA = entityA.getPart(CollisionPart.class);
        CollisionPart colliderB = entityB.getPart(CollisionPart.class);

        float xDifference = positionA.getX() - positionB.getX();
        float yDifference = positionA.getY() - positionB.getY();

        float overlapX = colliderA.getWidth() / 2 + colliderB.getWidth() / 2 - Math.abs(xDifference);
        float overlapY = colliderA.getHeight() / 2 + colliderB.getHeight() / 2 - Math.abs(yDifference);

        if (entityA.hasPart(MovingPart.class)) {
            if (overlapX >= overlapY) {
                if (yDifference > 0) {
                    positionA.setY(positionA.getY() + overlapY);
                } else {
                    positionA.setY(positionA.getY() - overlapY);

                }
            } else {
                if (xDifference > 0) {
                    positionA.setX(positionA.getX() + overlapX);
                } else {
                    positionA.setX(positionA.getX() - overlapX);
                }
            }

        }

    }

}
