/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.services;

import dk.sdu.common.data.GameData;


/**
 *
 * @author Villy
 */
public interface IMapService {
    void create(GameData gameData);

    void render();
    
}
