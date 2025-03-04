package nullengine;

import nullengine.event.EventBus;
import nullengine.game.Game;
import nullengine.mod.ModManager;
import nullengine.registry.RegistryManager;
import nullengine.util.CrashHandler;
import nullengine.util.RuntimeEnvironment;
import nullengine.util.Side;
import org.slf4j.Logger;

import java.nio.file.Path;

/**
 * really, just the {@link Game} starter, nothing else
 */
public interface Engine {

    Path getRunPath();

    Logger getLogger();

    RuntimeEnvironment getRuntimeEnvironment();

    Side getSide();

    default boolean isClient() {
        return getSide() == Side.CLIENT;
    }

    default boolean isServer() {
        return getSide() == Side.DEDICATED_SERVER;
    }

    EventBus getEventBus();

    RegistryManager getRegistryManager();

    ModManager getModManager();

    CrashHandler getCrashHandler();

    /**
     * Initialize the Engine. Load all mods and complete registration
     */
    void initEngine();

    void runEngine();

    Game getCurrentGame();

    void startGame(Game game);

    boolean isPlaying();

    void terminate();

    boolean isMarkedTermination();

    void addShutdownListener(Runnable runnable);

    // TODO: client should add player profile manager here, to perform login,
    // logout, fetch skin and other operation
}
