package dk.sdu.player;

import dk.sdu.common.data.Entity;
import dk.sdu.common.data.GameData;
import dk.sdu.common.data.World;
import dk.sdu.commonplayer.Player;
import junit.framework.TestCase;

/**
 * @Author Yusaf
 */

public class PlayerPluginTest extends TestCase {
    private PlayerPlugin playerPlugin;
    private GameData gameData;
    private World world;

    public PlayerPluginTest() {
    }

    public void setUp() throws Exception {
        this.playerPlugin = new PlayerPlugin();
        this.gameData = new GameData();
        this.world = new World();
    }

    public void testStart() {
        boolean testResult = false;
        playerPlugin.start(gameData, world);

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player){
                testResult = true;
                break;
            }
        }
        System.out.println("TEST - Player Created: " + testResult);
    }

}