package social.godmode.venture.util.minecraft.position;

import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;
import social.godmode.venture.util.MinecraftUtil;

public class ScreenPosition extends MinecraftUtil {

	@Getter private double relativeX, relativeY;
	
	public ScreenPosition(double x, double y) {
		setRelative(x, y);
	}

	public ScreenPosition(int x, int y) {
		setAbsolute(x, y);
	}

	public void setAbsolute(int x, int y) {
		ScaledResolution res = new ScaledResolution(mc);
		
		this.relativeX = (double) x / res.getScaledWidth();
		this.relativeY = (double) y / res.getScaledHeight();
	}

	public void setRelative(double x, double y) {
		this.relativeX = x;
		this.relativeY = y;
	}

	public int getAbsoluteX() { return (int) (relativeX * new ScaledResolution(mc).getScaledWidth()); }
	public int getAbsoluteY() { return (int) (relativeY * new ScaledResolution(mc).getScaledHeight()); }

	public static ScreenPosition relative(double x, double y) { return new ScreenPosition(x, y); }
	public static ScreenPosition abs(int x, int y) { return new ScreenPosition(x, y); }
}