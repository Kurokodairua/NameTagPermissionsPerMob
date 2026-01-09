package de.kurokodairua.nametagpermissionspermob;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final NametagPermissionsPerMob plugin;

    public ReloadCommand(NametagPermissionsPerMob plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("nametag.admin")) {
            sender.sendMessage(plugin.msg("reload-no-permission"));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadAll();
            sender.sendMessage(plugin.msg("reload-success"));
            return true;
        }

        sender.sendMessage(plugin.msg("usage"));
        return true;
    }
}
