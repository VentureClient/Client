package social.godmode.venture.mod.manager;

import lombok.Getter;
import social.godmode.venture.mod.Mod;
import social.godmode.venture.mod.ModDraggable;
import social.godmode.venture.util.other.ClassPathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModManager {

    @Getter
    private static final List<Mod> mods = new ArrayList<>();

    public static void registerAllMods() {
        try {
            List<Class<?>> classes = ClassPathUtil.getClassesForPackage("social.godmode.venture.mod.mods");

            classes.stream().filter(clazz -> (clazz.getSuperclass() == Mod.class || clazz.getSuperclass() == ModDraggable.class)).forEach(clazz -> {
                try {
                    Mod mod = (Mod) clazz.newInstance();
                    mod.init();

                    register(mod);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            getMods().forEach((mod -> mod.setEnabled(true)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(Mod... addedMods) {
        mods.addAll(Arrays.asList(addedMods));
    }

    public static Mod getMod(Class<? extends Mod> clazz) {
        return mods.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }

    public static Mod getMod(String name) {
        return mods.stream().filter(mod -> mod.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static List<ModDraggable> getDraggableMods() {
        return mods.stream().filter(mod -> mod instanceof ModDraggable).map(mod -> (ModDraggable) mod).collect(Collectors.toList());
    }

}
