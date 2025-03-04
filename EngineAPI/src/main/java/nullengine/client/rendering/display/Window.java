package nullengine.client.rendering.display;

import org.joml.Matrix4fc;

public interface Window {

    int getWidth();

    int getHeight();

    float getContentScaleX();

    float getContentScaleY();

    void setSize(int width, int height);

    Matrix4fc projection();

    boolean isResized();

    String getTitle();

    void setTitle(String title);

    Cursor getCursor();

    void close();

    boolean isClosed();

    void show();

    void hide();

    void setVisible(boolean visable);

    boolean isVisible();

    void dispose();

    void addKeyCallback(KeyCallback callback);

    void removeKeyCallback(KeyCallback callback);

    void addMouseCallback(MouseCallback callback);

    void removeMouseCallback(MouseCallback callback);

    void addCursorCallback(CursorCallback callback);

    void removeCursorCallback(CursorCallback callback);

    void addScrollCallback(ScrollCallback callback);

    void removeScrollCallback(ScrollCallback callback);

    void addCharCallback(CharCallback callback);

    void removeCharCallback(CharCallback callback);

    void addWindowCloseCallback(WindowCloseCallback callback);

    void removeWindowCloseCallback(WindowCloseCallback callback);

    void addCursorEnterCallback(CursorEnterCallback callback);

    void removeCursorEnterCallback(CursorEnterCallback callback);

    void beginRender();

    void endRender();

    default void toggleFullscreen(){
        setFullscreen(!isFullscreen());
    }

    boolean isFullscreen();

    void setFullscreen(boolean fullscreen);

    @FunctionalInterface
    interface KeyCallback {
        void invoke(Window window, int key, int scancode, int action, int mods);
    }

    @FunctionalInterface
    interface MouseCallback {
        void invoke(Window window, int button, int action, int mods);
    }

    @FunctionalInterface
    interface CursorCallback {
        void invoke(Window window, double xpos, double ypos);
    }

    @FunctionalInterface
    interface ScrollCallback {
        void invoke(Window window, double xoffset, double yoffset);
    }

    @FunctionalInterface
    interface CharCallback {
        void invoke(Window window, char c);
    }

    @FunctionalInterface
    interface WindowCloseCallback {
        void invoke(Window window);
    }

    @FunctionalInterface
    interface CursorEnterCallback {
        void invoke(Window window, boolean entered);
    }
}
