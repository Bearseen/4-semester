package org.netbeans.modules.autoupdate.silentupdate;

/**
 *
 */
import java.io.IOException;
import java.net.URISyntaxException;
import static java.nio.file.Files.copy;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.netbeans.api.autoupdate.UpdateUnitProvider;
import org.netbeans.api.autoupdate.UpdateUnitProviderFactory;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class UpdateActivator extends ModuleInstall {

    private final ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);

    @Override
    public void restored() {
        try {
            setupUpdateCenter();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        exector.scheduleAtFixedRate(doCheck, 5000, 5000, TimeUnit.MILLISECONDS);
    }
        

    private static final Runnable doCheck = new Runnable() {
        @Override
        public void run() {
            if (UpdateHandler.timeToCheck()) {
                UpdateHandler.checkAndHandleUpdates();
            }
        }

    };

    @Override
    public void uninstalled() {
        super.uninstalled(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void setupUpdateCenter() throws URISyntaxException {
        String SILENT_UC_CODE_NAME = "org_netbeans_modules_autoupdate_silentupdate_update_center";
        String oldFile = "";
        String newFile = "";
        List<UpdateUnitProvider> providers = UpdateUnitProviderFactory.getDefault().getUpdateUnitProviders(true);
        for (UpdateUnitProvider p : providers) {
            if (SILENT_UC_CODE_NAME.equals(p.getName())) {
                oldFile = Paths.get(p.getProviderURL().toURI()).toFile().getAbsolutePath();
                newFile = oldFile.replace("updates.xml", "newUpdates.xml");
            }
        }

        Path newFilePath = Paths.get(newFile);
        System.out.println(oldFile);
        System.out.println(newFilePath.toString());
        Path oldFilePath = Paths.get(oldFile);
        try {
            copy(newFilePath, oldFilePath, REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
