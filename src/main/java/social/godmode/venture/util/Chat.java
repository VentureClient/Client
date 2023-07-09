package social.godmode.venture.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

@UtilityClass
public class Chat {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void print(@NonNull String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message.replace("&", "§")));
    }

    public static void printf(@NonNull String message, Object... args) {
        print(String.format(message, args));
    }
}
