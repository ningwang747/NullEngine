package nullengine.client.rendering.shader;

import com.github.mouse0w0.observable.value.MutableValue;
import com.github.mouse0w0.observable.value.ObservableValue;
import com.github.mouse0w0.observable.value.SimpleMutableObjectValue;
import nullengine.Platform;
import org.joml.*;
import org.lwjgl.opengl.GL30;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ShaderManager {

    @Deprecated
    public static final ShaderManager INSTANCE = new ShaderManager();

    private final Map<String, MutableValue<ShaderProgram>> loadedShaders;
    private final Map<String, ShaderProgramBuilder> registeredShaders;

    private ShaderProgram lastShader;

    private ShaderProgram usingShader;

    private boolean overriding;

    private static final List<Class<?>> SUPPORTED_UNIFORM_TYPE;

    static {
        SUPPORTED_UNIFORM_TYPE = new ArrayList<>();
        Stream.of(ShaderManager.class.getDeclaredMethods()).filter(method -> method.getName().equals("setUniform")).forEach(method -> {
            int parCount = method.getParameterCount();
            if (parCount == 2) {
                var partypes = method.getParameterTypes();
                if (partypes.length == parCount && partypes[1] != Object.class) {
                    SUPPORTED_UNIFORM_TYPE.add(partypes[1]);
                }
            }
        });
        int id = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(id);
    }

    private ShaderManager() {
        loadedShaders = new HashMap<>();
        registeredShaders = new HashMap<>();
        uniforms = new HashMap<>();
        overriding = false;


    }

    private Map<String, Object> uniforms;

    public ObservableValue<ShaderProgram> registerShader(String name, ShaderProgramBuilder builder) {
        if (registeredShaders.containsKey(name)) {
            throw new IllegalStateException(String.format("Shader %s has already registered!", name));
        }
        registeredShaders.put(name, builder);
        MutableValue<ShaderProgram> value = new SimpleMutableObjectValue<>();
        loadedShaders.put(name, value);
        return value.toImmutable();
    }

    public void unregisterShader(String name) {
        if (registeredShaders.containsKey(name)) {
            registeredShaders.remove(name);
            MutableValue<ShaderProgram> shader = loadedShaders.get(name);
            if (shader.isPresent()) {
                shader.getValue().dispose();
                shader.setValue(null);
            }
        }
    }

    public void reload() {

        for (MutableValue<ShaderProgram> value : loadedShaders.values()) {
            ShaderProgram shaderProgram = value.getValue();
            if (shaderProgram != null) {
                shaderProgram.dispose();
            }
        }

        for (Map.Entry<String, ShaderProgramBuilder> entry : registeredShaders.entrySet()) {
            loadedShaders.get(entry.getKey()).setValue(entry.getValue().build());
        }
    }

    public void bindShader(ShaderProgram sp) {
        if (!overriding) {
            bindShaderInternal(sp);
        }
    }

    public void bindShader(String name) {
        if (loadedShaders.containsKey(name)) {
            bindShader(loadedShaders.get(name).getValue());
        } else {
            Platform.getLogger().warn("Shader Program %s cannot be found at Shader Manager!", name);
        }
    }

    public ObservableValue<ShaderProgram> getShader(String name) {
        return loadedShaders.get(name).toImmutable();
    }

    private void bindShaderInternal(ShaderProgram sp) {
        if (usingShader != null) {
            lastShader = usingShader;
        }
        usingShader = sp;
        usingShader.use();
        uniforms.forEach(this::setUniform);
    }

    public void restoreShader() {
        if (!overriding && lastShader != null) {
            var tmp = lastShader;
            bindShaderInternal(tmp);
        }
    }

    public void bindShaderOverriding(ShaderProgram sp) {
        overriding = true;
        bindShaderInternal(sp);
    }

    public void unbindOverriding() {
        overriding = false;
        restoreShader();
    }

    public void setUniform(String location, Integer value) {
        if (value == null) {
            usingShader.setUniform(location, 0);
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Float value) {
        if (value == null) {
            usingShader.setUniform(location, 0f);
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Boolean value) {
        if (value == null) {
            usingShader.setUniform(location, false);
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Vector2fc value) {
        if (value == null) {
            usingShader.setUniform(location, new Vector2f());
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Vector3fc value) {
        if (value == null) {
            usingShader.setUniform(location, new Vector3f());
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Vector4fc value) {
        if (value == null) {
            usingShader.setUniform(location, new Vector4f());
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Matrix3fc value) {
        if (value == null) {
            usingShader.setUniform(location, new Matrix3f());
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Matrix4fc value) {
        if (value == null) {
            usingShader.setUniform(location, new Matrix4f());
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    public void setUniform(String location, Matrix4fc[] value) {
        if (value == null) {
            usingShader.setUniform(location, new Matrix4f[0]);
            uniforms.remove(location);
        } else {
            usingShader.setUniform(location, value);
            uniforms.put(location, value);
        }
    }

    private void setUniform(String location, Object value) {
        boolean insertable = false;
        Class<?> chosen = null;
        for (Class<?> aClass : SUPPORTED_UNIFORM_TYPE) {
            if (aClass.isInstance(value)) {
                insertable = true;
                chosen = aClass;
            }
            if (insertable) break;
        }
        if (insertable) {
            try {
                var setUniform = this.getClass().getDeclaredMethod("setUniform", String.class, chosen);
                setUniform.invoke(this, location, (value));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Platform.getLogger().warn("Exception thrown when setting uniform", e);
            }
        }
    }

    public ShaderProgram getUsingShader() {
        return usingShader;
    }
}
