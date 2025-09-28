package com.github.pinont.shinysmp.events;

import com.github.pinont.shinysmp.Core;
import com.github.pinont.shinysmp.customItem.ItemRarity;
import com.github.pinont.shinysmp.customItem.Rarity;
import com.github.pinont.shinysmp.entities.player.SuperPlayer;
import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import com.github.pinont.singularitylib.api.items.ItemCreator;
import com.github.pinont.singularitylib.api.utils.Common;
import com.github.pinont.singularitylib.api.utils.Console;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AutoRegister
public class PlayerListener implements Listener {

    @EventHandler
    public void onEnterAnotherBiome(PlayerMoveEvent event) {
        if (event.getFrom().getBlock().getBiome() != event.getTo().getBlock().getBiome()) {
            Player player = event.getPlayer();
            SuperPlayer superPlayer = new SuperPlayer(event.getPlayer());
            Console.debug("Player " + player.getName() + " enters biome " + event.getTo().getBlock().getBiome());
            if (superPlayer.getVisitedBiomes().contains(event.getTo().getBlock().getBiome().toString())) {
                Console.debug("Player " + player.getName() + " biome matched... returning");
                return;
            }
            Title title = Title.title(
                    new Common().colorize("<aqua>" + event.getTo().getBlock().getBiome().toString().replace("_", " ") + "</aqua>"),
                    new Common().colorize(""),
                    Title.Times.times(
                            java.time.Duration.ofMillis(500),
                            java.time.Duration.ofSeconds(2),
                            java.time.Duration.ofMillis(500)
                    )
            );
            player.showTitle(title);
            player.playSound(player, "minecraft:entity.experience_orb.pickup", 1.0f, 1.0f);
            superPlayer.addVisitedBiome(event.getTo().getBlock().getBiome().toString());
            Console.debug("Player " + player.getName() + " visited biomes: " + superPlayer.getVisitedBiomes());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItemInMainHand(toItemRarity(Objects.requireNonNullElse(player.getInventory().getItemInMainHand(), new ItemStack(Material.AIR))));
        player.getInventory().setItemInOffHand(toItemRarity(Objects.requireNonNullElse(player.getInventory().getItemInOffHand(), new ItemStack(Material.AIR))));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!Common.isAir(event.getCurrentItem()) && !ItemRarity.hasRarity(event.getCurrentItem())) {
            event.setCancelled(true);
            event.getClickedInventory().setItem(event.getSlot(), toItemRarity(event.getCurrentItem()));
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (Common.isAir(event.getItem().getItemStack())) return;
        ItemStack item = event.getItem().getItemStack();
        event.getItem().setItemStack(Objects.requireNonNullElse(toItemRarity(item), new ItemStack(Material.AIR)));
        broadcastOnPickUp(event, ItemRarity.getRarity(item), item, event.getPlayer());
    }

    @EventHandler
    public void onPlayerDromItem(PlayerDropItemEvent event) {
        event.getItemDrop().setItemStack(Objects.requireNonNullElse(toItemRarity(event.getItemDrop().getItemStack()), new ItemStack(Material.AIR)));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Core.registeredPlayers.getConfig().contains(player.getName().toLowerCase())) {
            String kickMessage = "<red>You are not registered on the discord yet.</red>\n" +
                    "<yellow>Please register using the command </yellow><gold>/register " + player.getName() + "</gold><yellow> in the discord server.</yellow>";
            player.kick(new Common().colorize(kickMessage));
            event.joinMessage(null);
        }
        SuperPlayer superPlayer = new SuperPlayer(event.getPlayer());
        superPlayer.loadData();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SuperPlayer superPlayer = new SuperPlayer(event.getPlayer());
        superPlayer.saveData();
    }

    private ItemStack toItemRarity(ItemStack item) {
        if (Common.isAir(item)) return new ItemStack(Material.AIR);
        return ItemRarity.updateItemRarity(item);
    }

    private void broadcastOnPickUp(PlayerPickupItemEvent event, Rarity rarity, ItemStack item, Player player) {
        if (rarity == Rarity.LEGENDARY || rarity == Rarity.MYTHIC || rarity == Rarity.SPECIAL) {
            if (new ItemCreator(item).hasTag("broadcastOnPickUp")) return;
            Core.getInstance().getServer().broadcast(new Common().colorize("<gold>" + player.getName() + " has picked up a " + ItemRarity.getCustomName(item) + "!</gold>"));
            event.getItem().setItemStack(new ItemCreator(item).setTag("broadcastOnPickUp", "true").create());
        }
    }
}
