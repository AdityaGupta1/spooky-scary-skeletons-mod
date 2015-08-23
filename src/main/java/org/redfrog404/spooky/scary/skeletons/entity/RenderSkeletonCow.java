package org.redfrog404.spooky.scary.skeletons.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSkeletonCow extends RenderCow {
	private static final ResourceLocation cowTextures = new ResourceLocation(
			"spooky:textures/entity/dim8/skeletoncow.png");

	public RenderSkeletonCow() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelSkeletonCow(), 0.7F);
	}

	protected ResourceLocation func_180572_a(EntitySkeletonCow p_180572_1_) {
		return cowTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.func_180572_a((EntitySkeletonCow) entity);
	}
}