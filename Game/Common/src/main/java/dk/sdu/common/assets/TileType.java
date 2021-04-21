/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.assets;

/**
 *
 * @author Samuel
 */
public enum TileType {
    GRASS(0, "grass.png", false),
    TREE(1, "tree.png", true),
    WALL(2, "wall.png", true);


    private int numberValue;
    private String image;
    private boolean hasCollider;

    public void setHasCollider(boolean hasCollider) {
        this.hasCollider = hasCollider;
    }

    public boolean isHasCollider() {
        return hasCollider;
    }

    private TileType(int numberValue, String image, Boolean hasCollider) {
        this.numberValue = numberValue;
        this.image = image;
        this.hasCollider = hasCollider;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static TileType getGRASS() {
        return GRASS;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public String getImage() {
        return image;
    }

    
}
