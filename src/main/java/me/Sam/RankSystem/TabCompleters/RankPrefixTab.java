package me.Sam.RankSystem.TabCompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RankPrefixTab implements TabCompleter {
    public RankPrefixTab() {
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> commands = new ArrayList();
        List<String> completions = new ArrayList();
        if (cmd.getName().equalsIgnoreCase("rankprefix") && args.length == 1) {
            commands.add("on");
            commands.add("off");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}
