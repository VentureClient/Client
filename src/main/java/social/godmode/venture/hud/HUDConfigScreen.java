package social.godmode.venture.hud;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import social.godmode.venture.util.minecraft.position.ScreenPosition;

public class HUDConfigScreen extends GuiScreen {

	@Getter
	private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();

	@SuppressWarnings("all")
	private Optional<IRenderer> selectedRenderer = Optional.empty();

	private int prevX, prevY;
	private int dragX, dragY;

	public HUDConfigScreen() {

		Collection<IRenderer> registeredRenderers = HUDManager.getRegisteredRenderers();

		for(IRenderer ren : registeredRenderers) {
			if(!ren.isEnabled()) {
				continue;
			}

			ScreenPosition pos = ren.load();

			if(pos == null) {
				pos = ScreenPosition.relative(0.5, 0.5);
			}

			adjustBounds(ren, pos);
			this.renderers.put(ren, pos);
		}

		this.buttonList.add(new GuiButton(0, this.width/2 - 100, this.height / 2 - 20, 100, 20, "Toggle Mods"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		final float zBackup = this.zLevel;
		this.zLevel = 200;

		this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFF0000);

		for(IRenderer renderer : renderers.keySet()) {

			ScreenPosition pos = renderers.get(renderer);

			renderer.renderDummy(pos);

			this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), 0xFF00FFFF);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
		this.zLevel = zBackup;
	}

	private void drawHollowRect(int x, int y, int w, int h, int color) {
		this.drawHorizontalLine(x, x + w, y, color);
		this.drawHorizontalLine(x, x + w, y + h, color);

		this.drawVerticalLine(x, y + h, y, color);
		this.drawVerticalLine(x + w, y + h, y, color);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			renderers.forEach(IRenderConfig::save);
			this.mc.displayGuiScreen(null);
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long time) {

		if(selectedRenderer.isPresent()) {
			moveSelectedRenderBy(mouseX - prevX, mouseY - prevY);
		}

		this.prevX = mouseX;
		this.prevY = mouseY;
		super.mouseClickMove(mouseX, mouseY, mouseButton, time);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		this.dragX = 0;
		this.dragY = 0;
		super.mouseReleased(mouseX, mouseY, state);
	}

	private void moveSelectedRenderBy(int offsetX, int offsetY) {
		IRenderer renderer = selectedRenderer.get();
		ScreenPosition pos = renderers.get(renderer);

		pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);

		adjustBounds(renderer, pos);
	}

	@Override
	public void onGuiClosed() {
		renderers.keySet().forEach((renderer) -> renderer.save(renderers.get(renderer)));
	}

	private void adjustBounds(IRenderer renderer, ScreenPosition pos) {

		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();

		int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

		pos.setAbsolute(absoluteX, absoluteY);
	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton) throws IOException {
		this.prevX = x;
		this.prevY = y;

		this.dragX = x;
		this.dragY = y;

		loadMouseOver(x, y);
		super.mouseClicked(x, y, mouseButton);
	}

	private void loadMouseOver(int x, int y) {
		this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
	}

	private class MouseOverFinder implements Predicate<IRenderer> {

		@Getter @Setter
		private int mouseX, mouseY;

		public MouseOverFinder(int x, int y) {
			this.mouseX = x;
			this.mouseY = y;
		}

		@Override
		public boolean test(IRenderer renderer) {
			ScreenPosition pos = renderers.get(renderer);

			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();

			if(mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {
				return mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight();
			}

			return false;
		}

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}


}