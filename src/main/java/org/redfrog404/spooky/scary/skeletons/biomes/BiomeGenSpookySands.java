package org.redfrog404.spooky.scary.skeletons.biomes;

import java.util.Random;

import org.redfrog404.spooky.scary.skeletons.entity.EntityJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.EntitySkeletonCow;
import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenSpookySands extends BiomeGenBase {
	public BiomeGenSpookySands(int p_i1977_1_) {
		super(p_i1977_1_);
		this.theBiomeDecorator = createBiomeDecorator();
		this.spawnableCreatureList.clear();
		this.topBlock = Blocks.soul_sand.getDefaultState();
		this.fillerBlock = Spooky.bone_box.getDefaultState();
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = 0;
		this.theBiomeDecorator.reedsPerChunk = 0;
		this.theBiomeDecorator.cactiPerChunk = 0;
		this.enableRain = false;
		this.enableSnow = false;
		this.waterColorMultiplier = 0xC28148;
		this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySkeletonCow.class, 8, 3, 6));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityJellySkull.class, 10, 4, 4));
		this.spawnableWaterCreatureList.clear();
	}

	public void decorate(World worldIn, Random p_180624_2_, BlockPos p_180624_3_) {
		theBiomeDecorator.decorate(worldIn, p_180624_2_, this, p_180624_3_);
	}
	
	public BiomeDecorator createBiomeDecorator()
    {
		BiomeDecorator decorator = getModdedBiomeDecorator(new BiomeDecorator());
		decorator.generateLakes = false;
        return decorator;
    }
}