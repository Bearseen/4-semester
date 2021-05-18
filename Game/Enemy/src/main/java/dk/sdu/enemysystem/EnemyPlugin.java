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
import dk.sdu.common.services.IWaveProcessingService;
import dk.sdu.common.spawn.Spawn;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),
    @ServiceProvider(service = IWaveProcessingService.class)})
public class EnemyPlugin implements IGamePluginService, IWaveProcessingService {

    private int totalEnemies = 0;
    
    private final Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        // Adding 4 enemies at start
        for (int i = 0; i < 3; i++) {
            world.addEntity(createSpawn());
           
//        world.addEntity(enemy);
        // Add entities to the world
//        while (totalEnemies > 0){
//            enemy = createEnemy(gameData);
//            world.addEntity(enemy);
//            totalEnemies--;
        }       
    }
    
    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    
    private Entity createSpawn() {
        ArrayList<Entity> spawns = new ArrayList<>();
        
        float x1 = getRandomNumber(100, 150);
        float y1 = getRandomNumber(100, 740);
        
        float x2 = getRandomNumber(650, 750);
        float y2 = getRandomNumber(100, 740);
        
        Random random = new Random();
        Boolean r = random.nextBoolean();
        
        Entity spawn1 = new Spawn();
        Entity spawn2 = new Spawn();
        spawn1.add(new PositionPart(x1, y1, 3.1415f / 2));
        spawn2.add(new PositionPart(x2, y2, 3.1415f / 2));
        
        spawns.add(spawn1);
        spawns.add(spawn2);
        
        Entity spawn;
        
        if (r){
            spawn = spawns.get(0);
        } else {
            spawn = spawns.get(1);
        }

        return spawn;
    }
    

    private Entity createEnemy(float x, float y){
        float maxSpeed = 50;
//        float x = new Random().nextFloat() * gameData.getDisplayWidth();
//        float y = new Random().nextFloat() * gameData.getDisplayHeight();
//        float x = 10;
//        float y = 840/2;
        float radians = 3.1415f / 2;
        boolean target = true;
        float playerRadius = 250;
        float acceleration = 50;

        Entity enemy = new Enemy(target,playerRadius,"enemy.png");
        enemy.setRadius(4);
        enemy.add(new SimpleMovingPart(maxSpeed, acceleration));
        enemy.add(new PositionPart(x, y));
        enemy.add(new LifePart(4));
        enemy.add(new CollisionPart(75,35));
        
        
        return enemy;
    }
    

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }

        for (Entity spawnPoint : world.getEntities(Spawn.class)) {
            world.removeEntity(spawnPoint);
        }
        
    }

    @Override
    public void startWave(GameData gameData, World world, int wave) {
        float multiplier = wave / 2;
        if (multiplier < 1) {
            multiplier = 1;
        }

        List<Entity> spawns = world.getEntities(Spawn.class);

        if (wave >= totalEnemies) {
            int x = 0;
            while (x <= 3 * multiplier) {
                Entity spawn = spawns.get(this.random.nextInt(spawns.size()));
                PositionPart positionPart = spawn.getPart(PositionPart.class);
                float spawnX = positionPart.getX();
                float spawnY = positionPart.getY();

                world.addEntity(createEnemy(spawnX, spawnY));

                x++;
            }
        }

    }

    @Override
    public boolean stopWave(GameData gameData, World world) {
        return world.getEntities(Enemy.class).isEmpty();
    }

}
