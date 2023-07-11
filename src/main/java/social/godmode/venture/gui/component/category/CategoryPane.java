package social.godmode.venture.gui.component.category;

import lombok.Getter;
import lombok.Setter;
import social.godmode.venture.gui.ClickGui;
import social.godmode.venture.gui.component.Component;

@Getter @Setter
public class CategoryPane implements Component {

    private final ClickGui parent;
    private int x, y, width, height;
    private boolean open;

    public CategoryPane(ClickGui parent, int x, int y, int width, int height) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks, int x, int y) {

    }
}
