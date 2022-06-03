package me.Sam.RankSystem;

import me.Sam.RankSystem.Commands.RankPrefixCommand;
import me.Sam.RankSystem.Commands.RankupAdminCommand;
import me.Sam.RankSystem.Commands.RankupCommand;
import me.Sam.RankSystem.Listeners.InventoryClick;
import me.Sam.RankSystem.Listeners.JoinListener;
import me.Sam.RankSystem.Listeners.RankupListener;
import me.Sam.RankSystem.Ranks.*;
import me.Sam.RankSystem.TabCompleters.RankPrefixTab;
import me.Sam.RankSystem.TabCompleters.RankupAdminTab;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class RankSystem extends JavaPlugin {

    public static Map<String, Rank> ranks = new LinkedHashMap<>();
    public static Map<UUID, PlayerStats> playerStatsMap = new HashMap<>();
    public static RankSystem instance;
    public static Economy econ = null;
    public File dataFile = new File(getDataFolder(), "data.yml");
    public FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
    public ChatPrefixPlaceholder placeholder = null;

    public void onEnable() {
        instance = this;
        Rank rank1 = new Rank1();
        Rank rank2 = new Rank2();
        Rank rank3 = new Rank3();
        Rank rank4 = new Rank4();
        Rank rank5 = new Rank5();
        Rank rank6 = new Rank6();
        Rank rank7 = new Rank7();
        Rank rank8 = new Rank8();
        Rank rank9 = new Rank9();
        Rank rank10 = new Rank10();
        Rank rank11 = new Rank11();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RankupListener(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new JoinListener(), this);
        getCommand("rankup").setExecutor(new RankupCommand());
        getCommand("rankupadmin").setExecutor(new RankupAdminCommand());
        getCommand("rankupadmin").setTabCompleter(new RankupAdminTab());
        getCommand("rankprefix").setExecutor(new RankPrefixCommand());
        getCommand("rankprefix").setTabCompleter(new RankPrefixTab());
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!data.isConfigurationSection("Data")) {
            data.createSection("Data");
            try {
                data.save(dataFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        loadData();
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                saveData();
                Bukkit.getLogger().info("Saving data");
            }
        }, 30000, 30000);
        if (pm.getPlugin("PlaceholderAPI") != null) {
            placeholder = new ChatPrefixPlaceholder(this);
            placeholder.register();
        }
    }

    public void onDisable() {
        saveData();
        placeholder.unregister();
    }

    public void loadData() {
        ConfigurationSection dataSection = data.getConfigurationSection("Data");
        for (String uuid : dataSection.getKeys(false)) {
            String rankKey = dataSection.getString(uuid + ".rank");
            PlayerStats playerStats = new PlayerStats(ranks.get(rankKey));
            if (dataSection.getString(uuid + ".prefixOn") == null) {
                playerStats.setPrefixOn(true);
            } else {
                playerStats.setPrefixOn(dataSection.getBoolean(uuid + ".prefixOn"));
            }
            playerStatsMap.put(UUID.fromString(uuid), playerStats);
        }
    }

    public void saveData() {
        ConfigurationSection dataSection = data.getConfigurationSection("Data");
        for (Map.Entry<UUID, PlayerStats> entry : playerStatsMap.entrySet()) {
            dataSection.set(entry.getKey().toString() + ".rank", entry.getValue().getRank().getKey());
            dataSection.set(entry.getKey().toString() + ".rankName", entry.getValue().getRank().getName());
            dataSection.set(entry.getKey().toString() + ".prefixOn", entry.getValue().isPrefixOn());
        }
        try {
            data.save(dataFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
