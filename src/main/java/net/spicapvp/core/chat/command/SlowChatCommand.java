package net.spicapvp.core.chat.command;

import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.SpicaAPI;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "slowchat", permission = "spicaCore.staff.slowchat")
public class SlowChatCommand {

	public void execute(CommandSender sender) {
		SpicaCore.get().getChat().togglePublicChatDelay();

		String senderName;

		if (sender instanceof Player) {
			senderName = SpicaAPI.getColoredName((Player) sender);
		} else {
			senderName = ChatColor.DARK_RED + "Console";
		}

		String context = SpicaCore.get().getChat().getDelayTime() == 1 ? "" : "s";

		if (SpicaCore.get().getChat().isPublicChatDelayed()) {
			Bukkit.broadcastMessage(Locale.DELAY_CHAT_ENABLED_BROADCAST.format(senderName,
					SpicaCore.get().getChat().getDelayTime(), context));
		} else {
			Bukkit.broadcastMessage(Locale.DELAY_CHAT_DISABLED_BROADCAST.format(senderName));
		}
	}

	public void execute(CommandSender sender, Integer seconds) {
		if (seconds < 0 || seconds > 60) {
			sender.sendMessage(ChatColor.RED + "A delay can only be between 1-60 seconds.");
			return;
		}

		String context = seconds == 1 ? "" : "s";

		sender.sendMessage(ChatColor.GREEN + "You have updated the chat delay to " + seconds + " second" + context + ".");
		SpicaCore.get().getChat().setDelayTime(seconds);
	}

}
