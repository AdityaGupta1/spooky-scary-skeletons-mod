package org.redfrog404.spooky.scary.skeletons.creativetab;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class ArmorTab extends CreativeTabs
{
    public ArmorTab(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return Spooky.moss_chestplate;
    }
}
