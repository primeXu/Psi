/**
* This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [21/02/2016, 16:57:14 (GMT)]
 */
package vazkii.psi.common.crafting.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import vazkii.psi.api.exosuit.ISensorHoldable;

public class SensorRemoveRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		boolean foundHoldable = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(!foundHoldable && stack.getItem() instanceof ISensorHoldable && !((ISensorHoldable) stack.getItem()).getAttachedSensor(stack).isEmpty())
					foundHoldable = true;
				else return false;
			}
		}

		return foundHoldable;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		ItemStack holdableItem = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty())
				holdableItem = stack;
		}

		ItemStack copy = holdableItem.copy();
		ISensorHoldable holdable = (ISensorHoldable) holdableItem.getItem();
		holdable.attachSensor(copy, ItemStack.EMPTY);

		return copy;
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}

}
