package nullengine.client.gui.component;

import com.github.mouse0w0.observable.value.SimpleMutableFloatValue;
import com.github.mouse0w0.observable.value.SimpleMutableObjectValue;
import nullengine.client.asset.AssetPath;
import nullengine.client.gui.Component;
import nullengine.client.gui.internal.Internal;
import nullengine.client.gui.rendering.ComponentRenderer;
import nullengine.client.gui.rendering.ImageRenderer;
import nullengine.client.rendering.texture.GLTexture;

public class TextureImg extends Component {
    private final SimpleMutableObjectValue<AssetPath> image = new SimpleMutableObjectValue<>();
    private final SimpleMutableFloatValue imgX = new SimpleMutableFloatValue();
    private final SimpleMutableFloatValue imgY = new SimpleMutableFloatValue();
    private final SimpleMutableFloatValue imgWidth = new SimpleMutableFloatValue();
    private final SimpleMutableFloatValue imgHeight = new SimpleMutableFloatValue();
    private AssetPath cachedPath;
    private GLTexture cachedTexture;

    public TextureImg() {
        image.addChangeListener((ob, o, n) -> {
            buildCache();
            requestParentLayout();
        });
        imgX.addChangeListener((ob, o, n) -> requestParentLayout());
        imgY.addChangeListener((ob, o, n) -> requestParentLayout());
        imgWidth.addChangeListener((ob, o, n) -> requestParentLayout());
        imgHeight.addChangeListener((ob, o, n) -> requestParentLayout());
    }

    public TextureImg(AssetPath path) {
        this();
        image.setValue(path);
    }

    public void buildCache() {
        cachedPath = image.getValue();
        cachedTexture = Internal.getContext().getImageHelper().getTexture(cachedPath);
        if (cachedTexture != null) {
            imgWidth.set(cachedTexture.getWidth());
            imgHeight.set(cachedTexture.getHeight());
        }
    }

    @Override
    public float prefWidth() {
        return cachedTexture != null ? imgWidth.get() : 0;
    }

    @Override
    public float prefHeight() {
        return cachedTexture != null ? imgHeight.get() : 0;
    }

    @Override
    protected ComponentRenderer createDefaultRenderer() {
        return ImageRenderer.INSTANCE;
    }

    public GLTexture getCachedTexture() {
        return cachedTexture;
    }

    public SimpleMutableFloatValue imageX() {
        return imgX;
    }

    public SimpleMutableFloatValue imageY() {
        return imgY;
    }

    public SimpleMutableFloatValue imageWidth() {
        return imgWidth;
    }

    public SimpleMutableFloatValue imageHeight() {
        return imgHeight;
    }

    public SimpleMutableObjectValue<AssetPath> path() {
        return image;
    }

}
