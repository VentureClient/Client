package social.godmode.venture.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import social.godmode.venture.Venture;
import social.godmode.venture.mod.Mod;
import social.godmode.venture.mod.ModDraggable;
import social.godmode.venture.mod.manager.ModManager;
import social.godmode.venture.util.RenderUtil;

import java.awt.*;
import java.io.IOException;

public class DraggableGUI extends GuiScreen {

    public ModDraggable dragging = null;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(0, 0, width, 1, Color.RED);
        RenderUtil.drawRect(0, 0, 1, height, Color.RED);
        RenderUtil.drawRect(0, height - 1, width, 1, Color.RED);
        RenderUtil.drawRect(width - 1, 0, 1, height, Color.RED);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(dragging != null) return;

        for(ModDraggable mod : ModManager.getDraggableMods()) {
            if(mouseX <= mod.getX() && mouseX >= mod.getX() + mod.getWidth() && mouseY <= mod.getY() && mouseY >= mod.getY() + mod.getHeight()) continue;

            dragging = mod;
            dragging.setDragX(mouseX - dragging.getX());
            dragging.setDragY(mouseY - dragging.getY());
            break;
        }

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(dragging == null) return;

        dragging.setDragX(0);
        dragging.setDragY(0);
        dragging = null;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(dragging == null) return;

        dragging.setX(mouseX - dragging.getDragX());
        dragging.setY(mouseY - dragging.getDragY());

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void resize(float oldWidth, float oldHeight, float width, float height) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        float centerX = width / 2;
        float centerY = height / 2;

        float offsetX = centerX - (oldWidth / 2);
        float offsetY = centerY - (oldHeight / 2);

        for (ModDraggable mod : ModManager.getDraggableMods()) {
            double oldX = mod.getX();
            double oldY = mod.getY();

            double newX = oldX + offsetX;
            double newY = oldY + offsetY;

            mod.setPosition(newX, newY);
        }
    }
}