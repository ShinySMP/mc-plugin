package com.pinont.shinySMP.discordBot.commands;

import com.pinont.lib.api.hook.discordJDA.SimpleSlashCommands;
import com.pinont.lib.api.hook.discordJDA.SlashCommandComponent;
import com.pinont.shinySMP.Core;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Join extends SimpleSlashCommands {

    public SlashCommandComponent getCommand() {
        return new SlashCommandComponent() {
            @Override
            public String name() {
                return "join";
            }

            @Override
            public String description() {
                return "Join the Minecraft server via Discord.";
            }
        };
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(getCommand().name())) return;
        if (Core.registeredPlayers.getConfig().getValues(true).containsValue(event.getUser().getId())) {
            event.reply("You are already registered!").setEphemeral(true).queue();
        } else {
            event.reply("You are not registered yet. Please register first using the /register command.").setEphemeral(true).queue();
        }

    }

}
