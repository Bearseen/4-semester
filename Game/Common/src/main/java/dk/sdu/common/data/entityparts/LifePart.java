package dk.sdu.common.data.entityparts;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;

/**
 *
 * @author S
 */
public class LifePart implements EntityPart {

    private boolean dead = false;
    private boolean isHit = false;
    private int life;
    private int maxLife;
    
    

    public LifePart(int maxLife) {
        this.maxLife = maxLife;
        this.life = this.maxLife;;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    
    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isDead() {
        if (this.life <= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (isHit) {
            life = - 1;
            isHit = false;
        }
        if (life <= 0) {
            dead = true;
        }

    }
}
