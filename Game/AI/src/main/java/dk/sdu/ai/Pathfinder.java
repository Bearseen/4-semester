/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.ai;

import dk.sdu.common.node.Node;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.ArtificialMovingPart;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import dk.sdu.common.services.IPathFinderService;

/**
 *
 * @author Mustafa
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IPathFinderService.class),})

public class Pathfinder implements IPathFinderService {

    private Astar ai;
    
   
    
    public Pathfinder(){
        this.ai = new Astar();
    }
    
    @Override
    public void moveEnemy(GameData gameData, Entity entity, Node goal, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        ArtificialMovingPart artificialMovingPart = entity.getPart(ArtificialMovingPart.class);
        
//        if(dt > 200){
//            dt = 0;
//        }
        
        if (positionPart == null || artificialMovingPart == null) {
            
            return;
            
        }

        ArrayList<Node> path = ai.aStarPath(entity, world, goal);

        if (!path.isEmpty()) {
            
            float x = path.get(path.size() - 1).getX() - positionPart.getX();
            float y = path.get(path.size() - 1).getY() - positionPart.getY();
            positionPart.setRadians((float) Math.atan2(y,x));

            positionPart.setX(positionPart.getX() +  (float) cos(positionPart.getRadians())  * artificialMovingPart.getSpeed() * gameData.getDelta());
            positionPart.setY(positionPart.getY() +  (float) sin(positionPart.getRadians()) * artificialMovingPart.getSpeed() * gameData.getDelta());
        }
    }



    }
    

