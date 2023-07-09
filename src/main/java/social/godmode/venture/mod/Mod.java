package social.godmode.venture.mod;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import social.godmode.venture.Venture;
import social.godmode.venture.event.manager.EventManager;
import social.godmode.venture.mod.data.ModInfo;

@Getter
public class Mod {

    private final ModInfo info = getClass().getAnnotation(ModInfo.class);

    protected Minecraft mc = Minecraft.getMinecraft();

    private final String name, description;
    private boolean enabled;

    public Mod() {
        if(info == null) {
            throw new RuntimeException("ModInfo annotation not found!");
        }

        name = info.name();
        description = info.description();
    }

    public void init() {}

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if(enabled) onEnable();
        else onDisable();
    }

    public void onEnable() {
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
    }

}
