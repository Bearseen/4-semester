/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.ai;

import dk.sdu.common.ai.Node;
import dk.sdu.common.data.Entity;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.CollisionPart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.SimpleMovingPart;
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
        float pCost = node.getPath().size() * cost;

        return pCost;
    }

    private boolean nodeChecker(Node node, World world, Entity entity) {
        for (Entity player : world.getEntities()) {
            if (player.equals(entity)) {
                continue;
            }

            if (entity.hasPart(MovingPart.class)) {
                continue;
            }
            if (player.hasPart(CollisionPart.class) && player.hasPart(PositionPart.class)) {
                PositionPart positionPart = player.getPart(PositionPart.class);
                CollisionPart collisionPart = player.getPart(CollisionPart.class);

                boolean targetHit = collisionPart.checkCollision(node.getX(), node.getY(), positionPart.getX(), positionPart.getY());
                if (targetHit) {
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
            if (evaluationFunc(node, goal) < evaluationFunc(lowestNode, goal)) {
                lowestNode = node;
            }
        }
        fringe.remove(lowestNode);
        return lowestNode;
    }

    private ArrayList<Node> expand(World world, Entity entity, Node node) {

        ArrayList<Node> neighbors = new ArrayList<>();
        int val = 10;
        float x = node.getX();
        float y = node.getY();
        float[][] sucessors = {{x - val, y + val}, {x, y + val}, {x + val, y + val},
        {x - val, y}, {x + val, y}, {x - val, y - val}, {x, y - val}, {x + val, y - val}};

        for (float[] element : sucessors) {
            Node neighbor = new Node(element[0], element[1]);
            if (nodeChecker(neighbor, world, entity) == false) {
                neighbor.setParent(node);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    public ArrayList<Node> aStarPath(Entity entity, World world, Node goal) {
        PositionPart position = entity.getPart(PositionPart.class);
        CollisionPart collision = entity.getPart(CollisionPart.class);

        ArrayList<Node> fringe = new ArrayList<>();
        Node node = new Node(position.getX(), position.getY());
        insert(node, fringe);
       

        while (!fringe.isEmpty()) {
           
            Node lowest = remove(goal, fringe);

            if (collision.checkCollision(goal.getX(), goal.getY(), lowest.getX(), lowest.getY())) {
                 System.out.println("2");
                return lowest.getPath();
            }
            ArrayList<Node> children = expand(world, entity, lowest);
            insertAll(children, fringe);
            System.out.println(fringe.size());
            
        }
        return new ArrayList<Node>();
    }

}
