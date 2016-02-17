package org.redfrog404.spooky.scary.skeletons.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelJuggernaut extends ModelBase {
	ModelRenderer Bottom;
	ModelRenderer Arm1;
	ModelRenderer Arm2;
	ModelRenderer Thing1;
	ModelRenderer Top;
	ModelRenderer Side1;
	ModelRenderer Side2;

	public ModelJuggernaut() {
		textureWidth = 256;
		textureHeight = 128;

		Bottom = new ModelRenderer(this, 0, 0);
		Bottom.addBox(-8F, -32F, -8F, 16, 32, 16);
		Bottom.setRotationPoint(0F, 24F, 0F);
		Bottom.setTextureSize(256, 128);
		Bottom.mirror = true;
		setRotation(Bottom, 0F, 0F, 0F);
		Arm1 = new ModelRenderer(this, 0, 48);
		Arm1.addBox(-16F, -2F, -2F, 16, 4, 4);
		Arm1.setRotationPoint(-6F, 9F, 0F);
		Arm1.setTextureSize(256, 128);
		Arm1.mirror = true;
		setRotation(Arm1, 0F, 0F, 0.4537856F);
		Arm2 = new ModelRenderer(this, 0, 48);
		Arm2.addBox(0F, -2F, -2F, 16, 4, 4);
		Arm2.setRotationPoint(6F, 8.5F, 0F);
		Arm2.setTextureSize(256, 128);
		Arm2.mirror = true;
		setRotation(Arm2, 0F, 0F, -0.4537856F);
		Thing1 = new ModelRenderer(this, 64, 0);
		Thing1.addBox(-2F, -8F, -2F, 4, 8, 4);
		Thing1.setRotationPoint(0F, -8F, 0F);
		Thing1.setTextureSize(256, 128);
		Thing1.mirror = true;
		setRotation(Thing1, 0F, 0F, 0F);
		Top = new ModelRenderer(this, 80, 0);
		Top.addBox(-16F, -8F, -4F, 32, 8, 8);
		Top.setRotationPoint(0F, -16F, 0F);
		Top.setTextureSize(256, 128);
		Top.mirror = true;
		setRotation(Top, 0F, 0F, 0F);
		Side1 = new ModelRenderer(this, 0, 56);
		Side1.addBox(0F, -4F, -8F, 8, 8, 16);
		Side1.setRotationPoint(20F, 1.5F, 0F);
		Side1.setTextureSize(256, 128);
		Side1.mirror = true;
		setRotation(Side1, 0F, 0F, -0.4537856F);
		Side2 = new ModelRenderer(this, 0, 56);
		Side2.addBox(0F, -4F, -8F, 8, 8, 16);
		Side2.setRotationPoint(-20F, 2F, 0F);
		Side2.setTextureSize(256, 128);
		Side2.mirror = true;
		setRotation(Side2, 0F, 0F, -2.6529F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.translate(0.0F, -0.75F, 0.0F);
		Bottom.render(f5);
		Arm1.render(f5);
		Arm2.render(f5);
		Thing1.render(f5);
		Top.render(f5);
		Side1.render(f5);
		Side2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
