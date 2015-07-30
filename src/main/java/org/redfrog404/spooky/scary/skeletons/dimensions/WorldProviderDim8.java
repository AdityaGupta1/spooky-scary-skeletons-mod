package org.redfrog404.spooky.scary.skeletons.dimensions;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDim8 extends WorldProvider {
	
	public void registerWorldChunkManager(){
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.desert, 1.2F);
		this.dimensionId = DimensionRegistry.dimId8;
	}
	
	public IChunkProvider createChunkGeneration() {
		return null;
	}

	@Override
	public String getDimensionName() {
		return "spookysands";
	}

	@Override
	public String getInternalNameSuffix() {
		return "dimension";
	}

}
