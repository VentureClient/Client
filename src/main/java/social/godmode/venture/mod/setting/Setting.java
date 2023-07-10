package social.godmode.venture.mod.setting;

import lombok.Getter;
import lombok.Setter;
import social.godmode.venture.mod.Mod;

@Getter @Setter
public class Setting<T> {
    private String name;
    private Mod parent;
    private T value;

    public Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }
}