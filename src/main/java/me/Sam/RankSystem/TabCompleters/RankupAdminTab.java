package me.Sam.RankSystem.TabCompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RankupAdminTab implements TabCompleter {
    public RankupAdminTab() {
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> commands = new ArrayList();
        List<String> completions = new ArrayList();
        if (cmd.getName().equalsIgnoreCase("rankupadmin") && sender instanceof Player p) {
            if (!p.hasPermission("SamsRankSystem.admin")) {
                return completions;
            }

            if (args.length == 1) {
                commands.add("check");
                commands.add("promote");
                commands.add("demote");
                commands.add("reload");
                StringUtil.copyPartialMatches(args[0], commands, completions);
            } else if (args.length == 2) {
                Iterator var8 = Bukkit.getOnlinePlayers().iterator();

                while(var8.hasNext()) {
                    Player player = (Player)var8.next();
                    commands.add(player.getName());
                }

                StringUtil.copyPartialMatches(args[1], commands, completions);
            }
        }

        Collections.sort(completions);
        return completions;
    }
}
