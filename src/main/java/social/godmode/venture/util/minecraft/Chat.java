package social.godmode.venture.util.minecraft;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.minecraft.util.ChatComponentText;
import social.godmode.venture.util.MinecraftUtil;

@UtilityClass
public class Chat extends MinecraftUtil {

    public static void print(@NonNull String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message.replace("&", "§")));
    }

    public static void printf(@NonNull String message, Object... args) {
        print(String.format(message, args));
    }
}
