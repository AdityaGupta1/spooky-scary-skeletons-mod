package org.redfrog404.spooky.scary.skeletons.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.redfrog404.spooky.scary.skeletons.armor.GenericArmor;
import org.redfrog404.spooky.scary.skeletons.biomes.BiomeRegistry;
import org.redfrog404.spooky.scary.skeletons.creativetab.ArmorTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.BlocksTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.BowsTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.FoodTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.GunsTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.MaterialsTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.MiscTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.ToolsTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.WeaponsTab;
import org.redfrog404.spooky.scary.skeletons.dimensions.DimensionGateway;
import org.redfrog404.spooky.scary.skeletons.dimensions.DimensionRegistry;
import org.redfrog404.spooky.scary.skeletons.enchantments.EnchantmentArrowFast;
import org.redfrog404.spooky.scary.skeletons.enchantments.EnchantmentBones;
import org.redfrog404.spooky.scary.skeletons.enchantments.EnchantmentPoison;
import org.redfrog404.spooky.scary.skeletons.entity.EntityJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.EntitySkeletonCow;
import org.redfrog404.spooky.scary.skeletons.entity.ModelJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.ModelSkeletonCow;
import org.redfrog404.spooky.scary.skeletons.entity.RenderJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.RenderSkeletonCow;
import org.redfrog404.spooky.scary.skeletons.guns.GenericGun;
import org.redfrog404.spooky.scary.skeletons.tools.GenericAxe;
import org.redfrog404.spooky.scary.skeletons.tools.GenericBow;
import org.redfrog404.spooky.scary.skeletons.tools.GenericPickaxe;
import org.redfrog404.spooky.scary.skeletons.tools.GenericSpade;
import org.redfrog404.spooky.scary.skeletons.tools.GenericSword;

@Mod(modid = Spooky.MODID, version = Spooky.VERSION)
public class Spooky {

	/*
	 * ========================================================================================================================================================================
	 * Enchantments, Creative Tabs, Tool/Armor Materials, Misc.
	 * ========================================================================================================================================================================
	 */

	public static final String MODID = "Spooky";
	public static final String VERSION = "2.1.0";

	private int modEntityId = 163;

	public static final List<String> spooky_text = new ArrayList();

	ItemModelMesher mesher;

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
	public static final CreativeTabs bows = new BowsTab(
			CreativeTabs.getNextID(), "bowsTab");
	public static final CreativeTabs guns = new GunsTab(
			CreativeTabs.getNextID(), "gunsTab");
	public static final CreativeTabs armor = new ArmorTab(
			CreativeTabs.getNextID(), "armorTab");
	public static final CreativeTabs misc = new MiscTab(
			CreativeTabs.getNextID(), "miscTab");
	public static final CreativeTabs food = new FoodTab(
			CreativeTabs.getNextID(), "foodTab");

	public static ToolMaterial HELL = EnumHelper.addToolMaterial("HELL", 3,
			3122, 15.0F, 4.0F, 15);
	public static ToolMaterial ENDER = EnumHelper.addToolMaterial("ENDER", 4,
			3122, 17.5F, 6.0F, 20);
	public static ToolMaterial BC1 = EnumHelper.addToolMaterial("BC1", 4, 5000,
			20.0F, 7.0F, 22);
	public static ToolMaterial MOSS = EnumHelper.addToolMaterial("MOSS", 4,
			4096, 19.0F, 8.0F, 20);
	public static ToolMaterial BC2 = EnumHelper.addToolMaterial("BC2", 5, 7500,
			23.0F, 10.0F, 25);

	public static ArmorMaterial MOSSARMOR = EnumHelper.addArmorMaterial(
			"MOSSARMOR", "spooky:moss_armor", 20, new int[] { 5, 6, 4, 5 }, 18);

	/*
	 * ========================================================================================================================================================================
	 * Item/Block Variables
	 * ========================================================================================================================================================================
	 */

	// Bones, Bone Cores, and Bone Keys
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

	// Blocks
	public static Block bone_box;
	public static Block bone_ore;
	public static Block dimension_gateway;
	public static Block dim8_ore;

	// Misc.ellaneous
	public static Item spookyscaryskeletons;
	public static Item guardians_eye;
	public static Item bone_marrow;
	public static Item gray_gel;

	// Tools and Swords
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

	// Armor
	public static Item moss_helmet;
	public static Item moss_chestplate;
	public static Item moss_leggings;
	public static Item moss_boots;

	// Guns and Bullets
	public static GenericGun prismarine_pistol;
	public static GenericGun fire_gun;
	public static GenericGun ender_rifle;
	public static GenericGun steampunk_gun;
	public static Item compressed_redstone;

	// Bows and Arrows
	public static GenericBow double_bow;
	public static GenericBow ender_bow;
	public static Item ender_arrow;

	// Potions
	public static Potion curse = new GenericPotion(26, new ResourceLocation(
			"curse"), true, 0).setIconIndex(7, 1).setPotionName("potion.curse");

	/*
	 * ========================================================================================================================================================================
	 * Potion Reflection
	 * ========================================================================================================================================================================
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Potion[] potionTypes = null;

		for (Field f : Potion.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getName().equals("potionTypes")
						|| f.getName().equals("field_76425_a")) {
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

					potionTypes = (Potion[]) f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0,
							potionTypes.length);
					f.set(null, newPotionTypes);
				}
			} catch (Exception e) {
				System.err
						.println("Severe error, please report this to the mod author:");
				System.err.println(e);
			}
		}
	}

	/*
	 * ========================================================================================================================================================================
	 * Initialization
	 * ========================================================================================================================================================================
	 */

	@EventHandler
	public void init(FMLInitializationEvent event) {

		doMiscStuff1();

		registerItems();

		registerBlocks();

		registerSwordsToolsAndArmor();

		registerBowsAndGuns(event);

		registerMobs();

		doMiscStuff2();

	}

	/*
	 * ========================================================================================================================================================================
	 * Items
	 * ========================================================================================================================================================================
	 */

	private void registerItems() {

		bone1 = new GenericItem("bone1");
		GameRegistry.registerItem(bone1, "bone1");
		mesher.register(bone1, 0, new ModelResourceLocation("spooky:bone1",
				"inventory"));

		bone2 = new GenericItem("bone2");
		GameRegistry.registerItem(bone2, "bone2");
		mesher.register(bone2, 0, new ModelResourceLocation("spooky:bone2",
				"inventory"));

		bone3 = new GenericItem("bone3");
		GameRegistry.registerItem(bone3, "bone3");
		mesher.register(bone3, 0, new ModelResourceLocation("spooky:bone3",
				"inventory"));

		bone4 = new GenericItem("bone4");
		GameRegistry.registerItem(bone4, "bone4");
		mesher.register(bone4, 0, new ModelResourceLocation("spooky:bone4",
				"inventory"));

		bone_core1 = new GenericItem("bone_core1");
		GameRegistry.registerItem(bone_core1, "bone_core1");
		mesher.register(bone_core1, 0, new ModelResourceLocation(
				"spooky:bone_core1", "inventory"));

		bone5 = new GenericItem("bone5");
		GameRegistry.registerItem(bone5, "bone5");
		mesher.register(bone5, 0, new ModelResourceLocation("spooky:bone5",
				"inventory"));

		bone6 = new GenericItem("bone6");
		GameRegistry.registerItem(bone6, "bone6");
		mesher.register(bone6, 0, new ModelResourceLocation("spooky:bone6",
				"inventory"));

		bone7 = new GenericItem("bone7");
		GameRegistry.registerItem(bone7, "bone7");
		mesher.register(bone7, 0, new ModelResourceLocation("spooky:bone7",
				"inventory"));

		bone8 = new GenericItem("bone8");
		GameRegistry.registerItem(bone8, "bone8");
		mesher.register(bone8, 0, new ModelResourceLocation("spooky:bone8",
				"inventory"));

		bone_core2 = new GenericItem("bone_core2");
		GameRegistry.registerItem(bone_core2, "bone_core2");
		mesher.register(bone_core2, 0, new ModelResourceLocation(
				"spooky:bone_core2", "inventory"));

		guardians_eye = new GenericItem("guardians_eye", misc);
		GameRegistry.registerItem(guardians_eye, "guardians_eye");
		mesher.register(guardians_eye, 0, new ModelResourceLocation(
				"spooky:guardians_eye", "inventory"));

		bone_key1 = new GenericItem("bone_key1").setMaxStackSize(1);
		GameRegistry.registerItem(bone_key1, "bone_key1");
		mesher.register(bone_key1, 0, new ModelResourceLocation(
				"spooky:bone_key1", "inventory"));

		spookyscaryskeletons = new GenericRecord("spookyscaryskeletons");
		GameRegistry.registerItem(spookyscaryskeletons, "spookyscaryskeletons");
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						spookyscaryskeletons,
						0,
						new ModelResourceLocation(
								"spooky:spookyscaryskeletons", "inventory"));

		bone_marrow = new GenericFoodItem("bone_marrow", 1, 0.3F, true)
				.setPotionEffect(Potion.hunger.id, 6, 1, 0.25F);
		GameRegistry.registerItem(bone_marrow, "bone_marrow");
		mesher.register(bone_marrow, 0, new ModelResourceLocation(
				"spooky:bone_marrow", "inventory"));

		gray_gel = new GenericItem("gray_gel", misc);
		GameRegistry.registerItem(gray_gel, "gray_gel");
		mesher.register(gray_gel, 0, new ModelResourceLocation(
				"spooky:gray_gel", "inventory"));

	}

	/*
	 * ========================================================================================================================================================================
	 * Blocks
	 * ========================================================================================================================================================================
	 */

	private void registerBlocks() {
		bone_box = new GenericBlock("bone_box", Material.rock, 50.0F, 100.0F,
				"pickaxe", 4, Block.soundTypePiston);
		GameRegistry.registerBlock(bone_box, "bone_box");
		mesher.register(Item.getItemFromBlock(bone_box), 0,
				new ModelResourceLocation("spooky:bone_box", "inventory"));

		bone_ore = new GenericBlock("bone_ore", Material.rock, 25.0F, 20.0F,
				"pickaxe", 2, Block.soundTypePiston, Items.bone, 2, 2);
		GameRegistry.registerBlock(bone_ore, "bone_ore");
		mesher.register(Item.getItemFromBlock(bone_ore), 0,
				new ModelResourceLocation("spooky:bone_ore", "inventory"));

		dimension_gateway = new DimensionGateway("dimension_gateway",
				Material.rock, 50, 2000, "pickaxe", 4, Block.soundTypePiston);
		GameRegistry.registerBlock(dimension_gateway, "dimension_gateway");
		mesher.register(Item.getItemFromBlock(dimension_gateway), 0,
				new ModelResourceLocation("spooky:dimension_gateway",
						"inventory"));

		dim8_ore = new GenericBlock("dim8_ore", Material.rock, 50, 100,
				"pickaxe", 5, Block.soundTypePiston);
		GameRegistry.registerBlock(dim8_ore, "dim8_ore");
		mesher.register(Item.getItemFromBlock(dim8_ore), 0,
				new ModelResourceLocation("spooky:dim8_ore", "inventory"));
	}

	/*
	 * ========================================================================================================================================================================
	 * Swords, Tools, and Armor
	 * ========================================================================================================================================================================
	 */

	private void registerSwordsToolsAndArmor() {
		fire_sword = new GenericSword("fire_sword", HELL);
		GameRegistry.registerItem(fire_sword, "fire_sword");
		mesher.register(fire_sword, 0, new ModelResourceLocation(
				"spooky:fire_sword", "inventory"));

		vorpal_sword = new GenericSword("vorpal_sword", ENDER);
		GameRegistry.registerItem(vorpal_sword, "vorpal_sword");
		mesher.register(vorpal_sword, 0, new ModelResourceLocation(
				"spooky:vorpal_sword", "inventory"));

		bc1_sword = new GenericSword("bc1_sword", BC1);
		GameRegistry.registerItem(bc1_sword, "bc1_sword");
		mesher.register(bc1_sword, 0, new ModelResourceLocation(
				"spooky:bc1_sword", "inventory"));

		bc1_pickaxe = new GenericPickaxe("bc1_pickaxe", BC1);
		GameRegistry.registerItem(bc1_pickaxe, "bc1_pickaxe");
		mesher.register(bc1_pickaxe, 0, new ModelResourceLocation(
				"spooky:bc1_pickaxe", "inventory"));

		bc1_axe = new GenericAxe("bc1_axe", BC1);
		GameRegistry.registerItem(bc1_axe, "bc1_axe");
		mesher.register(bc1_axe, 0, new ModelResourceLocation("spooky:bc1_axe",
				"inventory"));

		bc1_spade = new GenericSpade("bc1_spade", BC1);
		GameRegistry.registerItem(bc1_spade, "bc1_spade");
		mesher.register(bc1_spade, 0, new ModelResourceLocation(
				"spooky:bc1_spade", "inventory"));

		moss_sword = new GenericSword("moss_sword", MOSS);
		GameRegistry.registerItem(moss_sword, "moss_sword");
		mesher.register(moss_sword, 0, new ModelResourceLocation(
				"spooky:moss_sword", "inventory"));

		bc2_sword = new GenericSword("bc2_sword", BC2);
		GameRegistry.registerItem(bc2_sword, "bc2_sword");
		mesher.register(bc2_sword, 0, new ModelResourceLocation(
				"spooky:bc2_sword", "inventory"));

		bc2_pickaxe = new GenericPickaxe("bc2_pickaxe", BC2);
		GameRegistry.registerItem(bc2_pickaxe, "bc2_pickaxe");
		mesher.register(bc2_pickaxe, 0, new ModelResourceLocation(
				"spooky:bc2_pickaxe", "inventory"));

		bc2_axe = new GenericAxe("bc2_axe", BC2);
		GameRegistry.registerItem(bc2_axe, "bc2_axe");
		mesher.register(bc2_axe, 0, new ModelResourceLocation("spooky:bc2_axe",
				"inventory"));

		bc2_spade = new GenericSpade("bc2_spade", BC2);
		GameRegistry.registerItem(bc2_spade, "bc2_spade");
		mesher.register(bc2_spade, 0, new ModelResourceLocation(
				"spooky:bc2_spade", "inventory"));

		GameRegistry.registerItem(moss_helmet = new GenericArmor("moss_helmet",
				MOSSARMOR, 1, 0, "moss"), "moss_helmet");
		mesher.register(moss_helmet, 0, new ModelResourceLocation(
				"spooky:moss_helmet", "inventory"));

		GameRegistry.registerItem(moss_chestplate = new GenericArmor(
				"moss_chestplate", MOSSARMOR, 1, 1, "moss"), "moss_chestplate");
		mesher.register(moss_chestplate, 0, new ModelResourceLocation(
				"spooky:moss_chestplate", "inventory"));

		GameRegistry.registerItem(moss_leggings = new GenericArmor(
				"moss_leggings", MOSSARMOR, 2, 2, "moss"), "moss_leggings");
		mesher.register(moss_leggings, 0, new ModelResourceLocation(
				"spooky:moss_leggings", "inventory"));

		GameRegistry.registerItem(moss_boots = new GenericArmor("moss_boots",
				MOSSARMOR, 1, 3, "moss"), "moss_boots");
		mesher.register(moss_boots, 0, new ModelResourceLocation(
				"spooky:moss_boots", "inventory"));
	}
	
	/*
	 * ========================================================================================================================================================================
	 * Bows and Guns
	 * ========================================================================================================================================================================
	 */

	private void registerBowsAndGuns(FMLInitializationEvent event) {
		prismarine_pistol = new GenericGun("prismarine_pistol", 1234,
				Items.prismarine_shard, (byte) 6);
		GameRegistry.registerItem(prismarine_pistol, "prismarine_pistol");
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						prismarine_pistol,
						0,
						new ModelResourceLocation("spooky:prismarine_pistol",
								"inventory"));

		fire_gun = new GenericGun("fire_gun", 666, Items.fire_charge, (byte) 9);
		GameRegistry.registerItem(fire_gun, "fire_gun");
		mesher.register(fire_gun, 0, new ModelResourceLocation(
				"spooky:fire_gun", "inventory"));

		ender_rifle = new GenericGun("ender_rifle", 888, Items.ender_pearl,
				(byte) 11);
		GameRegistry.registerItem(ender_rifle, "ender_rifle");
		mesher.register(ender_rifle, 0, new ModelResourceLocation(
				"spooky:ender_rifle", "inventory"));

		double_bow = new GenericBow(double_bow, "double_bow", 512, Items.arrow,
				1.0D, 2);
		GameRegistry.registerItem(double_bow, "double_bow");

		if (event.getSide().isClient()) {
			ModelBakery.addVariantName(double_bow, new String[] {
					"spooky:double_bow", "spooky:double_bow_0",
					"spooky:double_bow_1", "spooky:double_bow_2" });
			mesher.register(double_bow, 0, new ModelResourceLocation(
					"spooky:double_bow", "inventory"));
			mesher.register(double_bow, 1, new ModelResourceLocation(
					"spooky:double_bow_0", "inventory"));
			mesher.register(double_bow, 2, new ModelResourceLocation(
					"spooky:double_bow_1", "inventory"));
			mesher.register(double_bow, 3, new ModelResourceLocation(
					"spooky:double_bow_2", "inventory"));
		}

		ender_arrow = new GenericItem("ender_arrow", bows);

		ender_bow = new GenericBow(ender_bow, "ender_bow", 999, ender_arrow,
				2.0D);
		GameRegistry.registerItem(ender_bow, "ender_bow");

		GameRegistry.registerItem(ender_arrow, "ender_arrow");
		mesher.register(ender_arrow, 0, new ModelResourceLocation(
				"spooky:ender_arrow", "inventory"));

		if (event.getSide().isClient()) {
			ModelBakery.addVariantName(ender_bow, new String[] {
					"spooky:ender_bow", "spooky:ender_bow_0",
					"spooky:ender_bow_1", "spooky:ender_bow_2" });
			mesher.register(ender_bow, 0, new ModelResourceLocation(
					"spooky:ender_bow", "inventory"));
			mesher.register(ender_bow, 1, new ModelResourceLocation(
					"spooky:ender_bow_0", "inventory"));
			mesher.register(ender_bow, 2, new ModelResourceLocation(
					"spooky:ender_bow_1", "inventory"));
			mesher.register(ender_bow, 3, new ModelResourceLocation(
					"spooky:ender_bow_2", "inventory"));
		}

		compressed_redstone = new GenericItem("compressed_redstone", guns);

		steampunk_gun = new GenericGun("steampunk_gun", 1280,
				compressed_redstone, (byte) 10, 4);
		GameRegistry.registerItem(steampunk_gun, "steampunk_gun");
		mesher.register(steampunk_gun, 0, new ModelResourceLocation(
				"spooky:steampunk_gun", "inventory"));

		GameRegistry.registerItem(compressed_redstone, "compressed_redstone");
		mesher.register(compressed_redstone, 0, new ModelResourceLocation(
				"spooky:compressed_redstone", "inventory"));
	}

	/*
	 * ========================================================================================================================================================================
	 * Recipes
	 * ========================================================================================================================================================================
	 */

	private void addRecipes() {
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
						Blocks.redstone_block), new ItemStack(
						Blocks.redstone_block), new ItemStack(
						Blocks.redstone_block), new ItemStack(
						Blocks.redstone_block), new ItemStack(
						Blocks.redstone_block));

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

		GameRegistry.addRecipe(new ItemStack(bone_key1), "bb ", " b ", "cbc",
				'b', bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(bone_key1), " bb", " b ", "cbc",
				'b', bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(moss_helmet), "bcb", "b b", 'b',
				bone6, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(moss_chestplate), "c c", "bcb",
				"bbb", 'b', bone6, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(moss_leggings), "bcb", "b b",
				"b b", 'b', bone6, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(moss_boots), "c c", "b b", 'b',
				bone6, 'c', bone_core2);

		ItemStack spooky_book = new ItemStack(Items.writable_book);
		NBTTagList bookPages = new NBTTagList();
		for (int page = 0; page < spooky_text.size(); page++) {
			bookPages.appendTag(new NBTTagString(spooky_text.get(page)));
		}
		spooky_book.setTagInfo("pages", bookPages);
		spooky_book.setTagInfo("author", new NBTTagString("Unknown"));
		spooky_book.setTagInfo("title", new NBTTagString(
				"Spooky Scary Skeletons"));
		spooky_book.setItem(Items.written_book);
		GameRegistry.addShapelessRecipe(spooky_book, new ItemStack(Items.bone),
				new ItemStack(Items.writable_book));

		GameRegistry.addShapelessRecipe(
				new ItemStack(Items.prismarine_shard, 4), new ItemStack(
						Blocks.prismarine));

		GameRegistry.addShapelessRecipe(
				new ItemStack(Items.prismarine_shard, 9), new ItemStack(
						Blocks.prismarine, 1, 1));

		GameRegistry.addShapelessRecipe(
				new ItemStack(Items.prismarine_shard, 8), new ItemStack(
						Blocks.prismarine, 1, 2));

		GameRegistry.addRecipe(new ItemStack(prismarine_pistol), "epp", "  d",
				'e', guardians_eye, 'p', Items.prismarine_shard, 'd',
				Items.diamond);

		GameRegistry.addRecipe(new ItemStack(ender_rifle), "eoo", " bb", 'e',
				Items.ender_eye, 'o', Blocks.end_stone, 'b', bone4);

		GameRegistry.addSmelting(bone4, new ItemStack(Items.ender_pearl, 6),
				0.1F);

		GameRegistry.addRecipe(new ItemStack(fire_gun), "pnn", " bb", 'p',
				Items.blaze_powder, 'n', Blocks.netherrack, 'b', bone3);

		GameRegistry.addRecipe(new ItemStack(ender_bow), "b b", " B ", "b b",
				'b', bone4, 'B', Items.bow);

		GameRegistry.addRecipe(new ItemStack(ender_arrow, 48), "o", "b", "f",
				'b', bone4, 'o', Blocks.obsidian, 'f', Items.feather);

		GameRegistry.addRecipe(new ItemStack(compressed_redstone, 16), "rrr",
				"rRr", "rrr", 'r', Items.redstone, 'R', Blocks.redstone_block);

		GameRegistry.addRecipe(new ItemStack(steampunk_gun), "hpr", " tr",
				"  t", 'r', Items.redstone, 'p', Blocks.piston, 'h',
				Blocks.hopper, 't', Blocks.redstone_torch);

		GameRegistry.addRecipe(new ItemStack(double_bow), "aba", "aea", "aba",
				'a', Items.arrow, 'b', Items.bow, 'e', Items.ender_eye);

		GameRegistry.addRecipe(new ItemStack(bone_marrow, 16), "bbb", "bBb",
				"bbb", 'B', Items.bone, 'b', new ItemStack(Items.dye, 1, 15));

		GameRegistry.addRecipe(new ItemStack(dimension_gateway), "bbb", "bBb",
				"bbb", 'b', bone_box, 'B', bone4);

		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 8),
				new ItemStack(gray_gel, 1), new ItemStack(Items.dye, 1, 15));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 8),
				new ItemStack(gray_gel, 1), new ItemStack(Items.dye, 1, 15));

		GameRegistry.addRecipe(new ItemStack(spookyscaryskeletons), "ctc",
				"tbt", "ctc", 'b', bone_box, 'c', Items.record_cat, 't',
				Items.record_13);

		GameRegistry.addRecipe(new ItemStack(spookyscaryskeletons), "tct",
				"cbc", "tct", 'b', bone_box, 'c', Items.record_cat, 't',
				Items.record_13);
	}

	/*
	 * ========================================================================================================================================================================
	 * Mobs
	 * ========================================================================================================================================================================
	 */

	private void registerMobs() {
		RenderingRegistry.registerEntityRenderingHandler(
				EntitySkeletonCow.class, new RenderSkeletonCow(
						new ModelSkeletonCow(), 0.7F));
		registerModEntity(EntitySkeletonCow.class, "skeletoncow", modEntityId++);

		RenderingRegistry.registerEntityRenderingHandler(
				EntityJellySkull.class, new RenderJellySkull(
						new ModelJellySkull(16), 0.7F));
		registerModEntity(EntityJellySkull.class, "jellyskull", modEntityId++);
	}

	/*
	 * ========================================================================================================================================================================
	 * Misc. Methods
	 * ========================================================================================================================================================================
	 */

	public void registerModEntity(Class parEntityClass, String parEntityName,
			int entityId) {
		EntityRegistry.registerModEntity(parEntityClass, parEntityName,
				entityId, this, 80, 1, false);
	}

	private void doMiscStuff1() {
		mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		spooky_text
				.add("Spooky, scary skeletons, send shivers down your spine. Shrieking skulls will shock your soul, seal your doom tonight.");
		spooky_text
				.add("Spooky, scary skeletons speak with such a screech. You'll shake and shudder in surprise when you hear these zombies shriek.");
		spooky_text
				.add("We're so sorry, skeletons, you're so misunderstood. You only want to socialize, (but I don't think we should!)");
		spooky_text
				.add("'Cause spooky, scary skeletons shout startling, shrilly screams. They'll sneak from their sarcophagus and just won't let you be.");
		spooky_text
				.add("Spirits supernatural are shy, what's all the fuss? But bags of bones seem so unsafe, it's semi-serious!");
		spooky_text
				.add("Spooky, scary skeletons are silly all the same. They'll smile and scrabble slowly by and drive you so insane!");
		spooky_text
				.add("Sticks and stones will break your bones; they seldom let you snooze. Spooky, scary skeletons will wake you with a BOO!");

		GameRegistry.registerFuelHandler(new FuelHandler());

		Enchantment.addToBookList(haste);
		MinecraftForge.EVENT_BUS.register(haste);

		Enchantment.addToBookList(poison);
		MinecraftForge.EVENT_BUS.register(poison);

		Enchantment.addToBookList(bones);
		MinecraftForge.EVENT_BUS.register(bones);

		MinecraftForge.EVENT_BUS.register(new EventHandlers());
	}

	private void doMiscStuff2() {
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
				new WeightedRandomChestContent(new ItemStack(
						spookyscaryskeletons), 1, 1, 50));

		GameRegistry.registerWorldGenerator(new OreGenerator(), 0);

		DimensionRegistry.mainRegistry();
		BiomeRegistry.mainRegistry();

		addRecipes();
	}
}
