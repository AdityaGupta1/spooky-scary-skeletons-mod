package org.redfrog404.spooky.scary.skeletons.creativetab;

import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public final class GunsTab extends CreativeTabs
{
    public GunsTab(int par1, String par2Str)
    {
        super(par1, par2Str);
        this.setRelevantEnchantmentTypes(new EnumEnchantmentType[] {EnumEnchantmentType.ALL});
    }

    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return Spooky.prismarine_pistol;
    }
}
