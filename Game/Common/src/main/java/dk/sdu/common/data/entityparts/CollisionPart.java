/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.data.entityparts;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;

/**
 *
 * @author musta
 */
public class CollisionPart implements EntityPart {
    
     private float height, width;

    public CollisionPart(float height, float width) {
        this.height = height;
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
    
    // if collides is used to check collision state in astar
    public boolean nodeCollision(float nodeX, float nodeY, float posX, float posY) {
        
        float x1 = posX - width / 2, 
              x2 = posX + width / 2, 
              y1 = posY - height / 2, 
              y2 = posY + height / 2;

        // true or false state if entities collide
        if (nodeX > x1 && nodeX < x2 && nodeY > y1 && nodeY < y2) {
            return true;
        }
         return false;
    }
}
