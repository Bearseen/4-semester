/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.ai;

import dk.sdu.common.ai.IPathFinder;
import dk.sdu.common.ai.Node;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.SimpleMovingPart;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Mustafa
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IPathFinder.class),})

public class Pathfinder implements IPathFinder {

    private Astar ai;
    
   
    
    public Pathfinder(){
        this.ai = new Astar();
    }
    
    @Override
    public void moveEnemy(GameData gameData, Entity entity, Node goal, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        SimpleMovingPart simpleMovingPart = entity.getPart(SimpleMovingPart.class);
        
//        if(dt > 200){
//            dt = 0;
//        }
        
        if (positionPart == null || simpleMovingPart == null) {
            
            return;
            
        }

        ArrayList<Node> path = ai.aStarPath(entity, world, goal);

        if (!path.isEmpty()) {
            System.out.println("path:" + path.size());
            float x = path.get(path.size() - 1).getX() - positionPart.getX();
            float y = path.get(path.size() - 1).getY() - positionPart.getY();
            positionPart.setRadians((float) Math.atan2(y,x));

            positionPart.setX(positionPart.getX() +  (float) cos(positionPart.getRadians())  + simpleMovingPart.getSpeed() * gameData.getDelta());
            positionPart.setY(positionPart.getY()  +  (float) sin(positionPart.getRadians()) + simpleMovingPart.getSpeed() * gameData.getDelta());
        }
    }



    }
    

