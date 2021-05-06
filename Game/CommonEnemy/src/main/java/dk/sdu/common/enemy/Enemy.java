package dk.sdu.common.enemy;

import dk.sdu.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Enemy extends Entity {
    
    private Entity target;
    private boolean isTargeted;
    private boolean isMoving;
    private float playerRadius;
    
    public Enemy() {
        super();
    }

    public Enemy(String image) {
        super(image);
    }
    public Enemy(boolean isTargeted, float playerRadius, String image){
       super(image);
       this.isTargeted = isTargeted;
       this.playerRadius = playerRadius;
       
    }
    
    public void setTarget(Entity target){
        this.target = target;
    }
    public Entity getTarget(){
        return target;
    }

    public boolean isTargeted() {
        return isTargeted;
    }

    public void setIsTargeted(boolean isTargeted) {
        this.isTargeted = isTargeted;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public float getPlayerRadius() {
        return playerRadius;
    }

    public void setPlayerRadius(float playerRadius) {
        this.playerRadius = playerRadius;
    }
    
    
    
}
