package com.Ocat.starBlade;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class starBladeHilt extends Item {
	boolean isIgnited;
	int stage = 0;
	
    public starBladeHilt() {
        setUnlocalizedName("starBladeHilt");
        setMaxStackSize(1);
        if (stage==1&&isIgnited) {
        	setTextureName(StarBlade.MODID + "starBladeStage1");
        	setMaxDamage(5);
        	//balance the thing dotz!!!!
        }
        else if (stage==2&&isIgnited) {
        	setTextureName(StarBlade.MODID + "starBladeStage2");
        	setMaxDamage(10);
        	//balance the thing dotz!!!!
        }
        else if (stage==3&&isIgnited) {
        	setTextureName(StarBlade.MODID + "starBladeStage3");
        	setMaxDamage(15);
        	//balance the thing dotz!!!!
        }
        else {
        	setTextureName(StarBlade.MODID + ":starBladeHilt");
        	setMaxDamage(0);
        }
        setCreativeTab(CreativeTabs.tabCombat);
    }
    
    public void ignite(ItemStack stack, EntityPlayer player, World world) {
        //adds the code for igniting the sword
    	//acess inventory and comsume the fuel
    	player.inventory.consumeInventoryItem(items.v1Fuel);
    	//nbt stuff!!
    	NBTTagCompound nbt;
    	if (stack.hasTagCompound()) {
    		nbt = stack.getTagCompound();
    	}
    	else {
    		nbt = new NBTTagCompound();
    	}
    	//is ignited
    	if (nbt.hasKey("isIgnited")) {
    		nbt.setBoolean("isIgnited", true);
    		isIgnited = true;
    	}
    	else {
    		nbt.setBoolean("isIgnited", true);
    		isIgnited = true;
    	}
    	//stage 1
    	//now changing stages will be the tricky part
    	if (nbt.hasKey("stage")) {
    		nbt.setInteger("stage", 1);
    		stage = 1;
    	}
    	else {
    		nbt.setInteger("Stage", 1);
    		stage = 1;
    	}
    	
    	MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("stage 1"));
    	//timing this could be laggy. have some other idiot look at it.
    	nbt.setInteger("timer1", (int) world.getTotalWorldTime());
    	//set up while loop 
    	int i = 1;
    	while (i==1) {
    		if ((nbt.getInteger("timer1")+6000)>=world.getTotalWorldTime()); {
    			//exit loop
    			i=0;
    			//remove tag
    			nbt.removeTag("timer1");
    		}
    	}
    	stage = 2;
    	
    	MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("Stage 2"));
    	//stage 2 timer this will be fun
    	nbt.setInteger("timer2", (int) world.getTotalWorldTime());
    	//set up while loop 
    	int i2 = 1;
    	while (i2==1) {
    		if ((nbt.getInteger("timer2")+6000)>=world.getTotalWorldTime()); {
    			//exit loop
    			i2=0;
    			//remove tag
    			nbt.removeTag("timer2");
    		}
    	}
    	stage = 3;
    	
    	MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("Stage 3"));
    	//stage 3
    	nbt.setInteger("timer3", (int) world.getTotalWorldTime());
    	//set up while loop 
    	int i3 = 1;
    	while (i3==1) {
    		if ((nbt.getInteger("timer3")+6000)>=world.getTotalWorldTime()); {
    			//exit loop
    			i3=0;
    			//remove tag
    			nbt.removeTag("timer3");
    		}
    	}
    	stage = 0;
    	MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("done"));
    	stage = 0;
    	//code to change the item to a depleated star blade
    	replaceItem(player);
    	isIgnited = false;
    }
    
    public void replaceItem(EntityPlayer player) {
    	ItemStack dpleated = new ItemStack(items.depleatedStarMetalBlade);
    	player.inventory.consumeInventoryItem(items.starBladeHilt);
    	player.inventory.addItemStackToInventory(dpleated);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
    	boolean hasFuel = false;
    	hasFuel = player.inventory.hasItem(items.v1Fuel);
    	//if has fuel and not ignited
    	if (!isIgnited&&stage==0&&hasFuel) {
    		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("ignited"));
    		ignite(itemStack, player, world);
    		
    	}
		return itemStack;
    }
}
