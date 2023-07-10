package social.godmode.venture.mod;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventRender2D;
import social.godmode.venture.hud.HUDConfigScreen;
import social.godmode.venture.hud.HUDManager;
import social.godmode.venture.hud.IRenderer;
import social.godmode.venture.mod.data.DraggableInfo;
import social.godmode.venture.util.minecraft.position.ScreenPosition;
import social.godmode.venture.util.minecraft.position.Vector2D;

@Getter @Setter
public abstract class ModDraggable extends Mod implements IRenderer {

    private final DraggableInfo draggableInfo = getClass().getAnnotation(DraggableInfo.class);

    protected ScreenPosition position;
    protected double width, height;

    public ModDraggable() {
        super();

        if(draggableInfo == null) {
            throw new RuntimeException("ModDraggable must have DraggableInfo annotation");
        }

        this.position = new ScreenPosition(draggableInfo.x(), draggableInfo.y());
        this.width = draggableInfo.width();
        this.height = draggableInfo.height();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        HUDManager.register(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        HUDManager.unregister(this);
    }

    @Override
    public void render(ScreenPosition pos) {
        this.position = pos;
        onRender(this.position);
    }

    /* todo */
    @Override public void save(ScreenPosition pos) {}
    @Override public ScreenPosition load() { return position; }
    @Override public int getWidth() { return (int) width; }
    @Override public int getHeight() { return (int) height; }

    public abstract void onRender(@NonNull ScreenPosition pos);
    @Override public void renderDummy(ScreenPosition pos) { IRenderer.super.renderDummy(pos); }

    public double getX() { return position.getRelativeX(); }
    public double getY() { return position.getRelativeY(); }
}
