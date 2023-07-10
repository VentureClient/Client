package social.godmode.venture.util.minecraft.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import social.godmode.venture.util.MinecraftUtil;

@AllArgsConstructor
@Getter @Setter
public class Vector2D extends MinecraftUtil {
    private double x, y;
}
