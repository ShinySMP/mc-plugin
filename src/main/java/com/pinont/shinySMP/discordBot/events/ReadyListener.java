package com.pinont.shinySMP.discordBot.events;

import com.pinont.shinySMP.discordBot.commands.Register;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import java.util.*;
import java.util.concurrent.*;
import org.bukkit.Bukkit;

public class ReadyListener extends ListenerAdapter {
    private final Register register;
    private final List<Activity> activities;
    private ScheduledExecutorService scheduler;
    private Activity currentActivity;

    public ReadyListener(Register register) {
        this.register = register;
        this.activities = new ArrayList<>();
        activities.add(Activity.playing("Shiny SMP"));
        activities.add(Activity.customStatus("Developing Shiny SMP"));
        activities.add(Activity.listening("/register"));
        // Watching activity will be handled dynamically
    }

    @Override
    public void onReady(ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            guild.updateCommands().addCommands(
                    register.getCommand().getSlashCommandData()
                            .addOption(OptionType.STRING, "name", "Your minecraft name", true)
            ).queue();
        }
        JDA jda = event.getJDA();
        scheduler = Executors.newScheduledThreadPool(2); // 2 threads for activity updates
        setRandomActivity(jda);
        // Change activity every hour
        scheduler.scheduleAtFixedRate(() -> setRandomActivity(jda), 1, 1, TimeUnit.HOURS);
    }

    private void setRandomActivity(JDA jda) {
        Random rand = new Random();
        int idx = rand.nextInt(activities.size() + 1); // +1 for watching
        if (idx < activities.size()) {
            currentActivity = activities.get(idx);
            jda.getPresence().setActivity(currentActivity);
        } else {
            // Watching activity: update every minute
            updateWatchingActivity(jda);
            scheduler.scheduleAtFixedRate(() -> updateWatchingActivity(jda), 1, 1, TimeUnit.MINUTES);
        }
    }

    private void updateWatchingActivity(JDA jda) {
        int playerCount = Bukkit.getOnlinePlayers().size();
        Activity watching = Activity.watching(playerCount + " players");
        currentActivity = watching;
        jda.getPresence().setActivity(watching);
    }
}
