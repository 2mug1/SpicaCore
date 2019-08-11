package net.spicapvp.core.nametag;

import net.spicapvp.core.util.Style;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Iterator;

public class NameTagHandler {

    private static final String PREFIX = "nt_team_";
    private static final ChatColor[] COLORS = new ChatColor[]{
            ChatColor.RED,
            ChatColor.GREEN,
            ChatColor.BLUE,
            ChatColor.AQUA,
            ChatColor.LIGHT_PURPLE,
            ChatColor.DARK_PURPLE,
            ChatColor.GOLD,
            ChatColor.YELLOW,
    };

    private static String getTeamName(Player target) {
        return target.getName();
    }

    public static void setup(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard.equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard())) {
            scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        }

        for (ChatColor color : COLORS) {
            String teamName = getTeamName(player);
            Team team = scoreboard.getTeam(teamName);

            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }

            team.setPrefix(color.toString());

            Iterator<String> entryIterator = team.getEntries().iterator();

            while (entryIterator.hasNext()) {
                entryIterator.remove();
            }
        }

        player.setScoreboard(scoreboard);
    }

    public static void addToTeam(Player player, Player target, String prefix, String suffix, boolean showHealth) {

        Team team = player.getScoreboard().getTeam(getTeamName(target));

        if (team == null) {
            team = player.getScoreboard().registerNewTeam(getTeamName(target));
        }

        if (prefix != null) {
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
        }

        if (suffix != null) {
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
        }

        if (team.hasEntry(target.getName())) {
            return;
        }

        team.addEntry(target.getName());


        if (showHealth) {
            Objective objective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

            if (objective == null) {
                objective = player.getScoreboard().registerNewObjective("showhealth", "health");
            }

            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName(Style.RED + StringEscapeUtils.unescapeJava("\u2764"));
            objective.getScore(target.getName()).setScore((int) Math.floor(target.getHealth() / 2));
        }
    }

    public static void removeFromTeams(Player player, Player target) {
        for (Team team : player.getScoreboard().getTeams()) {
            team.removeEntry(target.getName());
        }
    }

    public static void removeHealthDisplay(Player player) {
        if (player == null) {
            return;
        }

        Objective objective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

        if (objective != null) {
            objective.unregister();
        }
    }
}