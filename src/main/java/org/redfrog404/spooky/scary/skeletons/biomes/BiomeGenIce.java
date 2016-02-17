package org.redfrog404.spooky.scary.skeletons.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import org.redfrog404.spooky.scary.skeletons.entity.entity.EntityFrost;
import org.redfrog404.spooky.scary.skeletons.entity.entity.EntityIceGolem;

public class BiomeGenIce extends BiomeGenBase {
	
	public BiomeGenIce(int p_i1977_1_) {
		
		super(p_i1977_1_);
		this.theBiomeDecorator = createBiomeDecorator();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.topBlock = Blocks.packed_ice.getDefaultState();
		this.fillerBlock = Blocks.packed_ice.getDefaultState();
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = 0;
		this.theBiomeDecorator.reedsPerChunk = 0;
		this.theBiomeDecorator.cactiPerChunk = 0;
		this.enableRain = true;
		this.enableSnow = true;
		this.waterColorMultiplier = 0xB5EDFF;
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityIceGolem.class, 10, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityFrost.class, 10, 1, 3));
	}
}