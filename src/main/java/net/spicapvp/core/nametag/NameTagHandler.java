package net.spicapvp.core.nametag;

import net.spicapvp.core.profile.Profile;
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

    private static String getTeamName(Player player) {
        return player.getName();
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

    public static void addToTeam(Player player, Player other, ChatColor color, boolean showHealth) {
        if (player.equals(other)) {
            return;
        }

        Team team = player.getScoreboard().getTeam(getTeamName(other));

        if (team == null) {
            team = player.getScoreboard().registerNewTeam(getTeamName(other));

            Profile profile = Profile.getByUuid(other.getUniqueId());

            if(profile == null)return;

            team.setPrefix((profile.isInClan() ? profile.getClan().getStyleTag() + " " : "") + color.toString());
        }

        if (team.hasEntry(other.getName())) {
            return;
        }

        removeFromTeams(player, other);

        team.addEntry(other.getName());

        if (showHealth) {
            Objective objective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

            if (objective == null) {
                objective = player.getScoreboard().registerNewObjective("showhealth", "health");
            }

            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName(Style.RED + StringEscapeUtils.unescapeJava("\u2764"));
            objective.getScore(other.getName()).setScore((int) Math.floor(other.getHealth() / 2));
        }
    }

    public static void addToTeam(Player player, Player other, ChatColor prefix, String suffix, boolean showHealth) {
        if (player.equals(other)) {
            return;
        }

        Team team = player.getScoreboard().getTeam(getTeamName(other));

        if (team == null) {
            team = player.getScoreboard().registerNewTeam(getTeamName(other));

            Profile profile = Profile.getByUuid(other.getUniqueId());

            if(profile == null)return;

            team.setPrefix((profile.isInClan() ? profile.getClan().getStyleTag() + " " : "") + prefix.toString());

            if(suffix != null){
                team.setSuffix(suffix);
            }
        }

        if (team.hasEntry(other.getName())) {
            return;
        }

        removeFromTeams(player, other);

        team.addEntry(other.getName());

        if (showHealth) {
            Objective objective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

            if (objective == null) {
                objective = player.getScoreboard().registerNewObjective("showhealth", "health");
            }

            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName(Style.RED + StringEscapeUtils.unescapeJava("\u2764"));
            objective.getScore(other.getName()).setScore((int) Math.floor(other.getHealth() / 2));
        }
    }

    public static void removeFromTeams(Player player, Player other) {
        if (player == null || other == null) {
            return;
        }

        if (player.equals(other)) {
            return;
        }

        for (Team team : player.getScoreboard().getTeams()) {
            team.removeEntry(other.getName());
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
