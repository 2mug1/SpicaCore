package net.spicapvp.core.rank.command;

import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "rank setcolor", permission = "spicaCore.admin.rank", async = true)
public class RankSetColorCommand {

	public void execute(CommandSender sender, Rank rank, ChatColor color) {
		if (rank == null) {
			sender.sendMessage(Style.RED + "A packet with that name does not exist.");
			return;
		}

		if (color == null) {
			sender.sendMessage(Style.RED + "That color is not valid.");
			return;
		}

		rank.setColor(color);
		rank.save();

		sender.sendMessage(Style.GREEN + "You updated the packet's color.");
	}

}
