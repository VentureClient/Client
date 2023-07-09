package social.godmode.venture.mod.mods;

import lombok.NonNull;
import net.minecraft.client.gui.FontRenderer;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventRender2D;
import social.godmode.venture.mod.ModDraggable;
import social.godmode.venture.mod.data.DraggableInfo;
import social.godmode.venture.mod.data.ModInfo;

@ModInfo(name = "DraggableTest")
@DraggableInfo(height = 10)
public class TestDraggableMod extends ModDraggable {

    @Override
    public void init() {
        setEnabled(true);
    }

    @Override
    public void render(@NonNull EventRender2D e) {
        FontRenderer fr = mc.fontRendererObj;

        width = (fr.getStringWidth("Draggable Test")+5);
        height = (FontRenderer.FONT_HEIGHT +5);

        fr.drawStringWithShadow("Draggable Test", x+2, y+2, 0xFFFFFFFF);
    }

}
