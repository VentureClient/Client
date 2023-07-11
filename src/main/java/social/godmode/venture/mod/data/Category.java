package social.godmode.venture.mod.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    SERVER("Server"), HUD("HUD"),
    MECHANICS("Mechanics"), RENDER("Render");

    final String name;
}
