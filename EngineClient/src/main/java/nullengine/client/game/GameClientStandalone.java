package nullengine.client.game;

import nullengine.block.Block;
import nullengine.client.EngineClient;
import nullengine.client.gui.Scene;
import nullengine.client.input.controller.EntityCameraController;
import nullengine.client.input.controller.EntityController;
import nullengine.client.rendering.camera.FirstPersonCamera;
import nullengine.enginemod.client.gui.hud.HUDGame;
import nullengine.event.game.GameTerminationEvent;
import nullengine.game.GameServerFullAsync;
import nullengine.player.Player;
import nullengine.registry.Registries;
import nullengine.world.World;
import nullengine.world.WorldCommon;
import nullengine.world.WorldCommonProvider;
import nullengine.world.gen.ChunkGeneratorFlat;

import javax.annotation.Nonnull;

public class GameClientStandalone extends GameServerFullAsync implements GameClient {

    private final EngineClient engineClient;
    private final Player player;

    private EntityController entityController;

    public GameClientStandalone(EngineClient engine, Player player) {
        super(engine);
        this.engineClient = engine;
        this.player = player;
    }

    @Nonnull
    @Override
    public EngineClient getEngine() {
        return engineClient;
    }

    /**
     * Get player client
     */
    @Nonnull
    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the client world
     */
    @Nonnull
    @Override
    public World getWorld() {
        if(player != null && player.getControlledEntity() != null) {
            return player.getControlledEntity().getWorld();
        }
        return null;
    }

    @Override
    public EntityController getEntityController() {
        return entityController;
    }

    @Override
    public void setEntityController(EntityController controller) {
        this.entityController = controller;
    }

    @Override
    protected void constructStage() {
        super.constructStage();
    }

    @Override
    protected void resourceStage() {
        super.resourceStage();

        engineClient.getAssetManager().reload();
    }

    @Override
    protected void finishStage() {
        logger.info("Finishing Game Initialization!");

        // TODO: Remove it
//        WorldCommonProvider provider = new WorldCommonProvider();
//        var dirt = Registries.getBlockRegistry().getValue("foundation:dirt");
//        var grass = Registries.getBlockRegistry().getValue("foundation:grass");
////        provider.setChunkGenerator(new ChunkGeneratorFlat(new ChunkGeneratorFlat.Setting().setLayers(new Block[]{dirt, dirt, dirt, dirt, grass})));
//        spawnWorld(provider, "default");
//        var world = (WorldCommon) getWorld("default");
//        world.playerJoin(player);
//        player.getControlledEntity().getPosition().set(0, 5, 0);
//
//        engineClient.getRenderContext().setCamera(new FirstPersonCamera(player));
//
//        entityController = new EntityCameraController(player);
//        engineClient.getRenderContext().getWindow().addCursorCallback((window, xpos, ypos) -> {
//            entityController.handleCursorMove(xpos, ypos);
//        });

        super.finishStage();
        logger.info("Game Ready!");


//        engineClient.getRenderContext().getGuiManager().showHud("game-hud", new Scene(new HUDGame()));
//        a = Platform.getEngineClient().getSoundManager().createSoundSource("test sound").position(25,5,0).gain(1.0f).speed(dir);
//        a.setLoop(true);
//        a.assignSound(sound);
//        a.play();
    }

    public void clientTick() {
        if (isMarkedTermination()) {
            tryTerminate();
            return;
        }
        // TODO upload particle physics here
    }

    @Override
    protected void tryTerminate() {
        logger.info("Game terminating!");
        engine.getEventBus().post(new GameTerminationEvent.Pre(this));
        super.tryTerminate();
        engine.getEventBus().post(new GameTerminationEvent.Post(this));
        logger.info("Game terminated.");
    }
}