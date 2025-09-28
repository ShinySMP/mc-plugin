package com.github.pinont.shinysmp.customItem;

import com.github.pinont.shinysmp.customItem.items.ExampleStick;
import com.github.pinont.shinysmp.customItem.items.ExampleSword;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ShinyItemStack {
    EXAMPLE_STICK,
    EXAMPLE_SWORD;

    public ItemStack getItem() {
        return switch (this) {
            case EXAMPLE_STICK -> new ExampleStick().getItem();
            case EXAMPLE_SWORD -> new ExampleSword().getItem();
            default -> new ItemStack(Material.AIR);
        };
    }
}
