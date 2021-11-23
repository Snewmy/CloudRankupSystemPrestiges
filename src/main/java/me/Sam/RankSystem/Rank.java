package me.Sam.RankSystem;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

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
        if (config.getStringList(key + ".RewardList") == null) {
            List<String> rewardList = new ArrayList<>();
            config.set(name + ".RewardList", rewardList);
            RankSystem.instance.saveConfig();
        }
        if (config.getStringList(key + ".RewardCommands") == null) {
            List<String> rewardCommands = new ArrayList<>();
            config.set(name + ".RewardCommands", rewardCommands);
            RankSystem.instance.saveConfig();
        }
        this.rewardList = config.getStringList(key + ".RewardList");
        this.rewardCommands = config.getStringList(key + ".RewardCommands");
        RankSystem.instance.saveConfig();
        RankSystem.ranks.put(key, this);
    }

    public boolean isLastRank() {
        return lastRank;
    }

    public int getMoneyRequirement() {
        return moneyRequirement;
    }

    public int getPlaytimeRequirementDays() {
        return playtimeRequirementDays;
    }

    public int getPlaytimeRequirementHours() {
        return playtimeRequirementHours;
    }

    public int getPosition() {
        return position;
    }

    public int getVoteRequirement() {
        return voteRequirement;
    }

    public List<String> getRewardCommands() {
        return rewardCommands;
    }

    public List<String> getRewardList() {
        return rewardList;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public int getAnimalsBredRequirement() {
        return animalsBredRequirement;
    }

    public int getCatchFishRequirement() {
        return catchFishRequirement;
    }

    public int getEnchantedItemsRequirement() {
        return enchantedItemsRequirement;
    }

    public int getEnderDragonKilledRequirement() {
        return enderDragonKilledRequirement;
    }

    public int getJobsRequirement() {
        return jobsRequirement;
    }

    public int getMcmmoRequirement() {
        return mcmmoRequirement;
    }

    public int getMobsKilledRequirement() {
        return mobsKilledRequirement;
    }

    public int getWinRaidsRequirement() {
        return winRaidsRequirement;
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
