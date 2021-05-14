/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.commonbullet;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;

/**
 *
 * @author Samuel
 */
public interface BulletSPI {
    Entity createBullet(Entity entity, GameData gameData);
}
