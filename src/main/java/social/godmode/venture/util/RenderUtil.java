package social.godmode.venture.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

@UtilityClass
public class RenderUtil {

    public void drawRect(double x, double y, double width, double height, Color color) {
        drawRect(x, y, width, height, color.getRGB());
    }

    public void drawRect(double x, double y, double width, double height, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Gui.drawRect(x, y, x + width, y + height, color);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public void drawRect(double x, double y, double width, double height) {
        drawRect(x, y, width, height, Color.WHITE);
    }

}