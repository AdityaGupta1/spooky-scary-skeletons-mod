package org.redfrog404.spooky.scary.skeletons.dimensions;

import java.util.List;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DimensionGateway extends Block {

	public DimensionGateway(String name, Material material, float hardness,
			float resistance, String tool, int harvestLevel, SoundType sound) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.blocks);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setHarvestLevel(tool, harvestLevel);
		this.setStepSound(sound);
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer entityplayer, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (entityplayer instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entityplayer;

			if (player.getHeldItem() == null) {
				return false;
			}

			if (player.getHeldItem().getItem() == Spooky.bone_key1) {
				if (player.dimension == 0) {
					player.mcServer
							.getConfigurationManager()
							.transferPlayerToDimension(
									player,
									DimensionRegistry.dimId8,
									new TeleporterDim8(
											player.mcServer
													.worldServerForDimension(DimensionRegistry.dimId8)));
				} else if (player.dimension == DimensionRegistry.dimId8) {
					player.mcServer.getConfigurationManager()
							.transferPlayerToDimension(
									player,
									0,
									new TeleporterDim8(player.mcServer
											.worldServerForDimension(0)));
				} else {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}
}
