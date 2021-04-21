/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.map.map;

import org.openide.util.lookup.ServiceProvider;
import dk.sdu.common.assets.TileType;
import dk.sdu.common.assets.Tile;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProviders;


/**
 *
 * @author Samuel
 */

 
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})

public class MapPlugin implements IGamePluginService {
    
    private Tile[][] map;
    int[][] numberMap = 
    {
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2},
        {2, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 2},
        {2, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    };
    
    @Override
    public void start(GameData gameData, World world) {
        // loadFromFile(currentMapName, gameData, world);
        TileType[][] tileMap = convertIntArrayToTileArray(this.numberMap);
        createMap(tileMap, world);
    }
    
    @Override
    public void stop(GameData gameData, World world) {
        for (Entity tile : world.getEntities(Tile.class)) {
            world.removeEntity(tile);
        }
    }
    
    private TileType[][] convertIntArrayToTileArray(int[][] mapArray) {
        TileType[][] tileMap = new TileType[mapArray.length][mapArray[0].length];
        for (int x = 0; x < mapArray.length; x++) {
            for (int y = 0; y < mapArray[x].length; y++) {
                tileMap[x][y] = TileType.values()[mapArray[x][y]];
            }
        }
        return tileMap;
    }
    
    private void createMap(TileType[][] tileTypeMap, World world) {
        float tileHeight = 70;
        float tileWidth = 70;
        
        map = new Tile[tileTypeMap.length][tileTypeMap[0].length];
        
        for (int x = 0; x < tileTypeMap.length; x++) {
            for (int y = 0; y < tileTypeMap[x].length; y++) {
                Tile tile = new Tile(tileTypeMap[x][y].getImage());
                
                PositionPart position = new PositionPart(y * tileWidth + tileWidth / 2, x * tileHeight + tileHeight / 2, 0);
                tile.add(position);
                
//                Collission detectino
//                if (tileTypeMap[x][y].isHascollider()) {
//                    tile.addComponent(new BoxCollider(tileHeight - 10, tileWidth - 10));
//                }
                
                world.addEntity(tile);
                map[x][y] = tile;
            }
            
        }
    }
    
}
