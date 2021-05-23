package dk.sdu.enemysystem;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.enemy.Enemy;
import dk.sdu.common.services.IEntityProcessingService;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import dk.sdu.common.node.Node;
import dk.sdu.common.data.entityparts.CollisionPart;
import dk.sdu.commonplayer.Player;
import org.openide.util.Lookup;
import dk.sdu.common.services.IPathFinderService;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})

public class EnemyControlSystem implements IEntityProcessingService {

    private IPathFinderService pathfinder;
    private final Lookup lookup = Lookup.getDefault();

    @Override
    public void process(GameData gameData, World world) {
        
        pathfinder = lookup.lookup(IPathFinderService.class);
        
        
        for (Entity enemy : world.getEntities(Enemy.class)) {
            Enemy currentEnemy = (Enemy) enemy;
          
            
            if(getTargetExistence(currentEnemy, world)){
                PositionPart targetPos = currentEnemy.getTarget().getPart(PositionPart.class);
                Node goal = new Node(targetPos.getX(), targetPos.getY());
                if(pathfinder != null){
                    pathfinder.moveEnemy(gameData, enemy, goal, world);
                }
            } else {
                setTarget(currentEnemy, world);
            }
            handleTarget(currentEnemy, world, gameData);
            handleLife(currentEnemy, world);
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
    
    private void handleTarget(Enemy enemy, World world, GameData gameData){
       if(world.getEntities(Player.class).isEmpty()){
           return;
       } 
       Entity target = world.getEntities(Player.class).get(0);
       if(target == null){
           return;
       }
       PositionPart targetPos = target.getPart(PositionPart.class);
       PositionPart enemyPos = enemy.getPart(PositionPart.class);
      
       if(enemy.getTarget() != null){
           if(enemy.getTarget().getClass().equals(Player.class)){
                if(enemy.isTargeted()){
                    if((Math.pow(targetPos.getX() - enemyPos.getX(), 2) + Math.pow(targetPos.getY() - enemyPos.getY(), 2)) < Math.pow(enemy.getPlayerRadius(), 2)){
                        damageTarget(enemy, world, gameData); 
                    }
                }
           } else {
               targetPlayer(enemy, world);
           }
       } 
    }
    
    private void damageTarget (Enemy enemy, World world, GameData gameData){
        Entity target = enemy.getTarget();
        PositionPart enemyPos = enemy.getPart(PositionPart.class);
        PositionPart playerPos = target.getPart(PositionPart.class);
        LifePart lifePart = target.getPart(LifePart.class);
        
        CollisionPart enemyCollision = enemy.getPart(CollisionPart.class);
        CollisionPart playerCollision = target.getPart(CollisionPart.class);
        
        float x1 = enemyPos.getX() - enemyCollision.getWidth() / 2;
        float x2 = enemyPos.getX() + enemyCollision.getWidth() / 2;
        float x3 = playerPos.getX() - playerCollision.getWidth() / 2;
        float x4 = playerPos.getX() + playerCollision.getWidth() / 2;

        float y1 = enemyPos.getY() - enemyCollision.getHeight() / 2;
        float y2 = enemyPos.getY() + enemyCollision.getHeight() / 2;
        float y3 = playerPos.getY() - playerCollision.getHeight() / 2;
        float y4 = playerPos.getY() + playerCollision.getHeight() / 2;
        
        if ((x1 < x4) && (x3 < x2) && (y1 < y4) && (y3 < y2)) {
            if(lifePart.getLife() >= -1){
                lifePart.setIsHit(true);
                if (lifePart.isDead()){
                    world.removeEntity(target);
                    gameData.setEndGame(true);
                }
            }
        }
    }
    
    private void handleLife(Enemy enemy, World world) {
        LifePart lifePart = enemy.getPart(LifePart.class);
        if (lifePart == null) {
            return;
        }
        if (lifePart.isDead()) {
            world.removeEntity(enemy);
        }
    }
}

