package com.github.pinont.shinysmp.customItem.items;

import com.github.pinont.singularitylib.api.items.CustomItem;
import com.github.pinont.singularitylib.api.items.ItemCreator;
import com.github.pinont.singularitylib.api.items.ItemInteraction;
import org.bukkit.Material;

public class ExampleStick extends CustomItem {
    @Override
    public ItemInteraction getInteraction() {
        return null;
    }

    @Override
    public ItemCreator register() {
        return new ItemCreator(Material.STICK);
    }
}
