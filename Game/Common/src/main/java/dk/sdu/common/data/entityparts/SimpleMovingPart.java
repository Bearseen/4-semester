/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.common.data.entityparts;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import static java.lang.Math.sqrt;

/**
 *
 * @author musta
 */
public class SimpleMovingPart implements EntityPart {
    
    private float dx, dy, acceleration, speed;
    private boolean left, right, up, down;
  

    public SimpleMovingPart(float acceleration, float speed) {
        this.acceleration = acceleration;
        this.speed = speed;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getSpeed() {
        return speed;
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

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    @Override
    public void process( GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float dt = gameData.getDelta();

        if (up) {
            dy += acceleration * dt;
        } else if (down) {
            dy -= acceleration * dt;
        }
        if (left) {
            dx -= acceleration * dt;
        } else if (right) {
            dx += acceleration * dt;
        }

        float vec = (float) sqrt(dx * dx + dy * dy);
        if (down == false && up == false && vec > 0) {
            dy -= (dy / vec) * 600 * dt;
        }

        if (left == false && right == false && vec > 0) {
            dx -= (dx / vec) * 600 * dt;
        }

        if (vec > speed) {
            dx = (dx / vec) * speed;
            dy = (dy / vec) * speed;
        }
        
        x += dx * dt;
        y += dy * dt;
        
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
        
//        x += dx * dt;
//        y += dy * dt;
//        positionPart.setX(x);
//        positionPart.setY(y);
    }
    
}
