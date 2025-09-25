package com.pinont.shinySMP.discordBot.commands;

import com.pinont.shinySMP.discordBot.SlashCommandComponent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Register extends ListenerAdapter {

    public SlashCommandComponent getCommand() {
        return new SlashCommandComponent() {
            @Override
            public String name() {
                return "register";
            }

            @Override
            public String description() {
                return "Add your self to a server";
            }
        };
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals(getCommand().name())) {
            String nameArg = event.getOption("name").getAsString();
            event.reply("Signed " + nameArg + " into the whitelist!").setEphemeral(true).queue();
        }
    }
}
