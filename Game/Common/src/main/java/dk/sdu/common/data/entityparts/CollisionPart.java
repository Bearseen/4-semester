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
    
    public boolean checkCollision(float nodeX, float nodeY, float posX, float posY) {

        float x1 = posX - width / 2;
        float x2 = posX + width / 2;
        float y1 = posY - height / 2;
        float y2 = posY + height / 2;

        if (nodeX > x1 && nodeX < x2 && nodeY > y1 && nodeY < y2) {
            return true;
        } else {
            return false;
        }
    }
}
