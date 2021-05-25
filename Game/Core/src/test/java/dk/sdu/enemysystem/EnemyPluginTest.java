package dk.sdu.enemysystem;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.common.enemy.Enemy;
import junit.framework.TestCase;

/**
 * @Author Yusaf
 */

public class EnemyPluginTest extends TestCase {
    private EnemyPlugin enemyPlugin;
    private GameData gameData;
    private World world;

    public EnemyPluginTest() {
    }

    public void setUp() throws Exception {
        this.enemyPlugin = new EnemyPlugin();
        this.gameData = new GameData();
        this.world = new World();
    }

    public void testStart() {
        boolean testResult = false;
        enemyPlugin.start(gameData, world);
        enemyPlugin.startWave(gameData, world, 1);

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy) {
                testResult = true;
                break;
            }
        }
        assertTrue(testResult);
        System.out.println("TEST - Enemy created: " + testResult);
    }

}
