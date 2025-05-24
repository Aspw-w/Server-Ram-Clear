package aspw.serverramcleaner.serverRamCleaner;

import aspw.serverramcleaner.serverRamCleaner.client.UpdatesChecker;
import net.fabricmc.api.ModInitializer;

public class MixinLoader implements ModInitializer {

    @Override
    public void onInitialize() {
        UpdatesChecker.updateCheck();
    }
}
