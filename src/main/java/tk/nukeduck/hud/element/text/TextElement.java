package tk.nukeduck.hud.element.text;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import tk.nukeduck.hud.BetterHud;
import tk.nukeduck.hud.element.HudElement;
import tk.nukeduck.hud.element.settings.SettingColor;
import tk.nukeduck.hud.element.settings.SettingPosition;
import tk.nukeduck.hud.util.Bounds;
import tk.nukeduck.hud.util.Colors;
import tk.nukeduck.hud.util.Direction;
import tk.nukeduck.hud.util.LayoutManager;
import tk.nukeduck.hud.util.PaddedBounds;
import tk.nukeduck.hud.util.Point;

public abstract class TextElement extends HudElement {
	protected SettingPosition position;
	protected SettingColor color = new SettingColor("color");

	protected boolean border = false;

	public TextElement(String name) {
		this(name, Direction.CORNERS);
	}

	public TextElement(String name, Direction... directions) {
		this(name, Direction.flags(directions));
	}

	public TextElement(String name, int directions) {
		super(name);

		settings.add(position = new SettingPosition("position", directions));
		settings.add(color);
	}

	public int getColor() {
		return color.get();
	}

	@Override
	public void loadDefaults() {
		settings.set(true);
		position.set(Direction.NORTH_WEST);
		color.set(Colors.WHITE);
	}

	protected Bounds getPadding() {
		return border ? new Bounds(-BetterHud.SPACER, -BetterHud.SPACER, 2*BetterHud.SPACER, 2*BetterHud.SPACER) : Bounds.EMPTY;
	}

	protected Bounds getMargin() {
		return Bounds.EMPTY;
	}

	@Override
	public Bounds render(RenderGameOverlayEvent event, LayoutManager manager) {
		String[] text = getText();
		if(text.length == 0) return null;

		Point size = getLinesSize(text);
		PaddedBounds bounds = position.applyTo(new PaddedBounds(new Bounds(size), getPadding(), getMargin()), manager);

		if(border) drawRect(bounds.paddingBounds(), Colors.TRANSLUCENT);
		drawLines(text, bounds.contentBounds(), position.getAnchor(), color.get());

		return bounds;
	}

	protected abstract String[] getText();
}