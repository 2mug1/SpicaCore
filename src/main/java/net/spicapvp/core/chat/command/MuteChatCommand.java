package net.spicapvp.core.chat.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "mutechat", permission = "spicaCore.staff.mutechat")
public class MuteChatCommand {

	public void execute(CommandSender sender) {
		SpicaCore.get().getChat().togglePublicChatMute();

		String senderName;

		if (sender instanceof Player) {
			Profile profile = Profile.getProfiles().get(((Player) sender).getUniqueId());
			senderName = profile.getActiveRank().getColor() + sender.getName();
		} else {
			senderName = ChatColor.DARK_RED + "Console";
		}

		String context = SpicaCore.get().getChat().isPublicChatMuted() ? "muted" : "unmuted";

		Bukkit.broadcastMessage(Locale.MUTE_CHAT_BROADCAST.format(context, senderName));
	}

}
