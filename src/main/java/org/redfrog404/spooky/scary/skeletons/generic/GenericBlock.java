package org.redfrog404.spooky.scary.skeletons.generic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class GenericBlock extends Block {
	
	private Item droppedItem;
	private int baseQuantity;
	private int randomQuantity;
	
	public GenericBlock(String name, Material material, float hardness, float resistance, String tool, int harvestLevel, SoundType sound) {
        super(material);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Spooky.blocks);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(tool, harvestLevel);
        this.setStepSound(sound);
        droppedItem = Item.getItemFromBlock(this);
        baseQuantity = 1;
        randomQuantity = 0;
    }
	
	public GenericBlock(String name, Material material, float hardness, float resistance, String tool, int harvestLevel, SoundType sound, Item drop) {
        super(material);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Spooky.blocks);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(tool, harvestLevel);
        this.setStepSound(sound);
        droppedItem = drop;
        baseQuantity = 1;
        randomQuantity = 0;
    }
	
	public GenericBlock(String name, Material material, float hardness, float resistance, String tool, int harvestLevel, SoundType sound, int base, int random) {
        super(material);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Spooky.blocks);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(tool, harvestLevel);
        this.setStepSound(sound);
        droppedItem = Item.getItemFromBlock(this);
        baseQuantity = base;
        randomQuantity = random;
    }
	
	public GenericBlock(String name, Material material, float hardness, float resistance, String tool, int harvestLevel, SoundType sound, Item drop, int base, int random) {
        super(material);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Spooky.blocks);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(tool, harvestLevel);
        this.setStepSound(sound);
        droppedItem = drop;
        baseQuantity = base;
        randomQuantity = random;
    }
	
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
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return droppedItem;
    }
	
	@Override
	public int quantityDropped(Random random)
    {
        return baseQuantity + random.nextInt(randomQuantity + 1);
    }

}
