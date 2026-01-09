package de.kurokodairua.nametagpermissionspermob;

import org.bukkit.Material;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class NametagListener implements Listener {

    private final NametagPermissionsPerMob plugin;

    public NametagListener(NametagPermissionsPerMob plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNameTag(PlayerInteractEntityEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItem(event.getHand());

        if (item == null || item.getType() != Material.NAME_TAG) return;
        if (!(event.getRightClicked() instanceof Mob mob)) return;

        String type = mob.getType().name();

        if (!plugin.getBlockedMobs().contains(type)) return;

        String perm = "nametag.block." + type.toLowerCase();
        if (event.getPlayer().hasPermission(perm)) return;

        event.setCancelled(true);
        event.getPlayer().sendMessage(plugin.msg("no-permission"));
    }
}
