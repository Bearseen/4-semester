/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.data.entityparts;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.GameKeys;

/**
 * @author Samuel & Yusaf
 */
public class MovingPart implements EntityPart {

    private float dx, dy;
    private float deceleration, acceleration;
    private float speed;
    private boolean left, right, up, down;

    public MovingPart(float speed) {
        this.speed = speed;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setSpeed(float speed) {
        
        this.speed = speed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down){
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float dt = gameData.getDelta();

        // movement: UP, DOWN, LEFT, RIGHT
        if (up) {
            y += speed * dt;
        } else if (down) {
            y -= speed * dt;
        }

        if (left) {
            x -= speed * dt;
        } else if (right) {
            x += speed * dt;
        }

        // set position
        x += /*dx **/ dt;
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        } else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += /*dy **/ dt;
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        } else if (y < 0) {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);

        // aim & look direction
        float diffX = GameKeys.mouse_X - positionPart.getX();
        float diffY = GameKeys.mouse_Y - positionPart.getY();

        positionPart.setRadians((float) Math.atan2(diffY, diffX));

        /*
        // old movement logic

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();
        
        if (left) {
            radians += rotationSpeed * dt;
        } else if (right) {            
            radians -= rotationSpeed * dt;
        }

        // accelerating
        if (up) {
            x += Math.cos(radians) * maxSpeed * dt;
            y += Math.sin(radians) * maxSpeed * dt;
        } else if (down) {
            x -= Math.cos(radians) * maxSpeed * dt;
            y -= Math.sin(radians) * maxSpeed * dt;
        } else {
            dt = 0;
        }

        // set position
        x += dx * dt;
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        } else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += dy * dt;
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        } else if (y < 0) {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
        */
    }


}
