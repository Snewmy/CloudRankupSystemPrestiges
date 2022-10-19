package me.Sam.RankSystem;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import me.Sam.RankSystem.Commands.RankPrefixCommand;
import me.Sam.RankSystem.Commands.RankupAdminCommand;
import me.Sam.RankSystem.Commands.RankupCommand;
import me.Sam.RankSystem.Listeners.InventoryClick;
import me.Sam.RankSystem.Listeners.JoinListener;
import me.Sam.RankSystem.Listeners.RankupListener;
import me.Sam.RankSystem.Listeners.ShopGUIListener;
import me.Sam.RankSystem.Ranks.Rank0;
import me.Sam.RankSystem.Ranks.Rank1;
import me.Sam.RankSystem.Ranks.Rank10;
import me.Sam.RankSystem.Ranks.Rank11;
import me.Sam.RankSystem.Ranks.Rank2;
import me.Sam.RankSystem.Ranks.Rank3;
import me.Sam.RankSystem.Ranks.Rank4;
import me.Sam.RankSystem.Ranks.Rank5;
import me.Sam.RankSystem.Ranks.Rank6;
import me.Sam.RankSystem.Ranks.Rank7;
import me.Sam.RankSystem.Ranks.Rank8;
import me.Sam.RankSystem.Ranks.Rank9;
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

public class RankSystem extends JavaPlugin {
    public static Map<String, Rank> ranks = new LinkedHashMap<>();
    public static Map<UUID, PlayerStats> playerStatsMap = new HashMap<>();
    public static RankSystem instance;
    public static Economy econ = null;
    public File dataFile = new File(this.getDataFolder(), "data.yml");
    public FileConfiguration data;
    public ChatPrefixPlaceholder placeholder;

    public RankSystem() {
        this.data = YamlConfiguration.loadConfiguration(this.dataFile);
        this.placeholder = null;
    }

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.initRanks();
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new RankupListener(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new ShopGUIListener(), this);
        this.getCommand("rankup").setExecutor(new RankupCommand());
        this.getCommand("rankupadmin").setExecutor(new RankupAdminCommand());
        this.getCommand("rankupadmin").setTabCompleter(new RankupAdminTab());
        this.getCommand("rankprefix").setExecutor(new RankPrefixCommand());
        this.getCommand("rankprefix").setTabCompleter(new RankPrefixTab());
        if (!this.setupEconomy()) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        } else {
            if (!this.data.isConfigurationSection("Data")) {
                this.data.createSection("Data");

                try {
                    this.data.save(this.dataFile);
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }

            this.loadData();
            Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                public void run() {
                    RankSystem.this.saveData();
                    Bukkit.getLogger().info("Saving data");
                }
            }, 30000L, 30000L);
            if (pm.getPlugin("PlaceholderAPI") != null) {
                this.placeholder = new ChatPrefixPlaceholder(this);
                this.placeholder.register();
            }

        }
    }

    public void onDisable() {
        this.saveData();
        this.placeholder.unregister();
    }

    public void initRanks() {
        new Rank0();
        new Rank1();
        new Rank2();
        new Rank3();
        new Rank4();
        new Rank5();
        new Rank6();
        new Rank7();
        new Rank8();
        new Rank9();
        new Rank10();
        new Rank11();
    }

    public void loadData() {
        ConfigurationSection dataSection = this.data.getConfigurationSection("Data");
        Iterator<String> var2 = dataSection.getKeys(false).iterator();

        while(var2.hasNext()) {
            String uuid = var2.next();
            String rankKey = dataSection.getString(uuid + ".rank");
            PlayerStats playerStats = new PlayerStats((Rank)ranks.get(rankKey));
            if (dataSection.getString(uuid + ".prefixOn") == null) {
                playerStats.setPrefixOn(true);
            } else {
                playerStats.setPrefixOn(dataSection.getBoolean(uuid + ".prefixOn"));
            }

            playerStats.setPrestige(dataSection.getInt(uuid + ".prestige"));
            playerStatsMap.put(UUID.fromString(uuid), playerStats);
        }

    }

    public void saveData() {
        ConfigurationSection dataSection = this.data.getConfigurationSection("Data");
        Iterator<Map.Entry<UUID, PlayerStats>> var2 = playerStatsMap.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<UUID, PlayerStats> entry = var2.next();
            dataSection.set(((UUID)entry.getKey()).toString() + ".rank", ((PlayerStats)entry.getValue()).getRank().getKey());
            dataSection.set(((UUID)entry.getKey()).toString() + ".rankName", ((PlayerStats)entry.getValue()).getRank().getName());
            dataSection.set(((UUID)entry.getKey()).toString() + ".prefixOn", ((PlayerStats)entry.getValue()).isPrefixOn());
            dataSection.set(((UUID)entry.getKey()).toString() + ".prestige", ((PlayerStats)entry.getValue()).getPrestige());
        }

        try {
            this.data.save(this.dataFile);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                econ = (Economy)rsp.getProvider();
                return econ != null;
            }
        }
    }
}
