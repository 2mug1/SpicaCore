package net.spicapvp.core.rank.command;

import net.spicapvp.core.Locale;
import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "packet delete", permission = "spicaCore.admin.packet", async = true)
public class RankDeleteCommand {

	public void execute(CommandSender sender, Rank rank) {
		if (rank == null) {
			sender.sendMessage(Locale.RANK_NOT_FOUND.format());
			return;
		}

		rank.delete();

		sender.sendMessage(Style.GREEN + "You deleted the packet.");
	}

}
