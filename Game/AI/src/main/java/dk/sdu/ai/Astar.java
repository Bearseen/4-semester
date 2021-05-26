/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.ai;

import dk.sdu.common.node.Node;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.CollisionPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.ArtificialMovingPart;
import java.util.ArrayList;

/**
 *
 * @author Mustafa
 */
public class Astar {

    public int cost = 1;

    private float heuristic(Node node, Node goal) {
        float nodeX = node.getX();
        float nodeY = node.getY();
        float goalX = goal.getX();
        float goalY = goal.getY();

        float distance = (float) Math.sqrt(Math.pow((double)(goalX - nodeX), 2) + Math.pow((double)(goalY - nodeY), 2));

        return distance;
    }

    private void insertAll(ArrayList<Node> children, ArrayList<Node> fringe) {
        for (Node node : children) {
            fringe.add(node);
        }
    }

    private void insert(Node node, ArrayList<Node> fringe) {
        fringe.add(node);
    }

    private float cost(Node node) {
        
        
        return node.getPath().size() * this.cost;

        
    }

    private boolean nodeChecker(Node node, World world, Entity entity) {
          for (Entity entity2 : world.getEntities()) {
            if (entity2.equals(entity)) {
                continue;
            }

            if (entity.hasPart(ArtificialMovingPart.class)) {
                continue;
            }
            if (entity2.hasPart(CollisionPart.class) && entity2.hasPart(PositionPart.class)) {
                PositionPart position = entity2.getPart(PositionPart.class);
                CollisionPart collider = entity2.getPart(CollisionPart.class);

                boolean hit = collider.nodeCollision(node.getX(), node.getY(), position.getX(), position.getY());
                if (hit) {
                    return true;
                }
            }
        }
        return false;
    }

    private float evaluationFunc(Node node, Node goal) {
        return cost(node) + heuristic(node, goal);
    }

    private Node remove(Node goal, ArrayList<Node> fringe) {
        Node lowestNode = fringe.get(0);
        for (Node node : fringe) {
            if (evaluationFunc(lowestNode, goal) > evaluationFunc(node, goal)) {
                lowestNode = node;
            }
        }
        fringe.remove(lowestNode);
        return lowestNode;
    }

    private ArrayList<Node> expand(World world, Entity entity, Node node) {

        ArrayList<Node> sucessors = new ArrayList<>();
        int val = 10;
        float x = node.getX();
        float y = node.getY();
        float[][] neighbours = {{x - val, y + val}, {x, y + val}, {x + val, y + val},
        {x - val, y}, {x + val, y}, {x - val, y - val}, {x, y - val}, {x + val, y - val}};

        for (float[] element : neighbours) {
            Node neighbor = new Node(element[0], element[1]);
            if (nodeChecker(neighbor, world, entity) == false) {
                neighbor.setParent(node);
                sucessors.add(neighbor);
            }
        }
        return sucessors;
    }

    public ArrayList<Node> aStarPath(Entity entity, World world, Node goal) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        CollisionPart collisionPart = entity.getPart(CollisionPart.class);

        ArrayList<Node> fringe = new ArrayList<>();
        Node node = new Node(positionPart.getX(), positionPart.getY());
        insert(node, fringe);
       

        while (!fringe.isEmpty()) {
            
            Node lowest = remove(goal, fringe);
            

            if (collisionPart.nodeCollision(goal.getX(), goal.getY(), lowest.getX(), lowest.getY())) {

                 
                return lowest.getPath();
            }
            ArrayList<Node> children = expand(world, entity, lowest);
            insertAll(children, fringe);
            
            
        }
        return new ArrayList<Node>();
    }

}
