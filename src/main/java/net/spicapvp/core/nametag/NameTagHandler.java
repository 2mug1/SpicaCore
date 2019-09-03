package net.spicapvp.core.nametag;

import java.util.Iterator;
import java.util.UUID;

import net.spicapvp.core.SpicaAPI;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class NameTagHandler {

    private static String getTeamName(Player player) {
        return player.getName();
    }

    public static void setTag(Player player, Player target, String prefix, String suffix, boolean showHealth) {
        Team team = player.getScoreboard().getTeam(getTeamName(target));

        if(team != null){
            team.unregister();
        }

        team = player.getScoreboard().registerNewTeam(getTeamName(target));

        if (prefix != null && !prefix.isEmpty()){
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix.replace("null", "")));
        }else{
           team.setPrefix("");
        }

        if (suffix != null && !suffix.isEmpty()){
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix.replace("null", "")));
        }else{
            team.setSuffix("");
        }

        if(!team.hasEntry(target.getName())){
            team.addEntry(target.getName());
        }

        if(player.equals(target))return;

        Profile profile = Profile.getByUuid(player.getUniqueId());

        if(profile == null)return;

        Team team2 = target.getScoreboard().getTeam(getTeamName(player));

        if(team2 != null){
            team2.unregister();
        }

        team2 = target.getScoreboard().registerNewTeam(getTeamName(player));

        if (profile.getCurrentPrefix() != null && !profile.getCurrentPrefix().isEmpty()){
            team2.setPrefix(ChatColor.translateAlternateColorCodes('&', profile.getCurrentPrefix()));
        }else{
            team2.setPrefix("");
        }

        if (profile.getCurrentSuffix() != null && !profile.getCurrentSuffix().isEmpty()){
            team2.setSuffix(ChatColor.translateAlternateColorCodes('&', profile.getCurrentSuffix()));
        }else{
            team2.setSuffix("");
        }

        if(!team2.hasEntry(player.getName())){
            team2.addEntry(player.getName());
        }

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

    public static void addToTeam(Player player, Player target, ChatColor color, boolean showHealth) {
        if(player.equals(target)){
            return;
        }

        Profile profile = Profile.getByUuid(target.getUniqueId());

        if(profile == null)return;

        Team team = player.getScoreboard().getTeam(getTeamName(target));

        if(team != null){
            team.unregister();
        }

        team = player.getScoreboard().registerNewTeam(getTeamName(target));

        team.setPrefix(color.toString());
        team.setSuffix(profile.getSuffix() == null ? "" : ChatColor.translateAlternateColorCodes('&', profile.getSuffix()));

        if(!team.hasEntry(target.getName())) {
            team.addEntry(target.getName());
        }

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
        if (player == null || target == null) {
            return;
        }

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