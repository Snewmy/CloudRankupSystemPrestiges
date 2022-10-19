package me.Sam.RankSystem;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class Rank {
    private String key;
    private String name;
    private String chatPrefix;
    private int position;
    private boolean lastRank;
    private int playtimeRequirementDays;
    private int playtimeRequirementHours;
    private int moneyRequirement;
    private int voteRequirement;
    private int mcmmoRequirement;
    private int jobsRequirement;
    private int animalsBredRequirement;
    private int catchFishRequirement;
    private int mobsKilledRequirement;
    private int enchantedItemsRequirement;
    private int winRaidsRequirement;
    private int enderDragonKilledRequirement;
    private List<String> rewardCommands;
    private List<String> rewardList;

    public Rank(String key, String name, String chatPrefix, int position, boolean lastRank) {
        this.name = name;
        this.chatPrefix = chatPrefix;
        this.position = position;
        this.lastRank = lastRank;
        this.playtimeRequirementDays = 0;
        this.playtimeRequirementHours = 0;
        this.moneyRequirement = 0;
        this.voteRequirement = 0;
        this.mcmmoRequirement = 0;
        this.jobsRequirement = 0;
        this.animalsBredRequirement = 0;
        this.catchFishRequirement = 0;
        this.mobsKilledRequirement = 0;
        this.enchantedItemsRequirement = 0;
        this.winRaidsRequirement = 0;
        this.enderDragonKilledRequirement = 0;
        this.key = key;
        FileConfiguration config = RankSystem.instance.getConfig();
        ArrayList rewardCommands;
        if (config.getStringList(key + ".RewardList") == null) {
            rewardCommands = new ArrayList();
            config.set(name + ".RewardList", rewardCommands);
            RankSystem.instance.saveConfig();
        }

        if (config.getStringList(key + ".RewardCommands") == null) {
            rewardCommands = new ArrayList();
            config.set(name + ".RewardCommands", rewardCommands);
            RankSystem.instance.saveConfig();
        }

        this.rewardList = config.getStringList(key + ".RewardList");
        this.rewardCommands = config.getStringList(key + ".RewardCommands");
        this.setVoteRequirement(config.getInt(".Requirements." + key + ".Votes"));
        this.setMoneyRequirement(config.getInt(".Requirements." + key + ".Money"));
        RankSystem.instance.saveConfig();
        RankSystem.ranks.put(key, this);
    }

    public boolean isLastRank() {
        return this.lastRank;
    }

    public int getMoneyRequirement() {
        return this.moneyRequirement;
    }

    public int getPlaytimeRequirementDays() {
        return this.playtimeRequirementDays;
    }

    public int getPlaytimeRequirementHours() {
        return this.playtimeRequirementHours;
    }

    public int getPosition() {
        return this.position;
    }

    public int getVoteRequirement() {
        return this.voteRequirement;
    }

    public List<String> getRewardCommands() {
        return this.rewardCommands;
    }

    public List<String> getRewardList() {
        return this.rewardList;
    }

    public String getChatPrefix() {
        return this.chatPrefix;
    }

    public String getName() {
        return this.name;
    }

    public String getKey() {
        return this.key;
    }

    public int getAnimalsBredRequirement() {
        return this.animalsBredRequirement;
    }

    public int getCatchFishRequirement() {
        return this.catchFishRequirement;
    }

    public int getEnchantedItemsRequirement() {
        return this.enchantedItemsRequirement;
    }

    public int getEnderDragonKilledRequirement() {
        return this.enderDragonKilledRequirement;
    }

    public int getJobsRequirement() {
        return this.jobsRequirement;
    }

    public int getMcmmoRequirement() {
        return this.mcmmoRequirement;
    }

    public int getMobsKilledRequirement() {
        return this.mobsKilledRequirement;
    }

    public int getWinRaidsRequirement() {
        return this.winRaidsRequirement;
    }

    public void setAnimalsBredRequirement(int animalsBredRequirement) {
        this.animalsBredRequirement = animalsBredRequirement;
    }

    public void setCatchFishRequirement(int catchFishRequirement) {
        this.catchFishRequirement = catchFishRequirement;
    }

    public void setEnchantedItemsRequirement(int enchantedItemsRequirement) {
        this.enchantedItemsRequirement = enchantedItemsRequirement;
    }

    public void setEnderDragonKilledRequirement(int enderDragonKilledRequirement) {
        this.enderDragonKilledRequirement = enderDragonKilledRequirement;
    }

    public void setJobsRequirement(int jobsRequirement) {
        this.jobsRequirement = jobsRequirement;
    }

    public void setMcmmoRequirement(int mcmmoRequirement) {
        this.mcmmoRequirement = mcmmoRequirement;
    }

    public void setMobsKilledRequirement(int mobsKilledRequirement) {
        this.mobsKilledRequirement = mobsKilledRequirement;
    }

    public void setMoneyRequirement(int moneyRequirement) {
        this.moneyRequirement = moneyRequirement;
    }

    public void setPlaytimeRequirementDays(int playtimeRequirementDays) {
        this.playtimeRequirementDays = playtimeRequirementDays;
    }

    public void setPlaytimeRequirementHours(int playtimeRequirementHours) {
        this.playtimeRequirementHours = playtimeRequirementHours;
    }

    public void setVoteRequirement(int voteRequirement) {
        this.voteRequirement = voteRequirement;
    }

    public void setWinRaidsRequirement(int winRaidsRequirement) {
        this.winRaidsRequirement = winRaidsRequirement;
    }

    public void setRewardCommands(List<String> list) {
        this.rewardCommands = list;
    }

    public void setRewardList(List<String> list) {
        this.rewardList = list;
    }
}
