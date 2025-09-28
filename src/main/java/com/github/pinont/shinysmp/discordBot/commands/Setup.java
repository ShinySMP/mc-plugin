package com.github.pinont.shinysmp.discordBot.commands;

import com.github.pinont.singularitylib.api.hook.discordJDA.SimpleSlashCommands;
import com.github.pinont.singularitylib.api.hook.discordJDA.SlashCommandComponent;
import com.github.pinont.singularitylib.api.manager.ConfigManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Setup extends SimpleSlashCommands {
    public SlashCommandComponent getCommand() {
        return new SlashCommandComponent() {
            @Override
            public String name() {
                return "setup";
            }

            @Override
            public String description() {
                return "Setup the bot by creating the 'registered' role and saving its ID.";
            }
        };
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(getCommand().name())) return;
        Member member = event.getMember();
        if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("You need admin privileges to use this command.").setEphemeral(true).queue();
            return;
        }
        Guild guild = event.getGuild();
        if (guild == null) {
            event.reply("This command can only be used in a server.").setEphemeral(true).queue();
            return;
        }
        Role registeredRole = guild.getRolesByName("whitelisted", true).stream().findFirst().orElse(null);
        if (registeredRole == null) {
            guild.createRole().setName("whitelisted").queue(role -> {
                saveRoleId(role.getId(), event.getUser().getId());
                event.reply("Role 'whitelisted' created and saved.").setEphemeral(true).queue();
            });
        } else {
            saveRoleId(registeredRole.getId(), event.getGuild().getId());
            event.reply("Role 'whitelisted' already exists. ID saved.").setEphemeral(true).queue();
        }
    }

    private void saveRoleId(String roleId, String guildId) {
        ConfigManager configManager = new ConfigManager("discord_bot.yml");
        configManager.set("whitelisted_roles_id." + guildId, roleId);
        configManager.saveConfig();
    }
}
