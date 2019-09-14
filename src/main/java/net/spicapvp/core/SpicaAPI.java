package net.spicapvp.core;

import net.spicapvp.core.profile.Economy;
import net.spicapvp.core.profile.Experience;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpicaAPI {

	public static String getServerName(){
		return SpicaCore.get().getServerName();
	}

	public static ChatColor getColorOfPlayer(Player player) {
		Profile profile = Profile.getProfiles().get(player.getUniqueId());
		return profile == null ? ChatColor.WHITE : profile.getActiveRank().getColor();
	}

	public static String getColoredName(Player player) {
		Profile profile = Profile.getProfiles().get(player.getUniqueId());
		return (profile == null ? ChatColor.WHITE : profile.getActiveRank().getColor()) + player.getName();
	}

	public static Rank getRankOfPlayer(Player player) {
		Profile profile = Profile.getProfiles().get(player.getUniqueId());
		return profile == null ? Rank.getDefaultRank() : profile.getActiveRank();
	}

	public static Profile getProfileByPlayer(Player player) {
		Profile profile = Profile.getProfiles().get(player.getUniqueId());
		return profile == null ? new Profile(player.getName(), player.getUniqueId()) : profile;
	}

	public static Profile getProfileByUUID(UUID uuid) {
		Profile profile = Profile.getProfiles().get(uuid);
		return profile == null ? new Profile("", uuid) : profile;
	}

	public static Experience getExperienceOfPlayer(Player player){
		return getProfileByPlayer(player).getExperience();
	}

	public static Economy getEconomyOfPlayer(Player player){
		return getProfileByPlayer(player).getEconomy();
	}

	public static Rank getRankOfPlayerByUUID(UUID uuid) {
		return new Profile(null, uuid).getActiveRank();
	}

	public static boolean isInStaffMode(Player player) {
		Profile profile = Profile.getProfiles().get(player.getUniqueId());
		return profile != null && player.hasPermission("spicaCore.staff") && profile.getStaffOptions().staffModeEnabled();
	}

}
