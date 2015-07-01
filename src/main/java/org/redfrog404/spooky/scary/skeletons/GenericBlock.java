package org.redfrog404.spooky.scary.skeletons;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class GenericBlock extends Block {
	
	public GenericBlock(String name, Material material, float hardness, float resistance, String tool, int harvestLevel, SoundType sound) {
        super(material);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Spooky.blocks);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(tool, harvestLevel);
        this.setStepSound(sound);
    }
	
	/**
     * Determines if this block is can be destroyed by the specified entities normal behavior.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z position
     * @return True to allow the ender dragon to destroy this block
     */
	@Override
    public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity)
    {
        if (entity instanceof EntityWither)
        {
            return this != Blocks.bedrock && this != Blocks.end_portal && this != Blocks.end_portal_frame && this != Blocks.command_block;
        }
        else if (entity instanceof EntityDragon)
        {
            return this != Blocks.obsidian && this != Blocks.end_stone && this != Blocks.bedrock && this != Spooky.bone_box; 
        }

        return true;
    }

}
