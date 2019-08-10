package net.spicapvp.core.rank.command;

import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "rank setweight", permission = "spicaCore.admin.rank", async = true)
public class RankSetWeightCommand {

	public void execute(CommandSender sender, Rank rank, String weight) {
		if (rank == null) {
			sender.sendMessage(Style.RED + "A rank with that name does not exist.");
			return;
		}

		try {
			Integer.parseInt(weight);
		} catch (Exception e) {
			sender.sendMessage(Style.RED + "Invalid number.");
			return;
		}

		rank.setWeight(Integer.parseInt(weight));
		rank.save();

		sender.sendMessage(Style.GREEN + "You updated the rank's weight.");
	}

}
