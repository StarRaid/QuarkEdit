package vazkii.quark.base.client.config.obj;

import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import vazkii.quark.base.client.config.ConfigCategory;

public class IntegerObject extends AbstractStringInputObject<Integer> {

	public IntegerObject(ConfigValue<Integer> value, String comment, Integer defaultObj, Supplier<Integer> objGetter, Predicate<Object> restriction, ConfigCategory parent) {
		super(value, comment, defaultObj, objGetter, restriction, parent);
	}

	@Override
	public Integer fromString(String s) {
		try {
			return Integer.parseInt(s);
		} catch(NumberFormatException e) {
			return null;
		}
	}

	@Override
	public boolean isStringValid(String s) {
		return s.matches("-?[0-9]*");
	}

	@Override
	public int getMaxStringLength() {
		return 16;
	}

}
