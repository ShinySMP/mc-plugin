package com.pinont.shinySMP.discordBot;

import com.pinont.lib.api.manager.ConfigManager;
import com.pinont.shinySMP.Core;
import com.pinont.shinySMP.discordBot.commands.Register;
import com.pinont.shinySMP.discordBot.events.ReadyListener;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class App {
    private Thread jdaThread;
    private net.dv8tion.jda.api.JDA jda;

    public void reloadConfig() {
        shutdown();
        start();
    }

    public void start() {
        ConfigManager configManager = new ConfigManager("discord_bot.yml");
        FileConfiguration config = configManager.getConfig();
        if (config.getString("bot_token") == null) {
            configManager.set("bot_token", "BOT_TOKEN_HERE");
            configManager.saveConfig();
            Core.sendConsoleMessage(ChatColor.RED + "Please set the bot_token in discord_bot.yml");
            return;
        }
        try {
            jdaThread = new Thread(() -> {
                String token = config.getString("bot_token");
                JDABuilder bot = JDABuilder.createDefault(token);
                Register register = new Register();
                jda = bot.addEventListeners(register, new ReadyListener(register))
                        .build();
            }, "JDA-Thread");
            jdaThread.start();
        } catch (Exception e) {
            Core.sendConsoleMessage(ChatColor.RED + "Make sure you have correct bot token in discord_bot.yml");
            throw e;
        }
    }

    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
        }
        if (jdaThread != null && jdaThread.isAlive()) {
            try {
                jdaThread.join(5000); // Wait up to 5 seconds for thread to finish
            } catch (InterruptedException ignored) {}
        }
    }
}
