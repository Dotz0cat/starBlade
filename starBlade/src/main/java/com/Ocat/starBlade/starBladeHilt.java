package com.Ocat.starBlade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class starBladeHilt extends Item {
	boolean isIgnited;
	int stage;
	
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
    }
    
    public void ignite(ItemStack stack) {
        //adds the code for igniting the sword
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
    	//timing this could be laggy. have some other idiot look at it.
    	if (nbt.hasKey("timer1")) {
    		nbt.setInteger("timer1", 6000);
    	}
    	else {
    		nbt.setInteger("timer1", 6000);
    	}
    	//set up while loop to irment timer
    	int i = nbt.getInteger("timer1");
    	while (i>=1) {
    		nbt.setInteger("timer1", i);
    		i--;
    	}
    	if (nbt.getInteger("timer1")==0) {
    		nbt.removeTag("timer1");
    		nbt.setInteger("stage", 2);
    		stage = 2;
    	}
    	//stage 2 timer this will be fun
    	if (nbt.hasKey("timer2")) {
    		nbt.setInteger("timer2", 6000);
    	}
    	else {
    		nbt.setInteger("timer2", 6000);
    	}
    	int i2 = nbt.getInteger("timer2");
    	while (i2>=1) {
    		nbt.setInteger("timer2", i2);
    		i2--;
    	}
    	if (nbt.getInteger("timer2")==0) {
    		nbt.removeTag("timer2");
    		nbt.setInteger("stage", 3);
    		stage = 3;
    	}
    	//stage 3
    	if (nbt.hasKey("timer3")) {
    		nbt.setInteger("timer3", 6000);
    	}
    	else {
    		nbt.setInteger("timer3", 6000);
    	}
    	int i3 = nbt.getInteger("timer3");
    	while (i3>=1) {
    		nbt.setInteger("timer2", i3);
    		i3--;
    	}
    	if (nbt.getInteger("timer3")==0) {
    		nbt.removeTag("timer3");
    		nbt.setInteger("stage", 0);
    		stage = 0;
    	}
    	stage = 0;
    	//code to change the item to a depleated star blade
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
    	boolean hasFuel = false;
    	hasFuel = player.inventory.hasItemStack(starBlade:v1Fuel);
    	//if has fuel and not ignited
    	if (!isIgnited&&stage==0&&hasFuel) {
    		ignite(itemStack);
    	}
		return itemStack;
    }
}
