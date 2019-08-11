package net.spicapvp.core.rank.command;

import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "packet setprefix", permission = "spicaCore.admin.packet", async = true)
public class RankSetPrefixCommand {

	public void execute(CommandSender sender, Rank rank, String prefix) {
		if (rank == null) {
			sender.sendMessage(Style.RED + "A packet with that name does not exist.");
			return;
		}

		rank.setPrefix(Style.translate(prefix));
		rank.save();

		sender.sendMessage(Style.GREEN + "You updated the packet's prefix.");
	}

}
