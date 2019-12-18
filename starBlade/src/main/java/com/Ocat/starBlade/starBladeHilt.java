package com.Ocat.starBlade;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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
    	nbt.setLong("timer1", world.getTotalWorldTime());
    	
    }
    
    public void replaceItem(EntityPlayer player) {
    	ItemStack dpleated = new ItemStack(items.depleatedStarMetalBlade);
    	player.inventory.consumeInventoryItem(items.starBladeHilt);
    	player.inventory.addItemStackToInventory(dpleated);
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int num, boolean bool) {
    	NBTTagCompound nbt;
    	if (stack.hasTagCompound()) {
    		nbt = stack.getTagCompound();
    		if (nbt.getBoolean("isIgnited")) {
        		switch (nbt.getInteger("Stage")) {
        			case (1):
        				if ((nbt.getLong("timer1")+6000)>=world.getTotalWorldTime()); {
        	    			nbt.removeTag("timer1");
        	    			nbt.setInteger("stage", 2);
        	    			stage = 2;
        	    			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("stage 2"));
        	    			nbt.setLong("timer2", world.getTotalWorldTime());
        	    		}
        			break;
        			case (2):
        				if ((nbt.getLong("timer2")+6000)>=world.getTotalWorldTime()); {
        	    			nbt.removeTag("timer2");
        	    			nbt.setInteger("stage", 3);
        	    			stage = 3;
        	    			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("stage 3"));
        	    			nbt.setLong("timer3", world.getTotalWorldTime());
        	    		}
        			break;
        			case (3):
        				if ((nbt.getLong("timer3")+6000)>=world.getTotalWorldTime()); {
        	    			nbt.removeTag("timer3");
        	    			nbt.setInteger("stage", 0);
        	    			stage = 0;
        	    			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("done"));
        	    			//get current player
        	    			replaceItem((EntityPlayer)entity);
        	    			nbt.setBoolean("isIgnited", false);
        	    			isIgnited = false;
        	    		}
        			break;
        			case (0):
        			default:
        				isIgnited = false;
        			break;
        		}
        	}
    	}
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
