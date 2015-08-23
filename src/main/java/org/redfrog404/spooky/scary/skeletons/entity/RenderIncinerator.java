package org.redfrog404.spooky.scary.skeletons.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;

@SideOnly(Side.CLIENT)
public class RenderIncinerator extends RenderBiped {
	private static final ResourceLocation zombieTextures = new ResourceLocation(
			"spooky:textures/entity/dim8/incinerator.png");
	private static final ResourceLocation zombieVillagerTextures = new ResourceLocation(
			"spooky:textures/entity/dim8/incinerator.png");
	private final ModelBiped field_82434_o;
	private final List field_177122_o;

	public RenderIncinerator() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelIncinerator(), 0.5F, 1.0F);
		LayerRenderer layerrenderer = (LayerRenderer) this.layerRenderers
				.get(0);
		this.field_82434_o = this.modelBipedMain;
		this.addLayer(new LayerHeldItem(this));
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {

			protected void func_177177_a() {
				this.field_177189_c = new ModelZombie(0.5F, true);
				this.field_177186_d = new ModelZombie(1.0F, true);
			}
		};
		this.addLayer(layerbipedarmor);
		this.field_177122_o = Lists.newArrayList(this.layerRenderers);
	}

	public void func_180579_a(EntityIncinerator p_180579_1_, double p_180579_2_,
			double p_180579_4_, double p_180579_6_, float p_180579_8_,
			float p_180579_9_) {
		this.func_82427_a(p_180579_1_);
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_,
				p_180579_8_, p_180579_9_);
	}

	protected ResourceLocation func_180578_a(EntityIncinerator p_180578_1_) {
		return p_180578_1_.isVillager() ? zombieVillagerTextures
				: zombieTextures;
	}

	private void func_82427_a(EntityIncinerator p_82427_1_) {
		this.mainModel = this.field_82434_o;
		this.layerRenderers = this.field_177122_o;

		this.modelBipedMain = (ModelBiped) this.mainModel;
	}

	protected void rotateCorpse(EntityIncinerator p_77043_1_, float p_77043_2_,
			float p_77043_3_, float p_77043_4_) {
		if (p_77043_1_.isConverting()) {
			p_77043_3_ += (float) (Math
					.cos((double) p_77043_1_.ticksExisted * 3.25D) * Math.PI * 0.25D);
		}

		super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return this.func_180578_a((EntityIncinerator) entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(EntityLiving entity, double x, double y, double z,
			float p_76986_8_, float partialTicks) {
		this.func_180579_a((EntityIncinerator) entity, x, y, z, p_76986_8_,
				partialTicks);
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_,
			float p_77043_3_, float p_77043_4_) {
		this.rotateCorpse((EntityIncinerator) p_77043_1_, p_77043_2_, p_77043_3_,
				p_77043_4_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(EntityLivingBase entity, double x, double y, double z,
			float p_76986_8_, float partialTicks) {
		this.func_180579_a((EntityIncinerator) entity, x, y, z, p_76986_8_,
				partialTicks);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.func_180578_a((EntityIncinerator) entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(Entity entity, double x, double y, double z,
			float p_76986_8_, float partialTicks) {
		this.func_180579_a((EntityIncinerator) entity, x, y, z, p_76986_8_,
				partialTicks);
	}
}