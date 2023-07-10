package social.godmode.venture.hud;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.client.Minecraft;
import social.godmode.venture.util.minecraft.position.ScreenPosition;

public class HUDManager {
	public static HUDConfigScreen configScreen;

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final Set<IRenderer> registeredRenderers = Sets.newHashSet();

	public static void callRenderer(IRenderer renderer) {
		if(!renderer.isEnabled()) return;

		ScreenPosition pos = renderer.load();

		if(pos == null) pos = ScreenPosition.relative(0.5, 0.5);

		renderer.render(pos);
	}

	public static void register(IRenderer... renderers) { registeredRenderers.addAll(Arrays.asList(renderers)); }
	public static void unregister(IRenderer... renderers) { Arrays.stream(renderers).forEach(registeredRenderers::remove); }

	public static void openConfigScreen() {
		if(configScreen == null) {
			configScreen = new HUDConfigScreen();
		}
		mc.displayGuiScreen(configScreen);
	}

	public static Collection<IRenderer> getRegisteredRenderers() { return Sets.newHashSet(registeredRenderers); }

}