package com.github.pinont.shinysmp;


import com.github.pinont.shinysmp.entities.player.SuperPlayer;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.plugin.CorePlugin;
import com.github.pinont.shinysmp.discordBot.App;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Core extends CorePlugin {

    public static ConfigManager registeredPlayers;

    App app;
//    ShinyDatabase database = new ShinyDatabase();

    @Override
    public void onPluginStart() {
        app = new App();
        registeredPlayers = new ConfigManager("registered_players.yml");
//        database.init("database.yml");
        app.start();
    }

    @Override
    public void onPluginStop() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            SuperPlayer superPlayer = new SuperPlayer(player);
            superPlayer.saveData();
        }
        app.shutdown();
//        database.closeConnection();
    }
}