package nullengine.client.gui.rendering;

import nullengine.client.gui.component.RadioButton;
import nullengine.client.gui.misc.Insets;
import nullengine.client.rendering.RenderContext;

public class RadioButtonRenderer implements ComponentRenderer<RadioButton> {
    @Override
    public void render(RadioButton component, Graphics graphics, RenderContext context) {
        component.background().getValue().render(component, graphics);
        component.border().getValue().render(component, graphics);
        if (component.selected().get()) {
            Insets insets = component.padding().getValue();
            float width = component.width().get() - insets.getRight() - insets.getLeft();
            float height = component.width().get() - insets.getTop() - insets.getBottom();
            graphics.pushClipRect(insets.getLeft(), insets.getTop(), width, height);
            graphics.setColor(component.contentColor().getValue());
            graphics.fillRect(0, 0, width, height);
            graphics.popClipRect();
        }
    }
}
