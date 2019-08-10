package net.spicapvp.core.rank.command;

import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "rank setsuffix", permission = "spicaCore.admin.rank", async = true)
public class RankSetSuffixCommand {

	public void execute(CommandSender sender, Rank rank, String suffix) {
		if (rank == null) {
			sender.sendMessage(Style.RED + "A rank with that name does not exist.");
			return;
		}

		rank.setSuffix(Style.translate(suffix));
		rank.save();

		sender.sendMessage(Style.GREEN + "You updated the rank's suffix.");
	}

}
