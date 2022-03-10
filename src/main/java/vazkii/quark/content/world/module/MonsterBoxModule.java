package vazkii.quark.content.world.module;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.Quark;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.QuarkModule;
import vazkii.quark.base.module.config.Config;
import vazkii.quark.base.module.config.type.DimensionConfig;
import vazkii.quark.base.world.WorldGenHandler;
import vazkii.quark.base.world.WorldGenWeights;
import vazkii.quark.content.world.block.MonsterBoxBlock;
import vazkii.quark.content.world.block.be.MonsterBoxBlockEntity;
import vazkii.quark.content.world.gen.MonsterBoxGenerator;
	
@LoadModule(category = ModuleCategory.WORLD, hasSubscriptions = true)
public class MonsterBoxModule extends QuarkModule {

	public static final String TAG_MONSTER_BOX_SPAWNED = "quark:monster_box_spawned";
	public static final ResourceLocation MONSTER_BOX_LOOT_TABLE = new ResourceLocation(Quark.MOD_ID, "misc/monster_box");
	
	public static BlockEntityType<MonsterBoxBlockEntity> blockEntityType;
	
	@Config(description = "The chance for the monster box generator to try and place one in a chunk, 1 is 100%\nThis can be higher than 100% if you want multiple per chunk, , 0 is 0%") 
	public static double chancePerChunk = 0.2;
	
	@Config public static int minY = -50;
	@Config public static int maxY = 0;
	@Config public static int minMobCount = 5;
	@Config public static int maxMobCount = 8;
	@Config public static DimensionConfig dimensions = DimensionConfig.overworld(false);
	@Config public static boolean enableExtraLootTable = true;
	
	@Config(description = "How many blocks to search vertically from a position before trying to place a block. Higher means you'll get more boxes in open spaces.")
	public static int searchRange = 15;
	
	public static Block monster_box = null;
	
	@Override
	public void register() {
		monster_box = new MonsterBoxBlock(this);
		
		blockEntityType = BlockEntityType.Builder.of(MonsterBoxBlockEntity::new, monster_box).build(null);
		RegistryHelper.register(blockEntityType, "monster_box");
	}
	
	@Override
	public void setup() {
		WorldGenHandler.addGenerator(this, new MonsterBoxGenerator(dimensions), Decoration.UNDERGROUND_DECORATION, WorldGenWeights.MONSTER_BOXES);
	}
	
	@SubscribeEvent
	public void onDrops(LivingDropsEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if(enableExtraLootTable && entity.getCommandSenderWorld() instanceof ServerLevel 
				&& entity.getPersistentData().getBoolean(TAG_MONSTER_BOX_SPAWNED) 
				&& entity.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)
				&& event.getSource().getEntity() instanceof Player) {
			LootTable loot = ((ServerLevel) entity.getCommandSenderWorld()).getServer().getLootTables().get(MONSTER_BOX_LOOT_TABLE);
			if(loot != null)
				loot.getRandomItems(new LootContext.Builder((ServerLevel) entity.getCommandSenderWorld()).create(LootContextParamSets.EMPTY), entity::spawnAtLocation);
		}
	}
	
}
