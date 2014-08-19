import java.awt.Color;

public class Style {

	public final static Color panelColor = new Color(240, 240, 250);

	public final static Color equipmentColor = new Color(224, 191, 222);
	public final static Color equipmentHoverColor = new Color(240, 218, 238);
	public final static Color equipmentPressedColor = new Color(212, 169, 209);
	public final static Color equipmentDarkColor = new Color(108, 47, 104);

	public final static Color itemColor = new Color(252, 205, 182);
	public final static Color itemHoverColor = new Color(252, 228, 200);
	public final static Color itemPressedColor = new Color(245, 186, 157);
	public final static Color itemDarkColor = new Color(184, 59, 29);

	public final static Color skillColor = new Color(159, 213, 183);
	public final static Color skillHoverColor = new Color(220, 250, 234);
	public final static Color skillPressedColor = new Color(134, 191, 160);
	public final static Color skillDarkColor = new Color(10, 99, 50);

	public static String toRGB(Color color) {
		return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
	}

	/**
	 * This is used by CardButton in Play for holding a set of colors.
	 * 
	 * @author Ivan Ng
	 * 
	 */
	public static class CardColorHolder {

		private Color normal = null;
		private Color hover = null;
		private Color pressed = null;
		private Color dark = null;

		public CardColorHolder(Card card) {
			if (card instanceof Equipment) {
				normal = Style.equipmentColor;
				hover = Style.equipmentHoverColor;
				pressed = Style.equipmentPressedColor;
				dark = Style.equipmentDarkColor;
			} else if (card instanceof Item) {
				normal = Style.itemColor;
				hover = Style.itemHoverColor;
				pressed = Style.itemPressedColor;
				dark = Style.itemDarkColor;
			} else { // Skill
				normal = Style.skillColor;
				hover = Style.skillHoverColor;
				pressed = Style.skillPressedColor;
				dark = Style.skillDarkColor;
			}
		}

		public Color getNormalColor() {
			return normal;
		}

		public Color getHoverColor() {
			return hover;
		}

		public Color getPressedColor() {
			return pressed;
		}

		public Color getDarkColor() {
			return dark;
		}
	}

}
