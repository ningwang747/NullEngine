package nullengine.client.gui.component;

import com.github.mouse0w0.observable.value.MutableDoubleValue;
import com.github.mouse0w0.observable.value.MutableValue;
import com.github.mouse0w0.observable.value.SimpleMutableDoubleValue;
import nullengine.client.gui.Region;
import nullengine.client.gui.event.MouseEvent;
import nullengine.client.gui.misc.Background;
import nullengine.event.Event;
import nullengine.util.Color;


public class HSlider extends Region {
    private Label slider = new Label();

    private Label back = new Label();

    private MutableDoubleValue value = new SimpleMutableDoubleValue(0);

    private double preMove = 0;

    private boolean select = false;

    public HSlider() {
        value.addChangeListener((ob, o, n) -> rebuild());
        this.getChildren().addAll(back, slider);
        backBg().setValue(new Background(Color.BLUE));
        sliderBg().setValue(new Background(Color.WHITE));
    }

    public MutableDoubleValue value() {
        return value;
    }

    public void setPreMove(double preMove) {
        this.preMove = preMove;
    }

    public void rebuild() {
        if (value.get() > 0.99) {
            value.set(0.99);
        } else if (value.get() < 0) {
            value.set(0);
        }
        slider.x().set((float) ((back.prefWidth()) * value.get()));
        slider.y().set(back.y().get() + back.prefHeight() / 2 - slider.prefHeight() / 2);
    }

    @Override
    public void onClick(MouseEvent.MouseClickEvent e) {
        super.onClick(e);
        if (e.getPosX() > slider.x().get() + slider.prefWidth()) {
            value.set(value.getValue() + preMove);
        } else if (e.getPosX() < slider.x().get()) {
            value.set(value.getValue() - preMove);
        }
        if (slider.contains(e.getPosX(), e.getPosY()))
            select = true;
    }

    @Override
    public void handleEvent(Event event) {
        super.handleEvent(event);
        if (event instanceof MouseEvent.MouseMoveEvent && select) {
            if ((((MouseEvent.MouseMoveEvent) event).getNewPosX() - x().get() - slider.x().get()) / prefWidth() > preMove * 0.9) {
                value.set(value.getValue() + preMove);
            } else if ((slider.x().get() - ((MouseEvent.MouseMoveEvent) event).getNewPosX() + x().get()) / prefWidth() > preMove * 0.9) {
                value.set(value.getValue() - preMove);
            }
        } else if (event instanceof MouseEvent.MouseReleasedEvent) {
            select = false;
        } else if (event instanceof MouseEvent.MouseLeaveEvent) {
            //select = false;
        }
    }

    public void resizeBack(float width, float height) {
        back.resize(width, height);
        rebuild();
    }

    public void resizeSlider(float width, float height) {
        slider.resize(width, height);
        rebuild();
    }

    public MutableValue<Background> backBg() {
        return back.background();
    }

    public MutableValue<Background> sliderBg() {
        return slider.background();
    }

}
