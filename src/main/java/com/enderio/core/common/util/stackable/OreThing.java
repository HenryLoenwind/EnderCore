package com.enderio.core.common.util.stackable;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.enderio.core.common.util.NNList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

class OreThing implements IThing {

  private final @Nonnull String name;
  private @Nonnull NNList<ItemStack> ores = new NNList<ItemStack>();

  OreThing(@Nonnull String name) {
    this.name = name;
  }

  @Override
  public @Nullable IThing bake() {
    if (OreDictionary.doesOreNameExist(name)) {
      ores.clear();
      ores.addAll(OreDictionary.getOres(name));
      if (!ores.isEmpty()) {
        return this;
      }
    }
    return null;
  }

  @Override
  public boolean is(@Nullable Item item) {
    for (ItemStack oreStack : ores) {
      if (oreStack.getItem() == item) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean is(@Nullable ItemStack itemStack) {
    for (ItemStack oreStack : ores) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() == oreStack.getItem()
          && (!oreStack.getHasSubtypes() || oreStack.getItemDamage() == OreDictionary.WILDCARD_VALUE || oreStack.getMetadata() == itemStack.getMetadata())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean is(@Nullable Block block) {
    for (ItemStack oreStack : ores) {
      if (block != null && (Item.getItemFromBlock(block) == oreStack.getItem() || Block.getBlockFromItem(oreStack.getItem()) == block)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public @Nonnull NNList<Item> getItems() {
    NNList<Item> result = new NNList<Item>();
    for (ItemStack oreStack : ores) {
      if (!oreStack.isEmpty() && !result.contains(oreStack.getItem())) {
        result.add(oreStack.getItem());
      }
    }
    return result;
  }

  @Override
  public @Nonnull NNList<ItemStack> getItemStacks() {
    return ores;
  }

  @Override
  public @Nonnull NNList<Block> getBlocks() {
    NNList<Block> result = new NNList<Block>();
    for (ItemStack oreStack : ores) {
      if (!oreStack.isEmpty()) {
        Block block = Block.getBlockFromItem(oreStack.getItem());
        if (block != Blocks.AIR) {
          result.add(block);
        }
      }
    }
    return result;
  }

  @Override
  public @Nullable Object getRecipeObject() {
    return name;
  }

  @Override
  public @Nonnull ItemStack getLeadItemStack() {
    return applyOverrides(name, IThing.super.getLeadItemStack());
  }

  //

  private static final @Nonnull Map<String, Things> OVERRIDES = new HashMap<>();

  public static void registerOverride(@Nonnull String name, @Nonnull Things override) {
    if (OVERRIDES.containsKey(name)) {
      OVERRIDES.get(name).add(override);
    } else {
      OVERRIDES.put(name, override);
    }
  }

  public static @Nonnull ItemStack applyOverrides(@Nonnull String name, @Nonnull ItemStack fallback) {
    if (OVERRIDES.containsKey(name)) {
      Things override = OVERRIDES.get(name);
      NNList<ItemStack> itemStacks = override.getItemStacks();
      if (!itemStacks.isEmpty()) {
        return itemStacks.get(0);
      }
    }
    return fallback;
  }

  public static @Nonnull ItemStack applyOverrides(@Nonnull ItemStack stackIn) {
    int[] oreIDs = OreDictionary.getOreIDs(stackIn);
    if (oreIDs != null) {
      for (int i = 0; i < oreIDs.length; i++) {
        String name = OreDictionary.getOreName(oreIDs[i]);
        ItemStack stack = applyOverrides(name, ItemStack.EMPTY);
        if (!stack.isEmpty()) {
          return stack;
        }
      }
    }
    return stackIn;
  }

}