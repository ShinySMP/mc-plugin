package com.pinont.shinySMP.commands;

import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import com.github.pinont.singularitylib.api.command.SimpleCommand;
import com.github.pinont.singularitylib.plugin.CorePlugin;
import com.pinont.shinySMP.discordBot.App;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

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
            sender.sendMessage(ChatColor.YELLOW + "====================\n" +
                    ChatColor.GREEN + "Welcome to ShinySMP\n" +
                    ChatColor.AQUA + "Version: " + ChatColor.WHITE + CorePlugin.getInstance().getDescription().getVersion() + "\n" +
                    ChatColor.YELLOW + "====================\n"
            );
            return;
        }
        if (strings[0].equalsIgnoreCase("reload")) {
            reload(sender);
        } else if (strings[0].equalsIgnoreCase("discord")) {
            discord(sender, strings);
        } else {
            sender.sendMessage(ChatColor.YELLOW + "====================\n" +
                    ChatColor.GREEN + "Welcome to ShinySMP\n" +
                    ChatColor.AQUA + "Version: " + ChatColor.WHITE + CorePlugin.getInstance().getDescription().getVersion() + "\n" +
                    ChatColor.AQUA + "API Version: " + ChatColor.WHITE + CorePlugin.getAPIVersion() + "\n" +
                    ChatColor.YELLOW + "===================="
            );
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of("discord");
    }

    private void reload(CommandSender sender) {
        CorePlugin.getInstance().reloadConfig();
        sender.sendMessage("Config reloaded!");
    }

    private void discord(CommandSender sender, String[] args) {
        if (args.length > 1 && args[1].equalsIgnoreCase("reload")) {
            new App().reloadConfig();
            sender.sendMessage("Discord Config reloaded!");
            return;
        }
        sender.sendMessage("Discord link: https://discord.gg/shinysmp");
    }
}
