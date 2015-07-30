package org.redfrog404.spooky.scary.skeletons.dimensions;

import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static void mainRegistry(){
		registerDimension1();
	}
	
	public static final int dimId8 = 8;
	
	public static void registerDimension1(){
		DimensionManager.registerProviderType(dimId8, WorldProviderDim8.class, false);
		DimensionManager.registerDimension(dimId8, dimId8);
	}

}
