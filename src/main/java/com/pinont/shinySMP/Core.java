package com.pinont.shinySMP;

import com.pinont.lib.plugin.CorePlugin;
import com.pinont.shinySMP.discordBot.App;

public class Core extends CorePlugin {

    App app = new App();
    ShinyDatabase database = new ShinyDatabase();

    @Override
    public void onPluginStart() {
        database.init("database.yml");
        app.start();
    }

    @Override
    public void onPluginStop() {
        app.shutdown();
        database.closeConnection();
    }
}
