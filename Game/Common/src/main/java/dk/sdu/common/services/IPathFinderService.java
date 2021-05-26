/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.services;

import dk.sdu.common.node.Node;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;

/**
 *
 * @author Mustafa
 */
public interface IPathFinderService {
    public void moveEnemy(GameData gameData, Entity entity, Node goal, World world );
}
