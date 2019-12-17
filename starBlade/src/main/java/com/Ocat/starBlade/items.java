package com.Ocat.starBlade;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class items {
    
    public static Item depleatedStarMetalBlade;
    public static Item starMetal;
    public static Item starBladeHilt;
    public static Item v1Fuel;
    
    public static void init() {
        //todo add creative tab
        //add star metal blade
        depleatedStarMetalBlade = new Item().setUnlocalizedName("depleatedStarMetalBlade").setTextureName(StarBlade.MODID + ":deplatedStarMetalBlade");
        GameRegistry.registerItem(depleatedStarMetalBlade, depleatedStarMetalBlade.getUnlocalizedName());
        
        //add star metal
        starMetal = new Item().setUnlocalizedName("starMetal").setTextureName(StarBlade.MODID + "starMetal");
        GameRegistry.registerItem(starMetal, starMetal.getUnlocalizedName());
        
        //add the hilt
        starBladeHilt = new starBladeHilt();
        GameRegistry.registerItem(starBladeHilt, starBladeHilt.getUnlocalizedName());
        
        //add v1 fuel
        v1Fuel = new Item().setUnlocalizedName("v1Fuel").setTextureName(StarBlade.MODID + ":v1Fuel");
        GameRegistry.registerItem(v1Fuel, v1Fuel.getUnlocalizedName());
    }
}
