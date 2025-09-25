package com.pinont.shinySMP.commands;

import com.pinont.lib.api.annotation.AutoRegister;
import com.pinont.lib.api.command.SimpleCommand;
import com.pinont.lib.plugin.CorePlugin;
import com.pinont.shinySMP.discordBot.App;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AutoRegister
public class ShinySMP implements SimpleCommand {

    @Override
    public String getName() {
        return "shinysmp:smp:shiny";
    }

    @Override
    public String description() {
        return "Main ShinySMP command";
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender.hasPermission("shinysmp.admin") || !(sender instanceof Player);
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        CommandSender sender = commandSourceStack.getSender();
        if (strings.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "\n====================\n" +
                    ChatColor.GREEN + "Welcome to ShinySMP\n" +
                    ChatColor.AQUA + "Version: " + ChatColor.WHITE + CorePlugin.getInstance().getDescription().getVersion() + "\n" +
                    ChatColor.AQUA + "API Version: " + ChatColor.WHITE + CorePlugin.getAPIVersion() + "\n" +
                    ChatColor.YELLOW + "====================\n"
            );
            return;
        }
        if (strings[0].equalsIgnoreCase("reload")) {
            reload();
        } else if (strings[0].equalsIgnoreCase("discord")) {
            discord(strings);
        } else {
            sender.sendMessage(ChatColor.YELLOW + "====================\n" +
                    ChatColor.GREEN + "Welcome to ShinySMP\n" +
                    ChatColor.AQUA + "Version: " + ChatColor.WHITE + CorePlugin.getInstance().getDescription().getVersion() + "\n" +
                    ChatColor.AQUA + "API Version: " + ChatColor.WHITE + CorePlugin.getAPIVersion() + "\n" +
                    ChatColor.YELLOW + "===================="
            );
        }
    }

    private void reload() {
        CorePlugin.getInstance().reloadConfig();
        CorePlugin.sendConsoleMessage("Config reloaded!");
    }

    private void discord(String[] args) {
        if (args.length > 1 && args[1].equalsIgnoreCase("reload")) {
            new App().reloadConfig();
            CorePlugin.sendConsoleMessage("Discord Config reloaded!");
            return;
        }
        CorePlugin.sendConsoleMessage("Discord link: https://discord.gg/shinysmp");
    }
}
