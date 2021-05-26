/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.gameStates;

import org.w3c.dom.Node;

/**
 *
 * @author Samuel
 */
public class Component {
    private String name;
    private boolean active;
    private Node node;
    private Node parent;

    public Component(String name, boolean active, Node node, Node parent) {
        this.name = name;
        this.active = active;
        this.node = node;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
