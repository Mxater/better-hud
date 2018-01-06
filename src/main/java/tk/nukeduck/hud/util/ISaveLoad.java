package tk.nukeduck.hud.util;

import net.minecraft.util.math.MathHelper;

/** generic interface for objects which can be saved and restored from a
 * string representation, for use in config files */
public interface ISaveLoad {
	/** @return A {@link #set(T)}-compatible
	 * representation of this object */
	public abstract String save();

	/** @param save A representation of the object to load,
	 * generated by {@link #save()} */
	public abstract void load(String save);

	/** Generic interface for objects which can be saved and restored from a
	 * single value. Instances also implement {@link ISaveLoad} */
	public static interface IGetSet<T> extends ISaveLoad {
		/** @return A {@link #set(Object)}-compatible
		 * representation of this object */
		public T get();

		/** @param value A representation of the object to load,
		 * generated by {@link #get()} */
		public void set(T value);
	}

	/** Interface representing a slider which maintains a minimum and maximum,
	 * an interval between valid values and a current value */
	public static interface ISlider extends IGetSet<Double> {
		/** @return the minimum of the slider's range */
		public Double getMinimum();

		/** @return The maximum of the slider's range */
		public Double getMaximum();

		/** @return The string to display on the background of the slider
		 * given its current value */
		public String getDisplayString();

		/** @return The interval between values.<br>
		 * Valid values are {@link #getMinimum()} {@code + k *} {@link #getInterval()} */
		public Double getInterval();

		/** Processes the value of {@code slider} so that it satisfies the
		 * following requirements:
		 * <ul>
		 * <li>{@code slider.getMinimum() <= slider.get() <= slider.getMaximum()}
		 * <li>{@code slider.get() - slider.getMinimum()} is a multiple of {@code slider.getInterval()}
		 * </ul> */
		public static void normalize(ISlider slider) {
			double value = slider.get();
			double interval = slider.getInterval();
			double minimum = slider.getMinimum();

			if(interval != -1) {
				value -= minimum;
				value = Math.round(value / interval) * interval;
				value += minimum;
			}
			value = MathHelper.clamp(value, minimum, slider.getMaximum());

			slider.set(value);
		}
	}
}