package com.github.pinont.shinysmp.customItem;

import com.github.pinont.singularitylib.api.utils.Common;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public enum Rarity {
    COMMON(new Common().colorize("<bold><white>COMMON</white></bold>", true)), // White
    UNCOMMON(new Common().colorize("<bold><green>UNCOMMON</green></bold>", true)), // Green
    RARE(new Common().colorize("<bold><blue>RARE</blue></bold>", true)), // Blue
    EPIC(new Common().colorize("<bold><dark_purple>EPIC</dark_purple></bold>", true)), // Dark Purple
    LEGENDARY(new Common().colorize("<bold><gold>LEGENDARY</gold></bold>", true)), // Gold
    MYTHIC(new Common().colorize("<bold><light_purple>MYTHIC</light_purple></bold>", true)), // Light Purple
    SPECIAL(new Common().colorize("<bold><red>SPECIAL</red></bold>", true)), // Red
    UNKNOWN(new Common().colorize("<bold><gray>UNKNOWN</gray></bold>", true)); // Gray

    private final Component rarity;

    Rarity(Component rarity) {
        this.rarity = rarity;
    }

    public Component getRarity() {
        return rarity;
    }

    public static Rarity fromString(String rarity) {
        if (rarity == null) return UNKNOWN;
        return switch (rarity.toLowerCase()) {
            case "common" -> COMMON;
            case "uncommon" -> UNCOMMON;
            case "rare" -> RARE;
            case "epic" -> EPIC;
            case "legendary" -> LEGENDARY;
            case "mythic" -> MYTHIC;
            case "special" -> SPECIAL;
            default -> UNKNOWN;
        };
    }

    public String toRarityColor(String text) {
        text = "<!italic>" + Common.resetStringColor(text); // remove legacy format code before apply new color
        return switch (this) {
            case COMMON -> "<white>" + text + "</white>";
            case UNCOMMON -> "<green>" + text + "</green>";
            case RARE -> "<blue>" + text + "</blue>";
            case EPIC -> "<dark_purple>" + text + "</dark_purple>";
            case LEGENDARY -> "<obf><white>i</obf><gradient:#ffa200:#fff6e3><shadow:#000000FF><b>" + text + "</gradient><!italic><obf><white>i</white></obf>";
            case MYTHIC -> "<obf><yellow>i<white>i</obf><gradient:#0014f5:#a652ff:#2e005e><shadow:#000000FF><b>" + text + "</gradient><!italic><obf><yellow>i</yellow><white>i</white></obf>";
            case SPECIAL -> "<obf><red>i<white>i<red>i</obf><gradient:#870017:#731d0e:#fffafb><shadow:#000000FF><b>" + text + "</gradient><!italic><obf><red>i</red><white>i</white><red>i</red></obf>";
            default -> "<gray>" + text + "</gray>";
        };
    }

    public Component toComponent(String text) {
        return new Common().colorize(toRarityColor(text), true);
    }

    public String toString() {
        return switch (this) {
            case COMMON -> "common";
            case UNCOMMON -> "uncommon";
            case RARE -> "rare";
            case EPIC -> "epic";
            case LEGENDARY -> "legendary";
            case MYTHIC -> "mythic";
            case SPECIAL -> "special";
            default -> "unknown";
        };
    }

    public static Rarity getMinecraftRarity(Material material) {
        if (material == null) return COMMON;
        return switch (material) {
            case DRAGON_EGG -> LEGENDARY;

            case NETHER_STAR, ELYTRA, DRAGON_HEAD -> EPIC;

            case ENCHANTED_GOLDEN_APPLE, NETHERITE_INGOT, NETHERITE_SCRAP, MACE, HEAVY_CORE,

                 DISC_FRAGMENT_5, MUSIC_DISC_5,
                 MUSIC_DISC_11, MUSIC_DISC_13, MUSIC_DISC_BLOCKS, MUSIC_DISC_CAT, MUSIC_DISC_CHIRP, MUSIC_DISC_CREATOR,
                 MUSIC_DISC_FAR, MUSIC_DISC_CREATOR_MUSIC_BOX, MUSIC_DISC_MALL, MUSIC_DISC_MELLOHI, MUSIC_DISC_PIGSTEP,
                 MUSIC_DISC_STAL, MUSIC_DISC_STRAD, MUSIC_DISC_WAIT, MUSIC_DISC_WARD, MUSIC_DISC_OTHERSIDE,
                 MUSIC_DISC_PRECIPICE, MUSIC_DISC_RELIC,

                 CREEPER_HEAD, CREEPER_WALL_HEAD, DRAGON_WALL_HEAD, PIGLIN_HEAD, PIGLIN_WALL_HEAD, PLAYER_HEAD, PISTON_HEAD,
                 PLAYER_WALL_HEAD, ZOMBIE_HEAD, ZOMBIE_WALL_HEAD,

                 BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
                 DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, EYE_ARMOR_TRIM_SMITHING_TEMPLATE, FLOW_ARMOR_TRIM_SMITHING_TEMPLATE,
                 HOST_ARMOR_TRIM_SMITHING_TEMPLATE, RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
                 SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
                 SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
                 VEX_ARMOR_TRIM_SMITHING_TEMPLATE, WARD_ARMOR_TRIM_SMITHING_TEMPLATE, WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
                 WILD_ARMOR_TRIM_SMITHING_TEMPLATE

                 -> RARE;

            case DIAMOND, EMERALD, ENCHANTED_BOOK, TOTEM_OF_UNDYING,
                 HEART_OF_THE_SEA, NETHERITE_UPGRADE_SMITHING_TEMPLATE, EXPERIENCE_BOTTLE,

                 ANGLER_POTTERY_SHERD, ARCHER_POTTERY_SHERD, ARMS_UP_POTTERY_SHERD,
                 BLADE_POTTERY_SHERD, BREWER_POTTERY_SHERD, DANGER_POTTERY_SHERD, EXPLORER_POTTERY_SHERD,
                 BURN_POTTERY_SHERD, FLOW_POTTERY_SHERD, FRIEND_POTTERY_SHERD, GUSTER_POTTERY_SHERD, HEART_POTTERY_SHERD,
                 HEARTBREAK_POTTERY_SHERD, HOWL_POTTERY_SHERD, MINER_POTTERY_SHERD, MOURNER_POTTERY_SHERD, PLENTY_POTTERY_SHERD,
                 PRIZE_POTTERY_SHERD, SCRAPE_POTTERY_SHERD, SHEAF_POTTERY_SHERD, SHELTER_POTTERY_SHERD, SKULL_POTTERY_SHERD, SNORT_POTTERY_SHERD

                 -> UNCOMMON;

            default -> COMMON;
        };
    }
}
