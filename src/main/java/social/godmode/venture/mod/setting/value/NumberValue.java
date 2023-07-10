package social.godmode.venture.mod.setting.value;

import lombok.Getter;
import lombok.Setter;
import social.godmode.venture.mod.setting.Setting;

public class NumberValue<T extends Number> extends Setting<Number> {

    @Getter @Setter
    private T min, max;

    public NumberValue(String name, T value, T min, T max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }

}