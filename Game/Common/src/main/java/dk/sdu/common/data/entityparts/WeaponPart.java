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
public class WeaponPart implements EntityPart {
    
    private String entityId;
    private int damage;
    private boolean isAttacking;
    private int ammo;
    private float reloadTime, reloadTimer;
    private float shotCooldown, shotTimer;

    public WeaponPart(int ammo, float reloadTime, float shotCooldown, String entityId, int damage) {
        this.entityId = entityId;
        this.damage = damage;
        this.ammo = ammo;
        this.reloadTime = reloadTime;
        this.shotCooldown = shotCooldown;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isIsAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
     public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    

    public float getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    public float getReloadTimer() {
        return reloadTimer;
    }

    public void setReloadTimer(float reloadTimer) {
        this.reloadTimer = reloadTimer;
    }

    public float getShotCooldown() {
        return shotCooldown;
    }

    public void setShotCooldown(float shotCooldown) {
        this.shotCooldown = shotCooldown;
    }

    public float getShotTimer() {
        return shotTimer;
    }

    public void setShotTimer(float shotTimer) {
        this.shotTimer = shotTimer;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if (this.shotTimer > 0) {
            this.shotTimer -= gameData.getDelta();
        }
    }
}
