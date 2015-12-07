package org.redfrog404.spooky.scary.skeletons.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureType;
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
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
import org.redfrog404.spooky.scary.skeletons.creativetab.StavesTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.ToolsTab;
import org.redfrog404.spooky.scary.skeletons.creativetab.WeaponsTab;
import org.redfrog404.spooky.scary.skeletons.dimensions.DimensionGateway;
import org.redfrog404.spooky.scary.skeletons.dimensions.DimensionRegistry;
import org.redfrog404.spooky.scary.skeletons.enchantments.EnchantmentArrowFast;
import org.redfrog404.spooky.scary.skeletons.enchantments.EnchantmentBones;
import org.redfrog404.spooky.scary.skeletons.enchantments.EnchantmentPoison;
import org.redfrog404.spooky.scary.skeletons.entity.EntityFrost;
import org.redfrog404.spooky.scary.skeletons.entity.EntityFrostBall;
import org.redfrog404.spooky.scary.skeletons.entity.EntityIceGolem;
import org.redfrog404.spooky.scary.skeletons.entity.EntityIncinerator;
import org.redfrog404.spooky.scary.skeletons.entity.EntityJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.EntityJuggernaut;
import org.redfrog404.spooky.scary.skeletons.entity.EntityRisenDead;
import org.redfrog404.spooky.scary.skeletons.entity.EntitySkeletonCow;
import org.redfrog404.spooky.scary.skeletons.entity.RenderFrost;
import org.redfrog404.spooky.scary.skeletons.entity.RenderFrostBall;
import org.redfrog404.spooky.scary.skeletons.entity.RenderIceGolem;
import org.redfrog404.spooky.scary.skeletons.entity.RenderIncinerator;
import org.redfrog404.spooky.scary.skeletons.entity.RenderJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.RenderJuggernaut;
import org.redfrog404.spooky.scary.skeletons.entity.RenderRisenDead;
import org.redfrog404.spooky.scary.skeletons.entity.RenderSkeletonCow;
import org.redfrog404.spooky.scary.skeletons.guns.GenericGun;
import org.redfrog404.spooky.scary.skeletons.staves.FireStaff;
import org.redfrog404.spooky.scary.skeletons.staves.IncineratorStaff;
import org.redfrog404.spooky.scary.skeletons.tools.GenericAxe;
import org.redfrog404.spooky.scary.skeletons.tools.GenericBow;
import org.redfrog404.spooky.scary.skeletons.tools.GenericPickaxe;
import org.redfrog404.spooky.scary.skeletons.tools.GenericSpade;
import org.redfrog404.spooky.scary.skeletons.tools.GenericSword;

import com.google.common.collect.Maps;

@Mod(modid = Spooky.MODID, version = Spooky.VERSION)
public class Spooky {

	/*
	 * ========================================================================================================================================================================
	 * Enchantments, Creative Tabs, Tool/Armor Materials, Misc.
	 * ========================================================================================================================================================================
	 */

	public static final String MODID = "Spooky";
	public static final String VERSION = "2.3.0";

	public static final List<String> spooky_text = new ArrayList();

	ItemModelMesher mesher;

	public Map entityRenderMap = Maps.newHashMap();

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
	public static final CreativeTabs staves = new StavesTab(
			CreativeTabs.getNextID(), "stavesTab");
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
	public static ToolMaterial RADIOACTIVE = EnumHelper.addToolMaterial(
			"RADIOACTIVE", 5, 3000, 30.0F, 10.0F, 30);
	public static ToolMaterial BC3 = EnumHelper.addToolMaterial("BC3", 6, 8500,
			25.0F, 13.0F, 27);

	public static ArmorMaterial MOSSARMOR = EnumHelper.addArmorMaterial(
			"MOSSARMOR", "spooky:moss_armor", 20, new int[] { 5, 6, 4, 5 }, 18);

	public static ArmorMaterial SLIMEARMOR = EnumHelper.addArmorMaterial(
			"SLIMEARMOR", "spooky:slime_armor", 27, new int[] { 5, 6, 4, 5 },
			14);

	public static ArmorMaterial FIREARMOR = EnumHelper.addArmorMaterial(
			"FIREARMOR", "spooky:fire_armor", 35, new int[] { 5, 6, 4, 5 }, 18);

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
	public static Item bone9;
	public static Item bone10;
	public static Item bone11;
	public static Item bone12;
	public static Item bone_core3;
	public static Item bone_key2;

	// Blocks
	public static Block bone_box;
	public static Block bone_ore;
	public static Block dimension_gateway;
	public static Block dim8_ore;
	public static Block jade_ore;
	public static Block jade_block;
	public static Block indium_ore;
	public static Block zinc_ore;
	public static Block purplonium_ore;
	public static Block fire_block;
	public static Block zinc_block;
	public static Block indium_block;
	public static Block cadmium_block;
	public static Block purplonium_block;
	public static Block obsidiron_block;

	// Miscellaneous
	public static Item spookyscaryskeletons;
	public static Item guardians_eye;
	public static Item bone_marrow;
	public static Item gray_gel;
	public static Item cloth;
	public static Item jade_ingot;
	public static Item canopic_jar;
	public static Item indium_ingot;
	public static Item zinc_ingot;
	public static Item cadmium_dust;
	public static Item cadmium_ingot;
	public static Item purplonium_ingot;
	public static Item fire_amulet;
	public static Item fire_crystal;
	public static Item bone_ingot;
	public static Item fire_ingot;
	public static Item ice_ingot;
	public static Item ice_charge;
	public static Item ice_plate;
	public static Item obsidiron_ingot;
	public static Item obsidiron_stick;
	public static Item bedrock_shard;
	public static Item molten_essence;
	public static Item bedrockium_ingot;

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

	public static Item radioactive_sword;
	public static Item radioactive_pickaxe;
	public static Item radioactive_axe;
	public static Item radioactive_spade;

	public static Item bc3_sword;
	public static Item bc3_pickaxe;
	public static Item bc3_axe;
	public static Item bc3_spade;

	// Armor
	public static Item moss_helmet;
	public static Item moss_chestplate;
	public static Item moss_leggings;
	public static Item moss_boots;

	public static Item slime_helmet;
	public static Item slime_chestplate;
	public static Item slime_leggings;
	public static Item slime_boots;

	public static Item fire_helmet;
	public static Item fire_chestplate;
	public static Item fire_leggings;
	public static Item fire_boots;

	// Bows and Arrows
	public static GenericBow double_bow;
	public static GenericBow ender_bow;
	public static Item ender_arrow;
	public static GenericBow fire_bow;
	public static Item fire_arrow;

	// Guns and Bullets
	public static GenericGun prismarine_pistol;
	public static GenericGun fire_gun;
	public static GenericGun ender_rifle;
	public static GenericGun steampunk_gun;
	public static Item compressed_redstone;
	public static GenericGun incinerator_gun;
	public static Item fire_bullet;

	// Staves
	public static Item incinerator_summon;
	public static Item incinerator_staff;
	public static Item fire_staff;
	public static Item juggernaut_summon;

	// Potions
	public static Potion curse = new GenericPotion(26, new ResourceLocation(
			"curse"), true, 0).setIconIndex(7, 1).setPotionName("potion.curse");

	public static Potion radioactive = new GenericPotion(27,
			new ResourceLocation("radioactive"), true, 0).setIconIndex(6, 0)
			.setPotionName("potion.radioactive");

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

		preRegister();

		registerItems();

		registerBlocks();

		registerSwordsToolsAndArmor();

		registerBowsGunsAndStaves(event);

		registerMobs();

		postRegister();

	}

	/*
	 * ========================================================================================================================================================================
	 * Items
	 * ========================================================================================================================================================================
	 */

	private void registerItems() {

		registerBones();

		registerFoodItems();

		registerMiscItems();

	}

	private void registerBones() {

		bone1 = new GenericItem("bone1");
		registerItem(bone1, "bone1");

		bone2 = new GenericItem("bone2");
		registerItem(bone2, "bone2");

		bone3 = new GenericItem("bone3");
		registerItem(bone3, "bone3");

		bone4 = new GenericItem("bone4");
		registerItem(bone4, "bone4");

		bone_core1 = new GenericItem("bone_core1");
		registerItem(bone_core1, "bone_core1");

		bone5 = new GenericItem("bone5");
		registerItem(bone5, "bone5");

		bone6 = new GenericItem("bone6");
		registerItem(bone6, "bone6");

		bone7 = new GenericItem("bone7");
		registerItem(bone7, "bone7");

		bone8 = new GenericItem("bone8");
		registerItem(bone8, "bone8");

		bone_core2 = new GenericItem("bone_core2");
		registerItem(bone_core2, "bone_core2");

		bone_key1 = new GenericItem("bone_key1").setMaxStackSize(1);
		registerItem(bone_key1, "bone_key1");

		bone9 = new GenericItem("bone9");
		registerItem(bone9, "bone9");

		bone10 = new GenericItem("bone10");
		registerItem(bone10, "bone10");

		bone11 = new GenericItem("bone11");
		registerItem(bone11, "bone11");

		bone12 = new GenericItem("bone12");
		registerItem(bone12, "bone12");

		bone_core3 = new GenericItem("bone_core3");
		registerItem(bone_core3, "bone_core3");

		bone_key2 = new GenericItem("bone_key2").setMaxStackSize(1);
		registerItem(bone_key2, "bone_key2");

	}

	private void registerFoodItems() {

		bone_marrow = new GenericFoodItem("bone_marrow", 1, 0.3F, true)
				.setPotionEffect(Potion.hunger.id, 6, 1, 0.25F);
		registerItem(bone_marrow, "bone_marrow");

		gray_gel = new GenericFoodItem("gray_gel", 2, 0.7F, false)
				.setPotionEffect(Potion.jump.id, 15, 0, 0.4F);
		registerItem(gray_gel, "gray_gel");

	}

	private void registerMiscItems() {

		guardians_eye = new GenericItem("guardians_eye", misc);
		registerItem(guardians_eye, "guardians_eye");

		spookyscaryskeletons = new GenericRecord("spookyscaryskeletons");
		registerItem(spookyscaryskeletons, "spookyscaryskeletons");

		cloth = new GenericItem("cloth", misc);
		registerItem(cloth, "cloth");

		jade_ingot = new GenericItem("jade_ingot", misc);
		registerItem(jade_ingot, "jade_ingot");

		canopic_jar = new GenericItem("canopic_jar", misc);
		registerItem(canopic_jar, "canopic_jar");

		indium_ingot = new GenericItem("indium_ingot", misc);
		registerItem(indium_ingot, "indium_ingot");

		zinc_ingot = new GenericItem("zinc_ingot", misc);
		registerItem(zinc_ingot, "zinc_ingot");

		cadmium_dust = new GenericItem("cadmium_dust", misc);
		registerItem(cadmium_dust, "cadmium_dust");

		cadmium_ingot = new GenericItem("cadmium_ingot", misc);
		registerItem(cadmium_ingot, "cadmium_ingot");

		purplonium_ingot = new GenericItem("purplonium_ingot", misc);
		registerItem(purplonium_ingot, "purplonium_ingot");

		fire_crystal = new GenericItem("fire_crystal", misc);
		registerItem(fire_crystal, "fire_crystal");

		fire_amulet = new GenericItem("fire_amulet", misc).setMaxStackSize(1);
		registerItem(fire_amulet, "fire_amulet");

		bone_ingot = new GenericItem("bone_ingot", misc);
		registerItem(bone_ingot, "bone_ingot");

		incinerator_summon = new GenericItem("incinerator_summon", staves)
				.setMaxStackSize(1);
		registerItem(incinerator_summon, "incinerator_summon");

		fire_ingot = new GenericItem("fire_ingot", misc);
		registerItem(fire_ingot, "fire_ingot");

		ice_ingot = new GenericItem("ice_ingot", misc);
		registerItem(ice_ingot, "ice_ingot");

		ice_charge = new GenericItem("ice_charge", misc);
		registerItem(ice_charge, "ice_charge");

		ice_plate = new GenericItem("ice_plate", misc);
		registerItem(ice_plate, "ice_plate");

		obsidiron_ingot = new GenericItem("obsidiron_ingot", misc);
		registerItem(obsidiron_ingot, "obsidiron_ingot");

		obsidiron_stick = new GenericItem("obsidiron_stick", misc);
		registerItem(obsidiron_stick, "obsidiron_stick");

		juggernaut_summon = new GenericItem("juggernaut_summon", staves)
				.setMaxStackSize(1);
		registerItem(juggernaut_summon, "juggernaut_summon");
		
		bedrock_shard = new GenericItem("bedrock_shard", misc);
		registerItem(bedrock_shard, "bedrock_shard");
		
		molten_essence = new GenericItem("molten_essence", misc);
		registerItem(molten_essence, "molten_essence");
		
		bedrockium_ingot = new GenericItem("bedrockium_ingot", misc);
		registerItem(bedrockium_ingot, "bedrockium_ingot");
		
	}

	private void registerItem(Item item, String name) {
		GameRegistry.registerItem(item, name);
		mesher.register(item, 0, new ModelResourceLocation("spooky:" + name,
				"inventory"));
	}

	/*
	 * ========================================================================================================================================================================
	 * Blocks
	 * ========================================================================================================================================================================
	 */

	private void registerBlocks() {
		bone_box = new GenericBlock("bone_box", Material.rock, 50.0F, 100.0F,
				"pickaxe", 4, Block.soundTypePiston);
		registerBlock(bone_box, "bone_box");

		bone_ore = new GenericBlock("bone_ore", Material.rock, 25.0F, 20.0F,
				"pickaxe", 2, Block.soundTypePiston, Items.bone, 2, 2);
		registerBlock(bone_ore, "bone_ore");

		dimension_gateway = new DimensionGateway("dimension_gateway",
				Material.rock, 50, 2000, "pickaxe", 4, Block.soundTypePiston);
		registerBlock(dimension_gateway, "dimension_gateway");

		dim8_ore = new GenericBlock("dim8_ore", Material.rock, 50, 100,
				"pickaxe", 5, Block.soundTypePiston);
		registerBlock(dim8_ore, "dim8_ore");

		jade_ore = new GenericBlock("jade_ore", Material.rock, 12, 24,
				"pickaxe", 3, Block.soundTypePiston);
		registerBlock(jade_ore, "jade_ore");

		jade_block = new GenericBlock("jade_block", Material.rock, 15, 30,
				"pickaxe", 3, Block.soundTypeMetal);
		registerBlock(jade_block, "jade_block");

		indium_ore = new GenericBlock("indium_ore", Material.rock, 17, 34,
				"pickaxe", 4, Block.soundTypePiston);
		registerBlock(indium_ore, "indium_ore");

		zinc_ore = new GenericBlock("zinc_ore", Material.rock, 18, 36,
				"pickaxe", 4, Block.soundTypePiston);
		registerBlock(zinc_ore, "zinc_ore");

		purplonium_ore = new GenericBlock("purplonium_ore", Material.rock, 20,
				40, "pickaxe", 4, Block.soundTypePiston);
		registerBlock(purplonium_ore, "purplonium_ore");

		fire_block = new GenericBlock("fire_block", Material.rock, 50, 100,
				"pickaxe", 5, Block.soundTypeMetal);
		registerBlock(fire_block, "fire_block");

		purplonium_block = new GenericBlock("purplonium_block", Material.rock,
				15, 30, "pickaxe", 4, Block.soundTypeMetal);
		registerBlock(purplonium_block, "purplonium_block");

		indium_block = new GenericBlock("indium_block", Material.rock, 15, 30,
				"pickaxe", 4, Block.soundTypeMetal);
		registerBlock(indium_block, "indium_block");

		cadmium_block = new GenericBlock("cadmium_block", Material.rock, 15,
				30, "pickaxe", 4, Block.soundTypeMetal);
		registerBlock(cadmium_block, "cadmium_block");

		zinc_block = new GenericBlock("zinc_block", Material.rock, 15, 30,
				"pickaxe", 4, Block.soundTypeMetal);
		registerBlock(zinc_block, "zinc_block");

		obsidiron_block = new GenericBlock("obsidiron_block", Material.rock,
				150, 3000, "pickaxe", 6, Block.soundTypeMetal);
		registerBlock(obsidiron_block, "obsidiron_block");
	}

	private void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, name);
		mesher.register(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("spooky:" + name, "inventory"));
	}

	/*
	 * ========================================================================================================================================================================
	 * Swords, Tools, and Armor
	 * ========================================================================================================================================================================
	 */

	private void registerSwordsToolsAndArmor() {

		registerInfusedTools();

		registerArmor();

		registerMiscTools();

	}

	private void registerInfusedTools() {

		bc1_sword = new GenericSword("bc1_sword", BC1);
		registerItem(bc1_sword, "bc1_sword");

		bc1_pickaxe = new GenericPickaxe("bc1_pickaxe", BC1);
		registerItem(bc1_pickaxe, "bc1_pickaxe");

		bc1_axe = new GenericAxe("bc1_axe", BC1);
		registerItem(bc1_axe, "bc1_axe");

		bc1_spade = new GenericSpade("bc1_spade", BC1);
		registerItem(bc1_spade, "bc1_spade");

		bc2_sword = new GenericSword("bc2_sword", BC2);
		registerItem(bc2_sword, "bc2_sword");

		bc2_pickaxe = new GenericPickaxe("bc2_pickaxe", BC2);
		registerItem(bc2_pickaxe, "bc2_pickaxe");

		bc2_axe = new GenericAxe("bc2_axe", BC2);
		registerItem(bc2_axe, "bc2_axe");

		bc2_spade = new GenericSpade("bc2_spade", BC2);
		registerItem(bc2_spade, "bc2_spade");

		bc3_sword = new GenericSword("bc3_sword", BC3);
		registerItem(bc3_sword, "bc3_sword");

		bc3_pickaxe = new GenericPickaxe("bc3_pickaxe", BC3);
		registerItem(bc3_pickaxe, "bc3_pickaxe");

		bc3_axe = new GenericAxe("bc3_axe", BC3);
		registerItem(bc3_axe, "bc3_axe");

		bc3_spade = new GenericSpade("bc3_spade", BC3);
		registerItem(bc3_spade, "bc3_spade");

	}

	private void registerArmor() {

		moss_helmet = new GenericArmor("moss_helmet", MOSSARMOR, 1, 0, "moss");
		registerItem(moss_helmet, "moss_helmet");

		moss_chestplate = new GenericArmor("moss_chestplate", MOSSARMOR, 1, 1,
				"moss");
		registerItem(moss_chestplate, "moss_chestplate");

		moss_leggings = new GenericArmor("moss_leggings", MOSSARMOR, 2, 2,
				"moss");
		registerItem(moss_leggings, "moss_leggings");

		moss_boots = new GenericArmor("moss_boots", MOSSARMOR, 1, 3, "moss");
		registerItem(moss_boots, "moss_boots");

		slime_helmet = new GenericArmor("slime_helmet", SLIMEARMOR, 1, 0,
				"slime");
		registerItem(slime_helmet, "slime_helmet");

		slime_chestplate = new GenericArmor("slime_chestplate", SLIMEARMOR, 1,
				1, "slime");
		registerItem(slime_chestplate, "slime_chestplate");

		slime_leggings = new GenericArmor("slime_leggings", SLIMEARMOR, 2, 2,
				"slime");
		registerItem(slime_leggings, "slime_leggings");

		slime_boots = new GenericArmor("slime_boots", SLIMEARMOR, 1, 3, "slime");
		registerItem(slime_boots, "slime_boots");

		fire_helmet = new GenericArmor("fire_helmet", FIREARMOR, 1, 0, "fire");
		registerItem(fire_helmet, "fire_helmet");

		fire_chestplate = new GenericArmor("fire_chestplate", FIREARMOR, 1, 1,
				"fire");
		registerItem(fire_chestplate, "fire_chestplate");

		fire_leggings = new GenericArmor("fire_leggings", FIREARMOR, 2, 2,
				"fire");
		registerItem(fire_leggings, "fire_leggings");

		fire_boots = new GenericArmor("fire_boots", FIREARMOR, 1, 3, "fire");
		registerItem(fire_boots, "fire_boots");

	}

	private void registerMiscTools() {

		fire_sword = new GenericSword("fire_sword", HELL);
		registerItem(fire_sword, "fire_sword");

		vorpal_sword = new GenericSword("vorpal_sword", ENDER);
		registerItem(vorpal_sword, "vorpal_sword");

		moss_sword = new GenericSword("moss_sword", MOSS);
		registerItem(moss_sword, "moss_sword");

		radioactive_sword = new GenericSword("radioactive_sword", RADIOACTIVE);
		registerItem(radioactive_sword, "radioactive_sword");

		radioactive_pickaxe = new GenericPickaxe("radioactive_pickaxe",
				RADIOACTIVE);
		registerItem(radioactive_pickaxe, "radioactive_pickaxe");

		radioactive_axe = new GenericAxe("radioactive_axe", RADIOACTIVE);
		registerItem(radioactive_axe, "radioactive_axe");

		radioactive_spade = new GenericSpade("radioactive_spade", RADIOACTIVE);
		registerItem(radioactive_spade, "radioactive_spade");

	}

	/*
	 * ========================================================================================================================================================================
	 * Bows, Guns, and Staves
	 * ========================================================================================================================================================================
	 */

	private void registerBowsGunsAndStaves(FMLInitializationEvent event) {

		registerBowsAndArrows(event);

		registerGuns();

		registerStaves();

	}

	private void registerBowsAndArrows(FMLInitializationEvent event) {

		ender_arrow = new GenericItem("ender_arrow", bows);

		ender_bow = new GenericBow(ender_bow, "ender_bow", 999, ender_arrow,
				2.0D);
		registerItem(ender_bow, "ender_bow");

		registerItem(ender_arrow, "ender_arrow");

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

		double_bow = new GenericBow(double_bow, "double_bow", 512, Items.arrow,
				1.0D, 2);
		registerItem(double_bow, "double_bow");

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

		fire_arrow = new GenericItem("fire_arrow", bows);

		fire_bow = new GenericBow(fire_bow, "fire_bow", 1500, fire_arrow, 4.0D);
		registerItem(fire_bow, "fire_bow");

		registerItem(fire_arrow, "fire_arrow");

		if (event.getSide().isClient()) {
			ModelBakery.addVariantName(fire_bow, new String[] {
					"spooky:fire_bow", "spooky:fire_bow_0",
					"spooky:fire_bow_1", "spooky:fire_bow_2" });
			mesher.register(fire_bow, 0, new ModelResourceLocation(
					"spooky:fire_bow", "inventory"));
			mesher.register(fire_bow, 1, new ModelResourceLocation(
					"spooky:fire_bow_0", "inventory"));
			mesher.register(fire_bow, 2, new ModelResourceLocation(
					"spooky:fire_bow_1", "inventory"));
			mesher.register(fire_bow, 3, new ModelResourceLocation(
					"spooky:fire_bow_2", "inventory"));
		}

	}

	private void registerGuns() {
		prismarine_pistol = new GenericGun("prismarine_pistol", 1234,
				Items.prismarine_shard, (byte) 6);
		registerItem(prismarine_pistol, "prismarine_pistol");

		fire_gun = new GenericGun("fire_gun", 666, Items.fire_charge, (byte) 9);
		registerItem(fire_gun, "fire_gun");

		ender_rifle = new GenericGun("ender_rifle", 888, Items.ender_pearl,
				(byte) 11);
		registerItem(ender_rifle, "ender_rifle");

		compressed_redstone = new GenericItem("compressed_redstone", guns);

		steampunk_gun = new GenericGun("steampunk_gun", 1280,
				compressed_redstone, (byte) 10, 4);
		registerItem(steampunk_gun, "steampunk_gun");

		registerItem(compressed_redstone, "compressed_redstone");

		fire_bullet = new GenericItem("fire_bullet", guns);

		incinerator_gun = new GenericGun("incinerator_gun", 1500, fire_bullet,
				(byte) 14);
		registerItem(incinerator_gun, "incinerator_gun");

		registerItem(fire_bullet, "fire_bullet");
	}

	private void registerStaves() {

		incinerator_staff = new IncineratorStaff("incinerator_staff");
		registerItem(incinerator_staff, "incinerator_staff");

		fire_staff = new FireStaff("fire_staff");
		registerItem(fire_staff, "fire_staff");

	}

	/*
	 * ========================================================================================================================================================================
	 * Recipes
	 * ========================================================================================================================================================================
	 */

	private void addRecipes() {

		addBoneRecipes();

		addIngotAndMineralRecipes();

		addInfusedSwordsAndToolsRecipes();

		addToolAndSwordRecipes();

		addArmorRecipes();

		addBowsGunsAndStavesRecipes();

		addMiscRecipes();

	}

	private void addBoneRecipes() {
		GameRegistry.addRecipe(new ItemStack(bone1), "ggg", "gbg", "ggg", 'g',
				Items.gold_nugget, 'b', Items.bone);

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

		GameRegistry.addRecipe(new ItemStack(bone_key1), "bb ", " b ", "cbc",
				'b', bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(bone_key1), " bb", " b ", "cbc",
				'b', bone8, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(bone9), "bbb", "bjb", "bbb", 'j',
				jade_ingot, 'b', Items.bone);

		GameRegistry.addRecipe(new ItemStack(bone10), "oeo", "ebe", "oeo", 'o',
				canopic_jar, 'e', cloth, 'b', bone2);

		GameRegistry.addRecipe(new ItemStack(bone10), "eoe", "obo", "eoe", 'o',
				canopic_jar, 'e', cloth, 'b', bone2);

		GameRegistry.addRecipe(new ItemStack(bone11), "ggg", "gbg", "ggg", 'g',
				gray_gel, 'b', bone_box);

		GameRegistry.addRecipe(new ItemStack(bone12), "ici", "ici", "ici", 'i',
				indium_ingot, 'c', cadmium_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(bone_core3),
				new ItemStack(bone9), new ItemStack(bone10), new ItemStack(
						bone11), new ItemStack(bone12), new ItemStack(
						Blocks.iron_block), new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block), new ItemStack(
						Blocks.iron_block), new ItemStack(Blocks.iron_block));

		GameRegistry.addRecipe(new ItemStack(bone_key1), "bb ", " b ", "cbc",
				'b', bone12, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(bone_key1), " bb", " b ", "cbc",
				'b', bone12, 'c', bone_core3);

	}

	private void addIngotAndMineralRecipes() {

		GameRegistry.addSmelting(jade_ore, new ItemStack(jade_ingot, 1), 0.3F);

		GameRegistry.addRecipe(new ItemStack(zinc_block), "jjj", "jjj", "jjj",
				'j', zinc_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(zinc_ingot, 9),
				new ItemStack(zinc_block));

		GameRegistry.addRecipe(new ItemStack(cadmium_block), "jjj", "jjj",
				"jjj", 'j', cadmium_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(cadmium_ingot, 9),
				new ItemStack(cadmium_block));

		GameRegistry.addRecipe(new ItemStack(indium_block), "jjj", "jjj",
				"jjj", 'j', indium_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(indium_ingot, 9),
				new ItemStack(indium_block));

		GameRegistry.addRecipe(new ItemStack(purplonium_block), "jjj", "jjj",
				"jjj", 'j', purplonium_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(purplonium_ingot, 9),
				new ItemStack(purplonium_block));

		GameRegistry.addSmelting(dim8_ore, new ItemStack(bone_ingot, 1), 0.3F);

		GameRegistry.addRecipe(new ItemStack(fire_block), "ff", "ff", 'f',
				fire_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(fire_ingot, 4),
				new ItemStack(fire_block));

		GameRegistry.addSmelting(indium_ore, new ItemStack(indium_ingot, 1),
				0.3F);

		GameRegistry.addSmelting(zinc_ore, new ItemStack(zinc_ingot, 1), 0.3F);

		GameRegistry.addSmelting(purplonium_ore, new ItemStack(
				purplonium_ingot, 1), 0.3F);

		GameRegistry.addSmelting(cadmium_dust, new ItemStack(cadmium_ingot, 1),
				0.1F);

		GameRegistry.addRecipe(new ItemStack(jade_block), "jjj", "jjj", "jjj",
				'j', jade_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(jade_ingot, 9),
				new ItemStack(jade_block));

		GameRegistry.addRecipe(new ItemStack(obsidiron_block), "iii", "iii",
				"iii", 'i', obsidiron_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(obsidiron_ingot, 9),
				new ItemStack(obsidiron_block));

	}

	private void addInfusedSwordsAndToolsRecipes() {

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

		GameRegistry.addRecipe(new ItemStack(bc3_sword), "c", "c", "b", 'b',
				bone12, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(bc3_pickaxe), "ccc", " b ", " b ",
				'b', bone12, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(bc3_axe), "cc", "cb", " b", 'b',
				bone12, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(bc3_axe), "cc", "bc", "b ", 'b',
				bone12, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(bc3_spade), "c", "b", "b", 'b',
				bone12, 'c', bone_core3);

	}

	private void addToolAndSwordRecipes() {

		GameRegistry.addRecipe(new ItemStack(Items.golden_sword), "b", "b",
				"s", 'b', bone1, 's', Items.stick);

		GameRegistry.addRecipe(new ItemStack(fire_sword), "b", "b", "s", 'b',
				bone3, 's', Items.blaze_rod);

		GameRegistry.addRecipe(new ItemStack(vorpal_sword), "b", "b", "B", 'b',
				bone4, 'B', bone3);

		GameRegistry.addRecipe(new ItemStack(moss_sword), "b", "b", "s", 's',
				Items.stick, 'b', bone6);

		GameRegistry.addRecipe(new ItemStack(radioactive_sword), "c", "c", "b",
				'b', bone12, 'c', purplonium_ingot);

		GameRegistry.addRecipe(new ItemStack(radioactive_pickaxe), "ccc",
				" b ", " b ", 'b', bone12, 'c', purplonium_ingot);

		GameRegistry.addRecipe(new ItemStack(radioactive_axe), "cc", "cb",
				" b", 'b', bone12, 'c', purplonium_ingot);

		GameRegistry.addRecipe(new ItemStack(radioactive_axe), "cc", "bc",
				"b ", 'b', bone12, 'c', purplonium_ingot);

		GameRegistry.addRecipe(new ItemStack(radioactive_spade), "c", "b", "b",
				'b', bone12, 'c', purplonium_ingot);

	}

	private void addArmorRecipes() {

		GameRegistry.addRecipe(new ItemStack(moss_helmet), "bcb", "b b", 'b',
				bone6, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(moss_chestplate), "c c", "bcb",
				"bbb", 'b', bone6, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(moss_leggings), "bcb", "b b",
				"b b", 'b', bone6, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(moss_boots), "c c", "b b", 'b',
				bone6, 'c', bone_core2);

		GameRegistry.addRecipe(new ItemStack(slime_helmet), "bcb", "b b", 'b',
				gray_gel, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(slime_chestplate), "c c", "bcb",
				"bbb", 'b', gray_gel, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(slime_leggings), "bcb", "b b",
				"b b", 'b', gray_gel, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(slime_boots), "c c", "b b", 'b',
				gray_gel, 'c', bone_core3);

		GameRegistry.addRecipe(new ItemStack(fire_helmet), "bcb", "b b", 'b',
				fire_ingot, 'c', fire_crystal);

		GameRegistry.addRecipe(new ItemStack(fire_chestplate), "c c", "bcb",
				"bbb", 'b', fire_ingot, 'c', fire_crystal);

		GameRegistry.addRecipe(new ItemStack(fire_leggings), "bcb", "b b",
				"b b", 'b', fire_ingot, 'c', fire_crystal);

		GameRegistry.addRecipe(new ItemStack(fire_boots), "c c", "b b", 'b',
				fire_ingot, 'c', fire_crystal);

	}

	private void addBowsGunsAndStavesRecipes() {

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

		GameRegistry.addRecipe(new ItemStack(ender_bow), " b ", "bBb", " b ",
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

		GameRegistry.addRecipe(new ItemStack(incinerator_summon), "c", "i",
				"i", 'c', bone_core2, 'i', bone_ingot);

		GameRegistry.addRecipe(new ItemStack(fire_bow), "iii", "ibi", "iii",
				'b', ender_bow, 'i', fire_ingot);

		GameRegistry.addShapelessRecipe(new ItemStack(fire_arrow, 8),
				new ItemStack(Items.arrow), new ItemStack(Items.blaze_powder));

		GameRegistry.addShapelessRecipe(new ItemStack(fire_bullet, 8),
				new ItemStack(fire_ingot));

		GameRegistry.addRecipe(new ItemStack(incinerator_gun), "eoo", " bb",
				'e', bone3, 'o', fire_ingot, 'b', fire_crystal);
		
		GameRegistry.addRecipe(new ItemStack(juggernaut_summon), "o", "r",
				"r", 'o', obsidiron_block, 'r', obsidiron_stick);

	}

	private void addMiscRecipes() {

		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 32),
				new ItemStack(bone_box));

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

		GameRegistry.addRecipe(new ItemStack(canopic_jar), " i ", "g g", "gGg",
				'g', Blocks.glass_pane, 'G', Items.gold_ingot, 'i',
				Items.iron_ingot);

		GameRegistry.addRecipe(new ItemStack(ice_plate, 1), "iii", "ici",
				"iii", 'i', ice_ingot, 'c', ice_charge);

		GameRegistry.addRecipe(new ItemStack(obsidiron_ingot, 2), "lil", "ioi",
				"lil", 'i', Blocks.iron_block, 'o', Blocks.obsidian, 'l',
				fire_block);

		GameRegistry.addRecipe(new ItemStack(obsidiron_ingot, 2), "ili", "lol",
				"ili", 'i', Blocks.iron_block, 'o', Blocks.obsidian, 'l',
				fire_block);

		GameRegistry.addRecipe(new ItemStack(obsidiron_stick, 4), "i", "i",
				'i', obsidiron_ingot);
		
		GameRegistry.addRecipe(new ItemStack(molten_essence, 2), "iii", "ibi",
				"iii", 'i', fire_ingot, 'b', fire_block);
		
		GameRegistry.addRecipe(new ItemStack(bedrockium_ingot, 4), "lil", "ioi",
				"lil", 'i', bedrock_shard, 'o', obsidiron_block, 'l',
				molten_essence);

		GameRegistry.addRecipe(new ItemStack(bedrockium_ingot, 4), "ili", "lol",
				"ili", 'i', bedrock_shard, 'o', obsidiron_block, 'l',
				molten_essence);

	}

	/*
	 * ========================================================================================================================================================================
	 * Mobs
	 * ========================================================================================================================================================================
	 */

	private void registerMobs() {

		registerSpawnEggMobs();

		registerNoSpawnEggMobs();

	}

	private void registerSpawnEggMobs() {
		registerModEntity(EntitySkeletonCow.class, new RenderSkeletonCow(),
				"skeletoncow", EntityRegistry.findGlobalUniqueEntityId(),
				0xEBEBD5, 0xC9C9A7);

		registerModEntity(EntityJellySkull.class, new RenderJellySkull(),
				"jellyskull", EntityRegistry.findGlobalUniqueEntityId(),
				0xC9CCBC, 0xE7F2AC);

		registerModEntity(EntityRisenDead.class, new RenderRisenDead(),
				"risen_dead", EntityRegistry.findGlobalUniqueEntityId(),
				0xF0DD51, 0xF01D1D);

		EntityRegistry.addSpawn(EntityRisenDead.class, 100, 1, 2,
				EnumCreatureType.MONSTER, BiomeGenBase.jungle,
				BiomeGenBase.plains, BiomeGenBase.desert,
				BiomeGenBase.extremeHills);

		registerModEntity(EntityIncinerator.class, new RenderIncinerator(),
				"incinerator", EntityRegistry.findGlobalUniqueEntityId(),
				0xE31B1B, 0xC51BE3);

		registerModEntity(EntityIceGolem.class, new RenderIceGolem(),
				"ice_golem", EntityRegistry.findGlobalUniqueEntityId(),
				0x6FBCD6, 0xA8DCED);

		registerModEntity(EntityFrost.class, new RenderFrost(), "frost",
				EntityRegistry.findGlobalUniqueEntityId(), 0x007BFF, 0x48CBF7);

		registerModEntity(EntityJuggernaut.class, new RenderJuggernaut(),
				"juggernaut", EntityRegistry.findGlobalUniqueEntityId(),
				0x404040, 0x9E9E9E);
	}

	private void registerNoSpawnEggMobs() {
		registerModEntity(EntityFrostBall.class, new RenderFrostBall(),
				"frost_ball", EntityRegistry.findGlobalUniqueEntityId());
	}

	public void registerModEntity(Class parEntityClass, Render render,
			String parEntityName, int entityId, int foregroundColor,
			int backgroundColor) {
		EntityRegistry.registerGlobalEntityID(parEntityClass, parEntityName,
				entityId, foregroundColor, backgroundColor);
		EntityRegistry.registerModEntity(parEntityClass, parEntityName,
				entityId, this, 80, 1, false);
		RenderingRegistry
				.registerEntityRenderingHandler(parEntityClass, render);
	}

	public void registerModEntity(Class parEntityClass, Render render,
			String parEntityName, int entityId) {
		EntityRegistry.registerModEntity(parEntityClass, parEntityName,
				entityId, this, 80, 1, false);
		RenderingRegistry
				.registerEntityRenderingHandler(parEntityClass, render);
	}

	/*
	 * ========================================================================================================================================================================
	 * Misc. Methods
	 * ========================================================================================================================================================================
	 */

	private void preRegister() {
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
		FMLCommonHandler.instance().bus().register(new EventHandlers());
	}

	private void postRegister() {
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
				new WeightedRandomChestContent(new ItemStack(
						spookyscaryskeletons), 1, 1, 50));

		GameRegistry.registerWorldGenerator(new OreGenerator(), 0);

		DimensionRegistry.mainRegistry();
		BiomeRegistry.mainRegistry();

		addRecipes();
	}
}
