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

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})

public class EnemyControlSystem implements IEntityProcessingService {

    private Entity enemy;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            SimpleMovingPart simpleMovingPart = enemy.getPart(SimpleMovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            Random rand = new Random();

            simpleMovingPart.setUp(rand.nextBoolean());
            simpleMovingPart.setRight(rand.nextBoolean());
            simpleMovingPart.setLeft(rand.nextBoolean());
            simpleMovingPart.setDown(rand.nextBoolean());
            
            
            simpleMovingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians - 2 * 3.1415f / 4) * (entity.getRadius() - 2 * 3.1415f));
        shapey[0] = (float) (y + Math.sin(radians - 2 * 3.1415f / 4) * (entity.getRadius() - 2 * 3.1415f));

        shapex[1] = (float) (x + Math.cos(radians + 2 * 3.1415f / 4) * (entity.getRadius() - 2 * 3.1415f));
        shapey[1] = (float) (y + Math.sin(radians + 2 * 3.1415f / 4) * (entity.getRadius() - 2 * 3.1415f));

        shapex[2] = (float) (x + Math.cos(radians + 3 * 3.1415f / 4) * entity.getRadius());
        shapey[2] = (float) (y + Math.sin(radians + 3 * 3.1415f / 4) * entity.getRadius());

        shapex[3] = (float) (x + Math.cos(radians - 3 * 3.1415f / 4) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians - 3 * 3.1415f / 4) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
