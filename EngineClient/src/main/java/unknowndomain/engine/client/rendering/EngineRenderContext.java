package unknowndomain.engine.client.rendering;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import unknowndomain.engine.client.EngineClient;
import unknowndomain.engine.client.UnknownDomain;
import unknowndomain.engine.client.gui.EngineGuiManager;
import unknowndomain.engine.client.gui.GuiManager;
import unknowndomain.engine.client.rendering.camera.Camera;
import unknowndomain.engine.client.rendering.camera.SimpleCamera;
import unknowndomain.engine.client.rendering.display.GLFWWindow;
import unknowndomain.engine.client.rendering.display.Window;
import unknowndomain.engine.client.rendering.texture.EngineTextureManager;
import unknowndomain.engine.client.rendering.texture.TextureManager;
import unknowndomain.engine.util.Disposable;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EngineRenderContext implements RenderContext, Disposable {

    public static final int WINDOW_WIDTH = 854, WINDOW_HEIGHT = 480;

    private final EngineClient engine;
    private final Logger logger;

    private final List<Renderer> renderers = new LinkedList<>();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private Thread renderThread;
    private GLFWWindow window;
    private TextureManager textureManager;
    private GuiManager guiManager;

    private Camera camera;
    private final FrustumIntersection frustumIntersection = new FrustumIntersection();

    public EngineRenderContext(EngineClient engine) {
        this.engine = engine;
        this.logger = engine.getLogger();
    }

    @Override
    public EngineClient getEngine() {
        return engine;
    }

    @Override
    public Thread getRenderThread() {
        return renderThread;
    }

    @Override
    public boolean isRenderThread() {
        return Thread.currentThread() == renderThread;
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    public TextureManager getTextureManager() {
        return textureManager;
    }

    @Override
    public GuiManager getGuiManager() {
        return guiManager;
    }

    @Override
    public void runTaskNextFrame(Runnable runnable) {
        tasks.add(runnable);
    }

    @Override
    @Nonnull
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void setCamera(@Nonnull Camera camera) {
        this.camera = Objects.requireNonNull(camera);
    }

    @Override
    public FrustumIntersection getFrustumIntersection() {
        return frustumIntersection;
    }

    public void render(float partial) {
        List<Runnable> tasks = new LinkedList<>();
        this.tasks.drainTo(tasks);
        tasks.forEach(Runnable::run);

        camera.update(partial);
        frustumIntersection.set(window.projection().mul(camera.getViewMatrix(), new Matrix4f()));

        window.beginRender();
        for (Renderer renderer : renderers) {
            renderer.render(partial);
        }
        window.endRender();
    }

    public void init(Thread renderThread) {
        this.renderThread = renderThread;

        logger.info("Initializing window!");
        window = new GLFWWindow(WINDOW_WIDTH, WINDOW_HEIGHT, UnknownDomain.getName());
        window.init();
        engine.addShutdownListener(window::dispose);

        initGL();

        window.show();

        textureManager = new EngineTextureManager();
        guiManager = new EngineGuiManager(this);

        camera = new SimpleCamera(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1));

        initRenderer();
    }

    private void initGL() {
        logger.info("Initializing OpenGL context!");

        GL.createCapabilities();

        logger.info("----- OpenGL Information -----");
        logger.info("\tGL_VENDOR: {}", GL11.glGetString(GL11.GL_VENDOR));
        logger.info("\tGL_RENDERER: {}", GL11.glGetString(GL11.GL_RENDERER));
        logger.info("\tGL_VERSION: {}", GL11.glGetString(GL11.GL_VERSION));
        logger.info("\tGL_EXTENSIONS: {}", GL11.glGetString(GL11.GL_EXTENSIONS));
        logger.info("\tGL_SHADING_LANGUAGE_VERSION: {}", GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
        logger.info("------------------------------");

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void initRenderer() {
        for (Renderer renderer : renderers) {
            renderer.init(this);
        }
    }

    public List<Renderer> getRenderers() {
        return renderers;
    }

    @Override
    public void dispose() {
        renderers.forEach(Disposable::dispose);
    }
}
