/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.node;

import java.util.ArrayList;

/**
 *
 * @author Mustafa
 */
public class Node {
    
    private float x;
    private float y;
    
    Node parent;
  
    
    public Node(float x, float y){
        this.x = x;
        this.y = y;
    }
   
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    public ArrayList<Node> getPath(){
        ArrayList<Node> path = new ArrayList<>();
        Node current_Node = this;
        path.add(current_Node);
        
        while(current_Node.getParent() != null){
            current_Node = current_Node.parent;
            path.add(current_Node);
        }
        if (path.get(path.size() - 1) != null) {
            path.remove(path.size() - 1);
        }
        return path;
    }
}
