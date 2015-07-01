package org.redfrog404.spooky.scary.skeletons;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Spooky.MODID, version = Spooky.VERSION)
public class Spooky {

	public static final String MODID = "Spooky";
	public static final String VERSION = "1.0";
	
	public static final Enchantment haste = new EnchantmentArrowFast(150,
			new ResourceLocation("haste"), 2);
	public static final Enchantment poison = new EnchantmentPoison(151,
			new ResourceLocation("poison"), 2);
	public static final Enchantment bones = new EnchantmentBones(152,
			new ResourceLocation("bones"), 2);
	
	public static final CreativeTabs materials = new MaterialsTab(
			CreativeTabs.getNextID(), "materialsTab");
	public static final CreativeTabs blocks = new BlocksTab(
			CreativeTabs.getNextID(), "blocksTab");
	public static final CreativeTabs tools = new ToolsTab(
			CreativeTabs.getNextID(), "toolsTab");
	public static final CreativeTabs weapons = new WeaponsTab(
			CreativeTabs.getNextID(), "weaponsTab");
	public static final CreativeTabs records = new RecordsTab(
			CreativeTabs.getNextID(), "recordsTab");

	public static ToolMaterial HELL = EnumHelper.addToolMaterial("HELL", 3,
			3122, 15.0F, 4.0F, 15);
	public static ToolMaterial ENDER = EnumHelper.addToolMaterial("ENDER", 4,
			3122, 17.5F, 6.0F, 20);
	public static ToolMaterial BC1 = EnumHelper.addToolMaterial("BC1", 4, 5000,
			20.0F, 7.0F, 22);
	public static ToolMaterial MOSS = EnumHelper.addToolMaterial("MOSS", 4, 4096,
			19.0F, 8.0F, 20);
	public static ToolMaterial BC2 = EnumHelper.addToolMaterial("BC2", 5, 7500,
			23.0F, 10.0F, 25);
	
	public static Item bone1;
	public static Item bone2;
	public static Item bone3;
	public static Item bone4;
	public static Item bone_core1;
	public static Item bone5;
	public static Item bone6;
	public static Item bone7;
	public static Item bone8;
	public static Item bone_core2;
	public static Item bone_key1;
	
	public static Item spookyscaryskeletons;
	
	public static Block bone_box;

	public static Item fire_sword;

	public static Item vorpal_sword;

	public static Item bc1_sword;
	public static Item bc1_pickaxe;
	public static Item bc1_axe;
	public static Item bc1_spade;
	
	public static Item moss_sword;
	
	public static Item bc2_sword;
	public static Item bc2_pickaxe;
	public static Item bc2_axe;
	public static Item bc2_spade;

	@EventHandler
	public void init(FMLInitializationEvent event) {

		Enchantment.addToBookList(haste);
		MinecraftForge.EVENT_BUS.register(haste);

		Enchantment.addToBookList(poison);
		MinecraftForge.EVENT_BUS.register(poison);
		
		Enchantment.addToBookList(bones);
		MinecraftForge.EVENT_BUS.register(bones);
		
		MinecraftForge.EVENT_BUS.register(new EventHandlers());

		bone1 = new GenericItem("bone1");
		GameRegistry.registerItem(bone1, "bone1");
		Item modelItemBone1 = GameRegistry.findItem("spooky", "bone1");
		ModelResourceLocation modelBone1 = new ModelResourceLocation(
				"spooky:bone1", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone1, 0, modelBone1);

		bone2 = new GenericItem("bone2");
		GameRegistry.registerItem(bone2, "bone2");
		Item modelItemBone2 = GameRegistry.findItem("spooky", "bone2");
		ModelResourceLocation modelBone2 = new ModelResourceLocation(
				"spooky:bone2", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone2, 0, modelBone2);

		bone3 = new GenericItem("bone3");
		GameRegistry.registerItem(bone3, "bone3");
		Item modelItemBone3 = GameRegistry.findItem("spooky", "bone3");
		ModelResourceLocation modelBone3 = new ModelResourceLocation(
				"spooky:bone3", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone3, 0, modelBone3);

		bone4 = new GenericItem("bone4");
		GameRegistry.registerItem(bone4, "bone4");
		Item modelItemBone4 = GameRegistry.findItem("spooky", "bone4");
		ModelResourceLocation modelBone4 = new ModelResourceLocation(
				"spooky:bone4", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone4, 0, modelBone4);

		bone_core1 = new GenericItem("bone_core1");
		GameRegistry.registerItem(bone_core1, "bone_core1");
		Item modelItemBone_Core1 = GameRegistry
				.findItem("spooky", "bone_core1");
		ModelResourceLocation modelBone_Core1 = new ModelResourceLocation(
				"spooky:bone_core1", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone_Core1, 0, modelBone_Core1);

		fire_sword = new GenericSword("fire_sword", HELL);
		GameRegistry.registerItem(fire_sword, "fire_sword");
		Item modelItemFire_Sword = GameRegistry
				.findItem("spooky", "fire_sword");
		ModelResourceLocation modelFire_Sword = new ModelResourceLocation(
				"spooky:fire_sword", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemFire_Sword, 0, modelFire_Sword);

		vorpal_sword = new GenericSword("vorpal_sword", ENDER);
		GameRegistry.registerItem(vorpal_sword, "vorpal_sword");
		Item modelItemVorpal_Sword = GameRegistry.findItem("spooky",
				"vorpal_sword");
		ModelResourceLocation modelVorpal_Sword = new ModelResourceLocation(
				"spooky:vorpal_sword", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemVorpal_Sword, 0, modelVorpal_Sword);

		bc1_sword = new GenericSword("bc1_sword", BC1);
		GameRegistry.registerItem(bc1_sword, "bc1_sword");
		Item modelItemBC1_Sword = GameRegistry.findItem("spooky", "bc1_sword");
		ModelResourceLocation modelBC1_Sword = new ModelResourceLocation(
				"spooky:bc1_sword", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC1_Sword, 0, modelBC1_Sword);

		bc1_pickaxe = new GenericPickaxe("bc1_pickaxe", BC1);
		GameRegistry.registerItem(bc1_pickaxe, "bc1_pickaxe");
		Item modelItemBC1_Pickaxe = GameRegistry.findItem("spooky",
				"bc1_pickaxe");
		ModelResourceLocation modelBC1_Pickaxe = new ModelResourceLocation(
				"spooky:bc1_pickaxe", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC1_Pickaxe, 0, modelBC1_Pickaxe);

		bc1_axe = new GenericAxe("bc1_axe", BC1);
		GameRegistry.registerItem(bc1_axe, "bc1_axe");
		Item modelItemBC1_Axe = GameRegistry.findItem("spooky", "bc1_axe");
		ModelResourceLocation modelBC1_Axe = new ModelResourceLocation(
				"spooky:bc1_axe", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC1_Axe, 0, modelBC1_Axe);

		bc1_spade = new GenericSpade("bc1_spade", BC1);
		GameRegistry.registerItem(bc1_spade, "bc1_spade");
		Item modelItemBC1_Spade = GameRegistry.findItem("spooky", "bc1_spade");
		ModelResourceLocation modelBC1_Spade = new ModelResourceLocation(
				"spooky:bc1_spade", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC1_Spade, 0, modelBC1_Spade);

		bone_box = new GenericBlock("bone_box", Material.rock, 50.0F, 2000.0F,
				"pickaxe", 4, Block.soundTypePiston);
		GameRegistry.registerBlock(bone_box, "bone_box");
		Item modelItemBone_Box = GameRegistry.findItem("spooky", "bone_box");
		ModelResourceLocation modelBone_Box = new ModelResourceLocation(
				"spooky:bone_box", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone_Box, 0, modelBone_Box);
		
		bone5 = new GenericItem("bone5");
		GameRegistry.registerItem(bone5, "bone5");
		Item modelItemBone5 = GameRegistry.findItem("spooky", "bone5");
		ModelResourceLocation modelBone5 = new ModelResourceLocation(
				"spooky:bone5", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone5, 0, modelBone5);
		
		bone6 = new GenericItem("bone6");
		GameRegistry.registerItem(bone6, "bone6");
		Item modelItemBone6 = GameRegistry.findItem("spooky", "bone6");
		ModelResourceLocation modelBone6 = new ModelResourceLocation(
				"spooky:bone6", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone6, 0, modelBone6);
		
		bone7 = new GenericItem("bone7");
		GameRegistry.registerItem(bone7, "bone7");
		Item modelItemBone7 = GameRegistry.findItem("spooky", "bone7");
		ModelResourceLocation modelBone7 = new ModelResourceLocation(
				"spooky:bone7", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone7, 0, modelBone7);
		
		bone8 = new GenericItem("bone8");
		GameRegistry.registerItem(bone8, "bone8");
		Item modelItemBone8 = GameRegistry.findItem("spooky", "bone8");
		ModelResourceLocation modelBone8 = new ModelResourceLocation(
				"spooky:bone8", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone8, 0, modelBone8);
		
		bone_core2 = new GenericItem("bone_core2");
		GameRegistry.registerItem(bone_core2, "bone_core2");
		Item modelItemBone_Core2 = GameRegistry
				.findItem("spooky", "bone_core2");
		ModelResourceLocation modelBone_Core2 = new ModelResourceLocation(
				"spooky:bone_core2", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone_Core2, 0, modelBone_Core2);
		
		moss_sword = new GenericSword("moss_sword", MOSS);
		GameRegistry.registerItem(moss_sword, "moss_sword");
		Item modelItemMoss_Sword = GameRegistry.findItem("spooky",
				"moss_sword");
		ModelResourceLocation modelMoss_Sword = new ModelResourceLocation(
				"spooky:moss_sword", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemMoss_Sword, 0, modelMoss_Sword);
		
		bc2_sword = new GenericSword("bc2_sword", BC2);
		GameRegistry.registerItem(bc2_sword, "bc2_sword");
		Item modelItemBC2_Sword = GameRegistry.findItem("spooky", "bc2_sword");
		ModelResourceLocation modelBC2_Sword = new ModelResourceLocation(
				"spooky:bc2_sword", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC2_Sword, 0, modelBC2_Sword);

		bc2_pickaxe = new GenericPickaxe("bc2_pickaxe", BC2);
		GameRegistry.registerItem(bc2_pickaxe, "bc2_pickaxe");
		Item modelItemBC2_Pickaxe = GameRegistry.findItem("spooky",
				"bc2_pickaxe");
		ModelResourceLocation modelBC2_Pickaxe = new ModelResourceLocation(
				"spooky:bc2_pickaxe", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC2_Pickaxe, 0, modelBC2_Pickaxe);

		bc2_axe = new GenericAxe("bc2_axe", BC2);
		GameRegistry.registerItem(bc2_axe, "bc2_axe");
		Item modelItemBC2_Axe = GameRegistry.findItem("spooky", "bc2_axe");
		ModelResourceLocation modelBC2_Axe = new ModelResourceLocation(
				"spooky:bc2_axe", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC2_Axe, 0, modelBC2_Axe);

		bc2_spade = new GenericSpade("bc2_spade", BC2);
		GameRegistry.registerItem(bc2_spade, "bc2_spade");
		Item modelItemBC2_Spade = GameRegistry.findItem("spooky", "bc2_spade");
		ModelResourceLocation modelBC2_Spade = new ModelResourceLocation(
				"spooky:bc2_spade", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBC2_Spade, 0, modelBC2_Spade);
		
		bone_key1 = new GenericItem("bone_key1");
		GameRegistry.registerItem(bone_key1, "bone_key1");
		Item modelItemBone_Key1 = GameRegistry
				.findItem("spooky", "bone_key1");
		ModelResourceLocation modelBone_Key1 = new ModelResourceLocation(
				"spooky:bone_key1", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemBone_Key1, 0, modelBone_Key1);
		
		spookyscaryskeletons = new GenericRecord("spookyscaryskeletons");
		GameRegistry.registerItem(spookyscaryskeletons, "spookyscaryskeletons");
		Item modelItemSpookyScarySkeletons = GameRegistry
				.findItem("spooky", "spookyscaryskeletons");
		ModelResourceLocation modelSpookyScarySkeletons = new ModelResourceLocation(
				"spooky:spookyscaryskeletons", "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(modelItemSpookyScarySkeletons, 0, modelSpookyScarySkeletons);
		
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(spookyscaryskeletons), 1, 1, 50));
		
		GameRegistry.registerWorldGenerator(new OreGenerator(), 0);

		GameRegistry.addRecipe(new ItemStack(bone1), "ggg", "gbg", "ggg", 'g',
				Items.gold_nugget, 'b', Items.bone);

		GameRegistry.addRecipe(new ItemStack(Items.golden_sword), "b", "b",
				"s", 'b', bone1, 's', Items.stick);

		GameRegistry.addRecipe(new ItemStack(bone2), "rer", "rbr", "rdr", 'r',
				Items.redstone, 'e', Items.emerald, 'd', Items.diamond, 'b',
				bone1);

		GameRegistry.addRecipe(new ItemStack(bone3), "flf", "BbB", "BgB", 'B',
				Items.blaze_rod, 'f', Items.fire_charge, 'l',
				Items.lava_bucket, 'g', Items.ghast_tear, 'b', Items.bone);

		GameRegistry.addRecipe(new ItemStack(bone4), "oeo", "ebe", "oeo", 'o',
				Blocks.obsidian, 'e', Items.ender_pearl, 'b', bone3);

		GameRegistry.addRecipe(new ItemStack(bone4), "eoe", "obo", "eoe", 'o',
				Blocks.obsidian, 'e', Items.ender_pearl, 'b', bone3);

		GameRegistry.addShapelessRecipe(new ItemStack(bone_core1),
				new ItemStack(bone1), new ItemStack(bone2),
				new ItemStack(bone3), new ItemStack(bone4), new ItemStack(
						Blocks.coal_block), new ItemStack(Blocks.coal_block),
				new ItemStack(Blocks.coal_block), new ItemStack(
						Blocks.coal_block), new ItemStack(Blocks.coal_block));

		GameRegistry.addRecipe(new ItemStack(Blocks.beacon), "ggg", "gcg",
				"eee", 'g', Blocks.glass, 'e', Blocks.end_stone, 'c',
				bone_core1);

		GameRegistry.addRecipe(new ItemStack(fire_sword), "b", "b", "s", 'b',
				bone3, 's', Items.blaze_rod);

		GameRegistry.addRecipe(new ItemStack(vorpal_sword), "b", "b", "B", 'b',
				bone4, 'B', bone3);

		GameRegistry.addRecipe(new ItemStack(bc1_sword), "c", "c", "b", 'b',
				bone4, 'c', bone_core1);

		GameRegistry.addRecipe(new ItemStack(bc1_pickaxe), "ccc", " b ", " b ",
				'b', bone4, 'c', bone_core1);

		GameRegistry.addRecipe(new ItemStack(bc1_axe), "cc", "cb", " b", 'b',
				bone4, 'c', bone_core1);

		GameRegistry.addRecipe(new ItemStack(bc1_axe), "cc", "bc", "b ", 'b',
				bone4, 'c', bone_core1);

		GameRegistry.addRecipe(new ItemStack(bc1_spade), "c", "b", "b", 'b',
				bone4, 'c', bone_core1);
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 32),
				new ItemStack(bone_box));
		
		GameRegistry.addRecipe(new ItemStack(bone5), "ttt", "tbt", "ttt", 'b',
				bone_box, 't', Blocks.tnt);
		
		GameRegistry.addRecipe(new ItemStack(bone6), "cvc", "vbv", "cvc", 'b',
				bone_box, 'c', Blocks.mossy_cobblestone, 'v', Blocks.vine);
		
		GameRegistry.addRecipe(new ItemStack(bone6), "vcv", "cbc", "vcv", 'b',
				bone_box, 'c', Blocks.mossy_cobblestone, 'v', Blocks.vine);
		
		GameRegistry.addRecipe(new ItemStack(bone7), "ddd", "dbd", "ddd", 'b',
				bone_box, 'd', Blocks.dirt);
		
		GameRegistry.addRecipe(new ItemStack(bone8), "pbp", "bBb", "pbp", 'b',
				bone_box, 'p', Items.cooked_porkchop, 'B', Blocks.beacon);
		
		GameRegistry.addRecipe(new ItemStack(bone8), "bpb", "pBp", "bpb", 'b',
				bone_box, 'p', Items.cooked_porkchop, 'B', Blocks.beacon);
		
		GameRegistry.addShapelessRecipe(new ItemStack(bone_core2),
				new ItemStack(bone5), new ItemStack(bone6),
				new ItemStack(bone7), new ItemStack(bone8), new ItemStack(
						Blocks.redstone_block), new ItemStack(Blocks.redstone_block),
				new ItemStack(Blocks.redstone_block), new ItemStack(
						Blocks.redstone_block), new ItemStack(Blocks.redstone_block));
		
		GameRegistry.addRecipe(new ItemStack(moss_sword), "b", "b", "s", 's',
				Items.stick, 'b', bone6);
		
		GameRegistry.addRecipe(new ItemStack(bc2_sword), "c", "c", "b", 'b',
				bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(bc2_pickaxe), "ccc", " b ", " b ",
				'b', bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(bc2_axe), "cc", "cb", " b", 'b',
				bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(bc2_axe), "cc", "bc", "b ", 'b',
				bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(bc2_spade), "c", "b", "b", 'b',
				bone8, 'c', bone_core2);
		
		GameRegistry.addRecipe(new ItemStack(bone_key1), "bb ", " b ", "cbc", 'b',
				bone8, 'c', bone_core2);
		
		GameRegistry.addRecipe(new ItemStack(bone_key1), " bb", " b ", "cbc", 'b',
				bone8, 'c', bone_core2);
	}

}
