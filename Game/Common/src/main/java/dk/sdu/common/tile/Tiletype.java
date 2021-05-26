/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.tile;

/**
 *
 * @author Samuel
 */
public enum  Tiletype {
    GRASS(0, "grass.png", false),
    TREE(1, "tree.png", true),
    WALL(2, "wall.png", true);


    private int numbervalue;
    private String image;
    private boolean hascollider; 

    public void setHascollider(boolean hascollider) {
        this.hascollider = hascollider;
    }

    public boolean isHascollider() {
        return hascollider;
    }

    private Tiletype(int numbervalue, String image, Boolean hascollider) {
        this.numbervalue = numbervalue;
        this.image = image;
        this.hascollider = hascollider;
    }

    public void setNumbervalue(int numbervalue) {
        this.numbervalue = numbervalue;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Tiletype getGRASS() {
        return GRASS;
    }

    public int getNumbervalue() {
        return numbervalue;
    }

    public String getImage() {
        return image;
    }

    
}
