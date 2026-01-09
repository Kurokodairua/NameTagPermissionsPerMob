package de.kurokodairua.nametagPermissionsPerMob;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class NametagPermissionsPerMob extends JavaPlugin {

    private FileConfiguration mobConfig;
    private FileConfiguration messages;

    @Override
    public void onEnable() {
        setupFiles();
        Bukkit.getPluginManager().registerEvents(new NametagListener(this), this);
        getCommand("npm").setExecutor(new ReloadCommand(this));
    }

    public List<String> getBlockedMobs() {
        return mobConfig.getStringList("blocked-mobs");
    }

    public String msg(String key) {
        return messages.getString(key, "&cMessage fehlt: " + key)
                .replace("&", "§");
    }

    public void reloadAll() {
        mobConfig = YamlConfiguration.loadConfiguration(
                new File(getDataFolder(), "config/mobs.yml")
        );
        messages = YamlConfiguration.loadConfiguration(
                new File(getDataFolder(), "config/messages.yml")
        );
    }

    private void setupFiles() {
        File configDir = new File(getDataFolder(), "config");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        createFile(
                "config/mobs.yml",
                List.of(
                        "blocked-mobs:",
                        "  - SILVERFISH",
                        "  - ENDERMITE"
                )
        );

        createFile(
                "config/messages.yml",
                List.of(
                        "no-permission: \"&cDu darfst diesen Mob nicht benennen!\"",
                        "reload-success: \"&aNametagPermissionsPerMob wurde neu geladen.\"",
                        "reload-no-permission: \"&cDazu hast du keine Rechte.\"",
                        "usage: \"&cUsage: /npm reload\""
                )
        );

        reloadAll();
    }

    /**
     * Erstellt eine Datei nur, wenn sie noch nicht existiert
     */
    private void createFile(String relativePath, List<String> content) {
        File file = new File(getDataFolder(), relativePath);

        if (file.exists()) {
            return;
        }

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            Files.write(file.toPath(), content);
        } catch (IOException e) {
            getLogger().severe("Fehler beim Erstellen von " + relativePath);
            e.printStackTrace();
        }
    }
}
