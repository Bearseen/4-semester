package dk.sdu.Game;




import dk.sdu.common.services.IEntityProcessingService;
import dk.sdu.common.services.IGamePluginService;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;

public class ApplicationTest extends NbTestCase {
    
    private static final String ADD_ENEMY_UPDATES_FILE = "c:\\Users\\Thang og Thao\\IdeaProjects\\4-semester\\Game\\application\\src\\test\\resources\\enemy\\updates.xml";
    private static final String REMOVE_ENEMY_UPDATES_FILE = "c:\\Users\\Thang og Thao\\IdeaProjects\\4-semester\\Game\\application\\src\\test\\resources\\reenemy\\updates.xml";
    private static final String UPDATES_FILE = "c:\\Users\\Thang og Thao\\IdeaProjects\\4-semester\\netbeans_site\\updates.xml";
    
    public static Test suite() {
        return NbModuleSuite.createConfiguration(ApplicationTest.class).
                gui(false).
                failOnMessage(Level.WARNING). // works at least in RELEASE71
                failOnException(Level.INFO).
                enableClasspathModules(false). 
                clusters(".*").
                suite(); // RELEASE71+, else use NbModuleSuite.create(NbModuleSuite.createConfiguration(...))
    }

    public ApplicationTest(String n) {
        super(n);
    }

    public void testApplication() throws InterruptedException, IOException{
        // pass if there are merely no warnings/exceptions
        /* Example of using Jelly Tools (additional test dependencies required) with gui(true):
        new ActionNoBlock("Help|About", null).performMenu();
        new NbDialogOperator("About").closeByButton();
         */
        
        // SETUP
        System.out.println("Setting up test");
        List<IEntityProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
        waitForUpdate(processors, plugins);
        
        System.out.println("Pre Assert");
        // PRE ASSERT
        assertEquals("2 processors", 2, processors.size());
        assertEquals("3 plugins", 3, plugins.size());
        
        //TEST: unLoad Enemy via UC
        
        System.out.println("Load Enemy Module");
        copy(get(ADD_ENEMY_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);

        System.out.println("Testing if enemy is loaded");
        // ASSERT ENEMY LOADED
        assertEquals("3 processors", 3, processors.size());
        assertEquals("4 plugins", 4, plugins.size());
        
        System.out.println("Unload Enemy Module");
        copy(get(REMOVE_ENEMY_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        
        System.out.println("Testing if enemy is unloaded");
        // ASSERT ENEMY UNLOADED
        assertEquals("2 processors", 2, processors.size());
        assertEquals("3 plugins", 3, plugins.size());
    }
        
        private void waitForUpdate(List<IEntityProcessingService> processors, List<IGamePluginService> plugins) throws InterruptedException, IOException{
            Thread.sleep(10000);
            processors.clear();
            processors.addAll(Lookup.getDefault().lookupAll(IEntityProcessingService.class));
            
            plugins.clear();
            plugins.addAll(Lookup.getDefault().lookupAll(IGamePluginService.class));
        }
}

