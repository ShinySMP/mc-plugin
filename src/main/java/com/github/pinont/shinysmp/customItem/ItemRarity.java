package com.github.pinont.shinysmp.customItem;

import com.github.pinont.singularitylib.api.items.ItemCreator;
import com.github.pinont.singularitylib.api.utils.Common;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class ItemRarity {
    public static ItemStack updateItemRarity(ItemStack item) {
        ItemCreator itemCreator = new ItemCreator(item);
        if (!itemCreator.hasTag("rarity")) {
            Rarity rarity = Rarity.getMinecraftRarity(item.getType()); // Default rarity
            itemCreator.setTag("rarity", rarity.toString());
            String blank = "";
            itemCreator.addLore(new Common().colorize(blank), rarity.getRarity());
            itemCreator.setAmount(item.getAmount());
            String name = rarity.toRarityColor(itemCreator.getName());
            itemCreator.setTag("customName", name);
            itemCreator.setName(new Common().colorize(name, true));
            item = itemCreator.create();
        }
        return item;
    }

    public static boolean hasRarity(ItemStack item) {
        ItemCreator itemCreator = new ItemCreator(item);
        return itemCreator.hasTag("rarity");
    }

    public static Rarity getRarity(ItemStack item) {
        ItemCreator itemCreator = new ItemCreator(item);
        if (itemCreator.hasTag("rarity")) {
            return Rarity.fromString(itemCreator.getTag("rarity"));
        }
        return Rarity.UNKNOWN;
    }

    public static String getCustomName(ItemStack item) {
        ItemCreator itemCreator = new ItemCreator(item);
        if (itemCreator.hasTag("customName")) {
            return itemCreator.getTag("customName");
        }
        return itemCreator.getName();
    }
}
