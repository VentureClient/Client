package social.godmode.venture.mod.manager;

import social.godmode.venture.mod.Mod;
import social.godmode.venture.mod.setting.Setting;

import java.lang.reflect.Field;

public class SettingManager {

    public static void registerAllSettings() {
        for(Mod mod : ModManager.getMods()) {
            Field[] fields = mod.getClass().getDeclaredFields();

            for (Field field : fields) {

                try {
                    if (field.getType().getSuperclass().equals(Setting.class)) {

                        try {
                            field.setAccessible(true);
                            Setting<?> setting = (Setting<?>) field.get(mod);
                            setting.setParent(mod);

                            mod.getSettings().add(setting);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

}