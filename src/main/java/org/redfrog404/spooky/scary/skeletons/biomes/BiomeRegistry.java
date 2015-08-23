package org.redfrog404.spooky.scary.skeletons.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;

public class BiomeRegistry {

	public static void mainRegistry() {
		initializeBiomes();
		registerBiomes();
	}

	public static BiomeGenBase spooky_sands;
	public static BiomeGenBase ice;

	public static void initializeBiomes() {
		spooky_sands = new BiomeGenSpookySands(137)
				.setBiomeName("Spooky Sands")
				.setTemperatureRainfall(1.2F, 0F);
		
		ice = new BiomeGenIce(138)
		.setBiomeName("Frozen Wasteland")
		.setTemperatureRainfall(0.0F, 0F);
	}

	public static void registerBiomes() {
		BiomeDictionary.registerBiomeType(spooky_sands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ice, Type.COLD);
	}

}