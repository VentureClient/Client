package social.godmode.venture.gui;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import social.godmode.venture.gui.component.Component;
import social.godmode.venture.gui.component.category.CategoryPane;
import social.godmode.venture.mod.data.Category;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {

    @Getter
    public static ClickGui instance;

    @Getter
    private final List<Component> components = new ArrayList<>();

    private final int width, height;
    private int x, y, dragX, dragY;
    private boolean dragging;

    public ClickGui(int width, int height) {
        this.x = 0;
        this.y = 5;
        this.width = width;
        this.height = height;

        for(Category category : Category.values()) {

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        // whole
        drawRect(x, y, x + width, y + height, Color.DARK_GRAY.darker().getRGB());

        drawRect(x, y - 20, x + width, y, Color.DARK_GRAY.getRGB());
        drawString(mc.fontRendererObj, "Venture", x + 5, y - 15, Color.WHITE.getRGB());
        drawString(mc.fontRendererObj, "X", x + width - 10, y - 15, Color.WHITE.getRGB());


        CategoryPane lastCategoryPane = null;
        int offsetY = 0;
        for(Component component : components) {

            if(component instanceof CategoryPane) {
                CategoryPane categoryPane = (CategoryPane) component;
                categoryPane.setX(x);
                categoryPane.setY(y + offsetY);
                categoryPane.setWidth(width);
                categoryPane.setHeight(height - offsetY);
                categoryPane.render(mouseX, mouseY, partialTicks, x, y + offsetY);

                offsetY += lastCategoryPane == null ? categoryPane.getHeight() : 20;
                lastCategoryPane = categoryPane;
            }

        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseX >= x && mouseX <= x + width && mouseY >= y - 20 && mouseY <= y && mouseButton == 0) {
            dragX = x - mouseX;
            dragY = y - mouseY;
            dragging = true;
        }

        if(mouseX >= x + width - 10 && mouseX <= x + width && mouseY >= y - 20 && mouseY <= y && mouseButton == 0) {
            dragging = false;
            mc.displayGuiScreen(null);
        }

        components.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton, x, y));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;

        components.forEach(component -> component.mouseReleased(mouseX, mouseY, state, x, y));
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        components.forEach(component -> component.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick, x, y));
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        components.forEach(component -> component.keyTyped(typedChar, keyCode, x, y));
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onGuiClosed() {
        components.forEach(Component::onGuiClosed);
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void display() {
        if(instance == null) {
            instance = new ClickGui(400, 200);
        }

        Minecraft.getMinecraft().displayGuiScreen(instance);
    }
}
