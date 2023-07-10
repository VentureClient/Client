package social.godmode.venture.hud;

import social.godmode.venture.util.minecraft.position.ScreenPosition;

public interface IRenderConfig {
	void save(ScreenPosition pos);
	ScreenPosition load();
}