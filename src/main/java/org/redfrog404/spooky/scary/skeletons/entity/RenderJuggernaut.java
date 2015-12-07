package org.redfrog404.spooky.scary.skeletons.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSnowmanHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderJuggernaut extends RenderLiving {
	private static final ResourceLocation juggernautTextures = new ResourceLocation(
			"spooky:textures/entity/dim9/juggernaut.png");

	public RenderJuggernaut() {
		super(Minecraft.getMinecraft().getRenderManager(),
				new ModelJuggernaut(), 0.5F);
//        this.addLayer(new LayerJuggernautHead(this));
	}

	protected ResourceLocation func_180587_a(EntityJuggernaut p_180587_1_) {
		return juggernautTextures;
	}

	public ModelJuggernaut func_177123_g() {
		return (ModelJuggernaut) super.getMainModel();
	}

	public ModelBase getMainModel() {
		return this.func_177123_g();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.func_180587_a((EntityJuggernaut) entity);
	}
}