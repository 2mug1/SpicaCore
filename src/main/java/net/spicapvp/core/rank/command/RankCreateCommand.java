package net.spicapvp.core.rank.command;

import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "rank create", permission = "spicaCore.admin.rank", async = true)
public class RankCreateCommand {

	public void execute(CommandSender sender, String name) {
		if (Rank.getRankByDisplayName(name) != null) {
			sender.sendMessage(Style.RED + "A packet with that name already exists.");
			return;
		}

		Rank rank = new Rank(name);
		rank.save();

		sender.sendMessage(Style.GREEN + "You created a new rank.");
	}

}
