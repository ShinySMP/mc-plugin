package com.github.pinont.shinysmp.discordBot.commands;

import com.github.pinont.singularitylib.api.hook.discordJDA.SimpleSlashCommands;
import com.github.pinont.singularitylib.api.hook.discordJDA.SlashCommandComponent;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import com.github.pinont.singularitylib.api.utils.Console;
import com.github.pinont.shinysmp.Core;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Objects;


public class Register extends SimpleSlashCommands {

    public SlashCommandComponent getCommand() {
        return new SlashCommandComponent() {
            @Override
            public String name() {
                return "register";
            }

            @Override
            public String description() {
                return "Register to the Server.";
            }

            @Override
            public CommandData getCommandData() {
                return getSlashCommandData()
                        .addOption(OptionType.STRING, "name", "Your minecraft name", true);
            }
        };
    }

    private boolean isPlayerRegistered(String playerName, String userId) {
        return Core.registeredPlayers.getConfig().contains(playerName) || Core.registeredPlayers.getConfig().getValues(true).containsValue(userId);
    }

    private Role getWhitelistedRole(Guild guild) {
        String guildId = guild.getId();
        String roleId = null;
        try {
            ConfigManager configManager = new ConfigManager("discord_bot.yml");
            roleId = configManager.getConfig().getString("whitelisted_roles_id." + guildId);
            if (roleId == null) return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert roleId != null;
        return guild.getRoleById(roleId);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(getCommand().name())) return;
        String nameArg = Objects.requireNonNull(event.getOption("name")).getAsString().toLowerCase();
        Member member = event.getMember();
        Guild guild = event.getGuild();
        if (guild == null || member == null) {
            event.reply("This command can only be used in a server.").setEphemeral(true).queue();
            return;
        }
        Role whitelistedRole = getWhitelistedRole(guild);
        if (whitelistedRole == null) {
            event.reply("The server is not set up yet. Please contact an administrator.").setEphemeral(true).queue();
            return;
        } else if (member.getRoles().contains(whitelistedRole) || isPlayerRegistered(nameArg, event.getUser().getId())) {
            event.reply("You are already whitelisted!").setEphemeral(true).queue();
            guild.addRoleToMember(member, whitelistedRole).queue();
            return;
        }
        event.reply("Signed " + nameArg + " into the whitelist!").setEphemeral(true).queue();
        Core.registeredPlayers.set(nameArg, event.getUser().getId());
        Core.registeredPlayers.saveConfig();
        guild.addRoleToMember(member, whitelistedRole).queue();
        Console.log("Signed " + nameArg + " into the whitelist!");
    }
}
