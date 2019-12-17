package com.Ocat.starBlade;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = StarBlade.MODID, version = StarBlade.VERSION, name = StarBlade.NAME)
public class StarBlade
{
    public static final String NAME = "Star Blade";
    public static final String MODID = "starBlade";
    public static final String VERSION = "1.0";
    
    @Mod.Instance("StarBlade")
    public static StarBlade instance;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        items.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        
    }
}
