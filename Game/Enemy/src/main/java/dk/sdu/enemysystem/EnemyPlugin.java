package dk.sdu.enemysystem;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.data.entityparts.CollisionPart;
import dk.sdu.common.data.entityparts.LifePart;
import dk.sdu.common.data.entityparts.MovingPart;
import dk.sdu.common.data.entityparts.PositionPart;
import dk.sdu.common.data.entityparts.SimpleMovingPart;
import dk.sdu.common.enemy.Enemy;
import dk.sdu.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    private int totalEnemies = 5;
    
    @Override
    public void start(GameData gameData, World world) {
           
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
        // Add entities to the world
//        while (totalEnemies > 0){
//            enemy = createEnemy(gameData);
//            world.addEntity(enemy);
//            totalEnemies--;
//        }
       
    }

    private Entity createEnemy(GameData gameData){
        float maxSpeed = 50;
       // float x = new Random().nextFloat() * gameData.getDisplayWidth();
        // float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float x = 10;
        float y = 840/2;
        float radians = 3.1415f / 2;
        boolean target = true;
        float playerRadius = 300;

        Entity enemy = new Enemy(target,playerRadius,"enemy.png");
        enemy.setRadius(8);
        enemy.add(new SimpleMovingPart(maxSpeed));
        enemy.add(new PositionPart(x, y));
        enemy.add(new LifePart(4));
        enemy.add(new CollisionPart(75,35));
        
        
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }

}
