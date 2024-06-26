package vazkii.quark.content.world.undergroundstyle.base;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import vazkii.quark.base.module.config.type.ClusterSizeConfig;
import vazkii.quark.base.module.config.type.IBiomeConfig;

public class UndergroundStyleConfig<T extends UndergroundStyle> extends ClusterSizeConfig {

	public final T biomeObj;

	@SafeVarargs
	public UndergroundStyleConfig(T biomeObj, int rarity, boolean isBlacklist, TagKey<Biome>... tags) {
		super(rarity, 26, 14, 14, 6, isBlacklist, tags);
		this.biomeObj = biomeObj;
	}

	@SafeVarargs
	public UndergroundStyleConfig(T biomeObj, int rarity, TagKey<Biome>... tags) {
		this(biomeObj, rarity, false, tags);
	}
	
	public UndergroundStyleConfig(T biomeObj, int rarity, int horizontal, int vertical, int horizontalVariation, int verticalVariation, IBiomeConfig config) {
		super(rarity, horizontal, vertical, horizontalVariation, verticalVariation, config);
		this.biomeObj = biomeObj;
	}
	
	public UndergroundStyleConfig<T> setDefaultSize(int horizontal, int vertical, int horizontalVariation, int verticalVariation) {
		this.horizontalSize = horizontal;
		this.verticalSize = vertical;
		this.horizontalVariation = horizontalVariation;
		this.verticalVariation = verticalVariation;
		return this;
	}

}
