package nullengine.client.rendering.gui.font;

import nullengine.Platform;
import nullengine.client.rendering.font.Font;
import org.lwjgl.stb.STBTTFontinfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.memUTF8;

public class NativeTTFontInfo {

    private final Path fontFile;
    private ByteBuffer fontData;

    private STBTTFontinfo fontInfo;
    private final Font font;

    private final String family;
    private final String style;

    private final double ascent;
    private final double descent;
    private final double lineGap;
    private final float contentScaleX;
    private final float contentScaleY;

    public NativeTTFontInfo(Path fontFile, STBTTFontinfo fontInfo, String family, String style, double ascent, double descent, double lineGap, float contentScaleX, float contentScaleY) {
        this.fontFile = fontFile;
        this.fontInfo = fontInfo;
        this.fontData = null;
        this.family = family;
        this.style = style;
        this.font = new Font(family, style, 1);
        this.ascent = ascent;
        this.descent = descent;
        this.lineGap = lineGap;
        this.contentScaleX = contentScaleX;
        this.contentScaleY = contentScaleY;
    }

    public NativeTTFontInfo(ByteBuffer fontData, STBTTFontinfo fontInfo, String family, String style, double ascent, double descent, double lineGap, float contentScaleX, float contentScaleY) {
        this.fontFile = null;
        this.fontInfo = fontInfo;
        this.fontData = fontData;
        this.family = family;
        this.style = style;
        this.font = new Font(family, style, 1);
        this.ascent = ascent;
        this.descent = descent;
        this.lineGap = lineGap;
        this.contentScaleX = contentScaleX;
        this.contentScaleY = contentScaleY;
    }

    public STBTTFontinfo getFontInfo() {
        return fontInfo;
    }

    public ByteBuffer getFontData() {
        if (fontData == null) {
            try {
                byte[] bytes = Files.readAllBytes(fontFile);
                fontData = ByteBuffer.allocateDirect(bytes.length).put(bytes).flip();
                fontInfo = STBTTFontinfo.create();
                if (!stbtt_InitFont(fontInfo, fontData)) {
                    throw new IllegalStateException("Failed in initializing ttf font info");
                }
            } catch (IOException e) {
                throw new IllegalStateException("Cannot read font data", e);
            }
        }
        return fontData;
    }

    public String getFamily() {
        return family;
    }

    public String getStyle() {
        return style;
    }

    public double getAscent() {
        return ascent;
    }

    public double getDescent() {
        return descent;
    }

    public double getLineGap() {
        return lineGap;
    }

    public float getContentScaleX() {
        return Platform.isClient() ? Platform.getEngineClient().getRenderContext().getWindow().getContentScaleX() : contentScaleX;
    }

    public float getContentScaleY() {
        return Platform.isClient() ? Platform.getEngineClient().getRenderContext().getWindow().getContentScaleY() : contentScaleY;
    }

    public Font getFont() {
        return font;
    }

    public String getFontName(int nameID) {
        ByteBuffer buffer = stbtt_GetFontNameString(fontInfo, STBTT_PLATFORM_ID_MICROSOFT, STBTT_MS_EID_UNICODE_BMP, STBTT_MS_LANG_ENGLISH, nameID);
        if (buffer == null) {
            return null;
        }
        return memUTF8(buffer.order(ByteOrder.BIG_ENDIAN));
    }

}
