package com.enderio.core.common.util.stackable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.enderio.core.common.util.NNList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

interface IThing {

  @Nullable
  IThing bake();
  
  boolean is(@Nullable Item item);

  boolean is(@Nullable ItemStack itemStack);

  boolean is(@Nullable Block block);

  @Nonnull
  NNList<Item> getItems();

  @Nonnull
  NNList<ItemStack> getItemStacks();

  @Nonnull
  NNList<Block> getBlocks();

  @Nullable
  Object getRecipeObject();

  /**
   * Returns the single stack that represents the whole Thing. Usually this will
   * be the first stack in the stack list, with the exception of
   * ore-dictionary-backed things, where this may be overridden by a a registered
   * preference.
   */
  default @Nonnull ItemStack getLeadItemStack() {
    NNList<ItemStack> itemStacks = getItemStacks();
    return itemStacks.isEmpty() ? ItemStack.EMPTY : itemStacks.get(0);
  }

}