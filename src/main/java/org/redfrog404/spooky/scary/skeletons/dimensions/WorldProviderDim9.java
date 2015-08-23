package org.redfrog404.spooky.scary.skeletons.dimensions;

import org.redfrog404.spooky.scary.skeletons.biomes.BiomeRegistry;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDim9 extends WorldProvider {
	
	public void registerWorldChunkManager(){
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeRegistry.ice, 0F);
		this.dimensionId = DimensionRegistry.dimId9;
	}
	
	public IChunkProvider createChunkGeneration() {
		return null;
	}

	@Override
	public String getDimensionName() {
		return "hightide";
	}

	@Override
	public String getInternalNameSuffix() {
		return "dimension";
	}

}
