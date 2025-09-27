package com.pinont.shinySMP.discordBot;

import com.pinont.lib.api.hook.discordJDA.DiscordApp;
import com.pinont.shinySMP.discordBot.commands.Register;
import com.pinont.shinySMP.discordBot.commands.Setup;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App extends DiscordApp {
    private final List<Activity> activities;
    private ScheduledExecutorService scheduler;
    private Activity currentActivity;

    public App() {
        super("discord_bot.yml", true);
        this.activities = new ArrayList<>();
        activities.add(Activity.playing("Shiny SMP"));
        activities.add(Activity.customStatus("Developing Shiny SMP"));
        activities.add(Activity.listening("/register"));
    }

    @Override
    public void onStartup() {
        addCommands(new Register(), new Setup());
    }

    @Override
    public void onAppReady(ReadyEvent readyEvent) {
        JDA jda = readyEvent.getJDA();
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

    @Override
    public void onShutdown() {

    }
}
