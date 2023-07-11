package social.godmode.venture.gui.component;

public interface Component {

    default void mouseClicked(int mouseX, int mouseY, int mouseButton, int x, int y) {}
    default void mouseReleased(int mouseX, int mouseY, int state, int x, int y) {}
    default void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, int x, int y) {}
    default void keyTyped(char typedChar, int keyCode, int x, int y) {}
    default void onGuiClosed() {}

    void render(int mouseX, int mouseY, float partialTicks, int x, int y);
}
