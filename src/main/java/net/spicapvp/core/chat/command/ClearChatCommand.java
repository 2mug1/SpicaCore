package net.spicapvp.core.chat.command;

import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.profile.Profile;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = { "clearchat", "cc" }, permission = "spicaCore.staff.clearchat")
public class ClearChatCommand {

	public void execute(CommandSender sender) {
		String[] strings = new String[101];

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("spicaCore.staff")) {
				if (SpicaCore.get().getMainConfig().getBoolean("CHAT.CLEAR_CHAT_FOR_STAFF")) {
					player.sendMessage(strings);
				}
			} else {
				player.sendMessage(strings);
			}
		}

		String senderName;

		if (sender instanceof Player) {
			Profile profile = Profile.getProfiles().get(((Player) sender).getUniqueId());
			senderName = profile.getActiveRank().getColor() + sender.getName();
		} else {
			senderName = ChatColor.DARK_RED + "Console";
		}

		Bukkit.broadcastMessage(Locale.CLEAR_CHAT_BROADCAST.format(senderName));
	}

}
