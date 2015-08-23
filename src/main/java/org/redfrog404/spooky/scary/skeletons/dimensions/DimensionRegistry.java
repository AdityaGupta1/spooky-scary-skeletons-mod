package org.redfrog404.spooky.scary.skeletons.dimensions;

import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static void mainRegistry(){
		registerDimensions();
	}
	
	public static final int dimId8 = 8;
	public static final int dimId9 = 9;
	
	public static void registerDimensions(){
		DimensionManager.registerProviderType(dimId8, WorldProviderDim8.class, false);
		DimensionManager.registerDimension(dimId8, dimId8);
		DimensionManager.registerProviderType(dimId9, WorldProviderDim9.class, false);
		DimensionManager.registerDimension(dimId9, dimId9);
	}

}
