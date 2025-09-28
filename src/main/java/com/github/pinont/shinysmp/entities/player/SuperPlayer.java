package com.github.pinont.shinysmp.entities.player;

import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.api.utils.Common;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SuperPlayer {

    private final Player player;
    private final ConfigManager configManager;

    public SuperPlayer(Player player) {
        this.player = player;
        this.configManager = new ConfigManager("players", player.getUniqueId() + ".yml");
    }

    public void saveData() {
        configManager.set("visited_biomes", getVisitedBiomes());
        configManager.saveConfig();
    }

    public void loadData() {
        setVisitedBiomes(configManager.getConfig().getStringList("visited_biomes"));
    }

    public List<String> getVisitedBiomes() {
        return player.getMetadata("visited_biomes").stream()
                .filter(meta -> meta.value() instanceof ArrayList)
                .map(meta -> (ArrayList<String>) meta.value())
                .findFirst()
                .orElse(new ArrayList<>());
    }

    public void setVisitedBiomes(List<String> visitedBiomes) {
        player.setMetadata("visited_biomes", new org.bukkit.metadata.FixedMetadataValue(Common.plugin, visitedBiomes));
    }

    public void addVisitedBiome(String biome) {
        List<String> visitedBiomes = getVisitedBiomes();
        if (!visitedBiomes.contains(biome)) {
            visitedBiomes.add(biome);
            setVisitedBiomes(visitedBiomes);
        }
    }
}
