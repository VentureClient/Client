package social.godmode.venture.mod;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventRender2D;
import social.godmode.venture.mod.data.DraggableInfo;
import social.godmode.venture.util.Chat;

@Getter @Setter
public abstract class ModDraggable extends Mod {

    private final DraggableInfo draggableInfo = getClass().getAnnotation(DraggableInfo.class);

    protected double x, y, dragX, dragY, width, height;

    public ModDraggable() {
        super();

        if(draggableInfo == null) {
            throw new RuntimeException("ModDraggable must have DraggableInfo annotation");
        }

        this.x = draggableInfo.x();
        this.y = draggableInfo.y();
        this.width = draggableInfo.width();
        this.height = draggableInfo.height();
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void render(@NonNull EventRender2D e);
    @EventInfo public void onRender(@NonNull EventRender2D e) { render(e); }

}
