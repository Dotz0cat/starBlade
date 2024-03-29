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
    	nbt.setBoolean("isIgnited", true);
    	isIgnited = true;
    	//stage 1
    	//now changing stages will be the tricky part
    	nbt.setInteger("stage", 1);
    	stage = 1;
    	MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("stage 1"));
    	//timing this could be laggy. have some other idiot look at it.
    	nbt.setLong("timer1", world.getTotalWorldTime());
    }
    
    public void replaceItem(EntityPlayer player) {
    	ItemStack depleted = new ItemStack(items.depleatedStarMetalBlade);
    	player.inventory.consumeInventoryItem(items.starBladeHilt);
    	player.inventory.addItemStackToInventory(depleted);
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int num, boolean bool) {
    	NBTTagCompound nbt;
    	try {
			nbt = stack.getTagCompound();
			if (nbt.getBoolean("isIgnited")||isIgnited) {
				switch (nbt.getInteger("stage")) {
					case (1):
						if (world.getTotalWorldTime()>=(nbt.getLong("timer1")+600)); {
				    		nbt.removeTag("timer1");
				    		nbt.setInteger("stage", 2);
				    		stage = 2;
				    		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("stage 2"));
				    		nbt.setLong("timer2", world.getTotalWorldTime());
				    	}
						MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("i am in the switch"));
					break;
					case (2):
						if (world.getTotalWorldTime()>=(nbt.getLong("timer2")+600)); {
				    		nbt.removeTag("timer2");
				    		nbt.setInteger("stage", 3);
				    		stage = 3;
				    		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("stage 3"));
				    		nbt.setLong("timer3", world.getTotalWorldTime());
				    	}
						//MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("i am in the switch"));
					break;
					case (3):
						if (world.getTotalWorldTime()>=(nbt.getLong("timer3")+600)); {
				    		nbt.removeTag("timer3");
				    		nbt.setInteger("stage", 0);
				    		stage = 0;
				    		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("done"));
				    		//get current player
				    		replaceItem((EntityPlayer)entity);
				    		nbt.setBoolean("isIgnited", false);
				    		isIgnited = false;
				    	}
						//MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("i am in the switch"));
					break;
					case (0):
					default:
						MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("i am in the switch"));
					break;
				}
			}
		} catch (Exception e) {
			//MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("the things broke"));
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
