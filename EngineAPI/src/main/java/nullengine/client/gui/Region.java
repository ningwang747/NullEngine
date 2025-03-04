package nullengine.client.gui;

import com.github.mouse0w0.observable.value.MutableValue;
import com.github.mouse0w0.observable.value.SimpleMutableObjectValue;
import nullengine.client.gui.misc.*;
import nullengine.client.gui.rendering.ComponentRenderer;
import nullengine.client.gui.rendering.RegionRenderer;
import nullengine.math.Math2;

public class Region extends Container {

    private final Size size = new Size();

    public final Size getSize() {
        return size;
    }

    private final MutableValue<Background> background = new SimpleMutableObjectValue<>(Background.NOTHING);
    private final MutableValue<Border> border = new SimpleMutableObjectValue<>();

    public MutableValue<Background> background() {
        return background;
    }

    public MutableValue<Border> border() {
        return border;
    }

    private final MutableValue<Insets> padding = new SimpleMutableObjectValue<>(Insets.EMPTY);

    public final MutableValue<Insets> padding() {
        return padding;
    }

    @Override
    public float minWidth() {
        return getSize().minWidth().get();
    }

    public static void positionInArea(Component child, float areaX, float areaY, float areaWidth, float areaHeight,
                                      float areaBaselineOffset, Insets margin, Pos.HPos halignment, Pos.VPos valignment, boolean isSnapToPixel) {
        Insets childMargin = margin != null ? margin : Insets.EMPTY;

        position(child, areaX, areaY, areaWidth, areaHeight, areaBaselineOffset,
                childMargin.getTop(),
                childMargin.getRight(),
                childMargin.getBottom(),
                childMargin.getLeft(),
                halignment, valignment, isSnapToPixel);
    }

    protected static float snap(float value, boolean snapToPixel) {
        return snapToPixel ? Math.round(value) : value;
    }

    private static void position(Component child, float areaX, float areaY, float areaWidth, float areaHeight,
                                 float areaBaselineOffset,
                                 float topMargin, float rightMargin, float bottomMargin, float leftMargin,
                                 Pos.HPos hpos, Pos.VPos vpos, boolean isSnapToPixel) {
        final float xoffset = leftMargin + computeXOffset(areaWidth - leftMargin - rightMargin,
                child.width().get(), hpos);
        final float yoffset;
        /*if (vpos == Pos.VPos.BASELINE) {
            double bo = child.getBaselineOffset();
            if (bo == BASELINE_OFFSET_SAME_AS_HEIGHT) {
                // We already know the layout bounds at this stage, so we can use them
                yoffset = areaBaselineOffset - child.getLayoutBounds().getHeight();
            } else {
                yoffset = areaBaselineOffset - bo;
            }
        } else */
        {
            yoffset = topMargin + computeYOffset(areaHeight - topMargin - bottomMargin,
                    child.height().get(), vpos);
        }
        final float x = snap(areaX + xoffset, isSnapToPixel);
        final float y = snap(areaY + yoffset, isSnapToPixel);

        child.relocate(x, y);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    static float computeXOffset(float width, float contentWidth, Pos.HPos hpos) {
        switch (hpos) {
            case LEFT:
                return 0;
            case CENTER:
                return (width - contentWidth) / 2;
            case RIGHT:
                return width - contentWidth;
            default:
                throw new AssertionError("Unhandled hPos");
        }
    }

    static float computeYOffset(float height, float contentHeight, Pos.VPos vpos) {
        switch (vpos) {
            case BASELINE:
            case TOP:
                return 0;
            case CENTER:
                return (height - contentHeight) / 2;
            case BOTTOM:
                return height - contentHeight;
            default:
                throw new AssertionError("Unhandled vPos");
        }
    }

    @Override
    public float minHeight() {
        return getSize().minHeight().get();
    }

    @Override
    public float prefWidth() {
        float width = getSize().prefWidth().get();
        return width != 0 ? width : super.prefWidth();
    }

    @Override
    public float prefHeight() {
        float height = getSize().prefHeight().get();
        return height != 0 ? height : super.prefHeight();
    }

    @Override
    public float maxWidth() {
        return getSize().maxWidth().get();
    }

    @Override
    public float maxHeight() {
        return getSize().maxHeight().get();
    }

    @Override
    protected ComponentRenderer createDefaultRenderer() {
        return RegionRenderer.INSTANCE;
    }

    protected void layoutInArea(Component c, float areaX, float areaY, float areaWidth, float areaHeight, int areaBaselineOffset, Insets margin, Pos.HPos hAlign, Pos.VPos vAlign) {
        Insets childMargin = margin != null ? margin : Insets.EMPTY;

        float top = childMargin.getTop();
        float bottom = childMargin.getBottom();
        float left = childMargin.getLeft();
        float right = childMargin.getRight();

//        if (valignment == VPos.BASELINE) {
//            double bo = child.getBaselineOffset();
//            if (bo == BASELINE_OFFSET_SAME_AS_HEIGHT) {
//                if (child.isResizable()) {
//                    // Everything below the baseline is like an "inset". The Node with BASELINE_OFFSET_SAME_AS_HEIGHT cannot
//                    // be resized to this area
//                    bottom += snapSpace(areaHeight - areaBaselineOffset, isSnapToPixel);
//                } else {
//                    top = snapSpace(areaBaselineOffset - child.getLayoutBounds().getHeight(), isSnapToPixel);
//                }
//            } else {
//                top = snapSpace(areaBaselineOffset - bo, isSnapToPixel);
//            }
//        }


//        if (child.isResizable()) {
//            Vec2d size = boundedNodeSizeWithBias(child, areaWidth - left - right, areaHeight - top - bottom,
//                    fillWidth, fillHeight, TEMP_VEC2D);
        c.resize(areaWidth - left - right, areaHeight - top - bottom);
//        }
        position(c, areaX, areaY, areaWidth, areaHeight, areaBaselineOffset,
                top, right, bottom, left, hAlign, vAlign, true);
    }

    protected float computeChildPrefAreaWidth(Component child, float baselineComplement, Insets margin, float height, boolean fillHeight) {
        float left = margin != null ? margin.getLeft() : 0;
        float right = margin != null ? margin.getRight() : 0;
        float alt = -1;
        if (height != -1) {
            //TODO width depends on height
        }
        return left + Math2.clamp(child.minWidth(), child.prefWidth(), child.maxWidth()) + right;
    }

    protected float computeChildPrefAreaHeight(Component child, float baselineComplement, Insets margin, float width) {
        float top = margin != null ? margin.getTop() : 0;
        float bottom = margin != null ? margin.getBottom() : 0;
        float alt = -1;
        if (false) {
            //TODO height depends on width
        }

        if (baselineComplement != -1) {
            return 0; //TODO
        } else {
            return top + Math2.clamp(child.minHeight(), child.prefHeight(), child.maxHeight()) + bottom;
        }
    }
}
