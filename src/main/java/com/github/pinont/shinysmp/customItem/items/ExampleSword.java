package com.github.pinont.shinysmp.customItem.items;

import com.github.pinont.singularitylib.api.items.CustomItem;
import com.github.pinont.singularitylib.api.items.ItemCreator;
import com.github.pinont.singularitylib.api.items.ItemInteraction;
import org.bukkit.Material;

public class ExampleSword extends CustomItem {

    // This is an example custom sword item class.

    @Override
    public ItemInteraction getInteraction() {
        return null;
    }

    @Override
    public ItemCreator register() {
        return new ItemCreator(Material.DIAMOND_SWORD) ;
    }
}
