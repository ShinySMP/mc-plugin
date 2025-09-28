package com.pinont.shinySMP;


import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.plugin.CorePlugin;
import com.pinont.shinySMP.discordBot.App;

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
        app.shutdown();
//        database.closeConnection();
    }
}