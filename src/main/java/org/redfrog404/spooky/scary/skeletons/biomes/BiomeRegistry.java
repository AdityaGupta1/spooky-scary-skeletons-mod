package org.redfrog404.spooky.scary.skeletons.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;

public class BiomeRegistry {

	public static void mainRegistry() {
		initializeBiome();
		registerBiome();
	}

	public static BiomeGenBase spooky_sands;

	public static void initializeBiome() {
		spooky_sands = new BiomeGenSpookySands(137)
				.setBiomeName("Spooky Sands")
				.setTemperatureRainfall(1.2F, 0F);
	}

	public static void registerBiome() {
		BiomeDictionary.registerBiomeType(spooky_sands, Type.DESERT);
	}

}