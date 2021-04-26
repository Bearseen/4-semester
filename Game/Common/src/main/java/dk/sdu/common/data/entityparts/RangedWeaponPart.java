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
public class RangedWeaponPart extends WeaponPart{
    private int ammo;
    private float reloadTime, reloadTimer;
    private float shotCooldown, shotTimer;

    public RangedWeaponPart(int magazineSize, float reloadTime, float shotCooldown, String entityId, int damage) {
        super(entityId, damage);
        this.ammo = ammo;
        this.reloadTime = reloadTime;
        this.shotCooldown = shotCooldown;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int mammo) {
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
