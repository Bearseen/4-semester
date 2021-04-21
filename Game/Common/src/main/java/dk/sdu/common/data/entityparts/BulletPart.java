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
 * @author Samuel
 */
public class BulletPart extends Entity{
    
    private String sourceId;
    private int damage;
    private float lifetime, lifeTimer;
    private boolean remove;

    public BulletPart(String sourceId, int damage, float lifetime) {
        this.sourceId = sourceId;
        this.damage = damage;
        this.lifetime = lifetime;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getLifetime() {
        return lifetime;
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }

    public float getLifeTimer() {
        return lifeTimer;
    }

    public void setLifeTimer(float lifeTimer) {
        this.lifeTimer = lifeTimer;
    }

    public boolean getRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
    
    public void update(Entity entity, GameData gameData) {
        lifeTimer += gameData.getDelta();
        if (lifetime <= lifeTimer) {
            remove = true;
        }
    }
    
}
