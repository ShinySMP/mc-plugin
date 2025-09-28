package com.pinont.shinySMP.events;

import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import com.github.pinont.singularitylib.api.utils.Common;
import com.pinont.shinySMP.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@AutoRegister
public class PiayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Core.registeredPlayers.getConfig().contains(player.getName().toLowerCase())) {
            String kickMessage = "<red>You are not registered on the discord yet.</red>\n" +
                    "<yellow>Please register using the command </yellow><gold>/register " + player.getName() + "</gold><yellow> in the discord server.</yellow>";
            player.kick(new Common().colorize(kickMessage));
            event.joinMessage(null);
        }
    }
}
