package vazkii.quark.base.client.config;

import org.apache.commons.lang3.text.WordUtils;
import vazkii.quark.api.config.IConfigCategory;
import vazkii.quark.api.config.IConfigObject;
import vazkii.quark.base.module.ModuleCategory;

import java.util.HashMap;
import java.util.Map;

public class TopLevelCategory extends ConfigCategory {

	private final Map<String, IConfigObject<Boolean>> moduleOptions = new HashMap<>();

	public TopLevelCategory(String name, String comment, IConfigCategory parent) {
		super(name, comment, parent, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> void addEntry(IConfigObject<T> obj, T default_) {
		super.addEntry(obj, default_);

		if(default_ instanceof Boolean)
			moduleOptions.put(obj.getName(), (IConfigObject<Boolean>) obj);
	}

	public IConfigObject<Boolean> getModuleOption(ModuleCategory category) {
		return moduleOptions.get(WordUtils.capitalizeFully(category.name));
	}

}
