/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.map.map;

import dk.sdu.common.tile.Tiletype;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;

/**
 *
 * @author Samuel
 */
public interface MapAPI {
    
    void createMap(Tiletype[][] d, GameData gamedata, World world);
    
}
