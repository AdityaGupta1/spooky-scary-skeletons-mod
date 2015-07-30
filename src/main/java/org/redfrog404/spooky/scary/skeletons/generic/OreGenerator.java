package org.redfrog404.spooky.scary.skeletons.generic;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGenerator implements IWorldGenerator {
	
	private WorldGenerator bone_box_generator;
	private WorldGenerator fossil_generator;

	public OreGenerator() {
	    this.bone_box_generator = new WorldGenMinable(Spooky.bone_box.getDefaultState(), 4, BlockHelper.forBlock(Blocks.end_stone));
	    this.fossil_generator = new WorldGenMinable(Spooky.bone_ore.getDefaultState(), 8, BlockHelper.forBlock(Blocks.netherrack));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		switch (world.provider.getDimensionId()) {
		case 0: // Overworld
			break;
		case -1: // Nether
			this.runGenerator(this.fossil_generator, world, random, chunkX, chunkZ, 20, 0, 128);
			break;
		case 1: // End
			this.runGenerator(this.bone_box_generator, world, random, chunkX, chunkZ, 20, 0, 80);
			break;
		}

	}
	
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
	    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
	        throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

	    int heightDiff = maxHeight - minHeight + 1;
	    for (int i = 0; i < chancesToSpawn; i ++) {
	        int x = chunk_X * 16 + rand.nextInt(16);
	        int y = minHeight + rand.nextInt(heightDiff);
	        int z = chunk_Z * 16 + rand.nextInt(16);
	        generator.generate(world, rand, new BlockPos(x, y, z));
	    }
	}

}
