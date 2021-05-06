package dk.sdu.enemysystem;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.SimpleMovingPart;
import dk.sdu.common.enemy.Enemy;
import dk.sdu.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.common.ai.IPathFinder;
import dk.sdu.common.ai.Node;
import dk.sdu.common.data.entityparts.CollisionPart;
import dk.sdu.commonplayer.Player;
import org.openide.util.Lookup;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})

public class EnemyControlSystem implements IEntityProcessingService {

    
    private IPathFinder pathfinder;
    private final Lookup lookup = Lookup.getDefault();
    
    
    

    @Override
    public void process(GameData gameData, World world) {
        
        this.pathfinder = lookup.lookup(IPathFinder.class);
        
        
        for (Entity enemy : world.getEntities(Enemy.class)) {
            Enemy currentEnemy = (Enemy) enemy;
            if(getTargetExistence(currentEnemy, world)){
                
                PositionPart targetPos = currentEnemy.getTarget().getPart(PositionPart.class);
                Node goal = new Node(targetPos.getX(), targetPos.getY());
                if(pathfinder != null){
                    
                  
                  this.pathfinder.moveEnemy(gameData, enemy, goal, world);
                   

                   
                }
            } else{
                setTarget(currentEnemy, world);
            }
            handleTarget(currentEnemy, world);
            
            

//         
        }
    }
    
    private void setTarget(Enemy enemy, World world){
        if(enemy.isTargeted()){
            targetPlayer(enemy, world);
            
        }
    }
    
    private void targetPlayer (Enemy enemy, World world){
        if(world.getEntities(Player.class).isEmpty()){
            
            return;
        }
        
        Entity target = world.getEntities(Player.class).get(0);
        if(target == null){
            return;
        }
        
        PositionPart targetPos = target.getPart(PositionPart.class);
        PositionPart enemyPos = enemy.getPart(PositionPart.class);
        
        if((Math.pow(targetPos.getX() - enemyPos.getX(), 2) + Math.pow(targetPos.getY() - enemyPos.getY(), 2)) < Math.pow(enemy.getPlayerRadius(), 2)){
           enemy.setTarget(target);
          
           
        }
    }
    private boolean getTargetExistence(Enemy enemy, World world){
        Entity target = enemy.getTarget();
        if (target == null) {

            return false;
        }
        Entity entity = world.getEntity(target.getID());
        if (entity == null) {
            return false;
        } else {
            
            
            return true;
            
        }
    }
    
    private void handleTarget(Enemy enemy, World world){
       if(world.getEntities(Player.class).isEmpty()){
           return;
       } 
       Entity target = world.getEntities(Player.class).get(0);
       if(target == null){
           return;
       }
       PositionPart targetPos = target.getPart(PositionPart.class);
       PositionPart enemyPos = enemy.getPart(PositionPart.class);
      
       if(enemy.getTarget() != null && enemy.getTarget().getClass().equals(Player.class) ){
           if(enemy.isTargeted()){
               if((Math.pow(targetPos.getX() - enemyPos.getX(), 2) + Math.pow(targetPos.getY() - enemyPos.getY(), 2)) < Math.pow(enemy.getPlayerRadius(), 2)){
                   
                   targetPlayer(enemy, world);
               }
           
    }    
       }
    }
}

