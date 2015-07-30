package org.redfrog404.spooky.scary.skeletons.biomes;

import java.util.Random;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenSpookySands extends BiomeGenBase {
	public BiomeGenSpookySands(int p_i1977_1_) {
		super(p_i1977_1_);
		this.spawnableCreatureList.clear();
		this.topBlock = Blocks.soul_sand.getDefaultState();
		this.fillerBlock = Spooky.bone_box.getDefaultState();
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = 0;
		this.theBiomeDecorator.reedsPerChunk = 0;
		this.theBiomeDecorator.cactiPerChunk = 0;
		FlowerEntry flower = new FlowerEntry(Blocks.nether_wart.getBlockState()
				.getBaseState(), 100);
		this.flowers.add(flower);
		this.enableRain = false;
		this.enableSnow = false;
		this.theBiomeDecorator.generateLakes = false;
		this.spawnableCreatureList.clear();
	}

	public void decorate(World worldIn, Random p_180624_2_, BlockPos p_180624_3_) {
		super.decorate(worldIn, p_180624_2_, p_180624_3_);
	}
}