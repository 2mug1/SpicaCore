package net.spicapvp.core.rank.command;

import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = { "rank removepermission", "rank removeperm", "rank deleteperm", "rank delperm" },
             permission = "spicaCore.admin.rank",
             async = true)
public class RankRemovePermissionCommand {

	public void execute(CommandSender sender, Rank rank, String permission) {
		if (!rank.removePermission(permission)) {
			sender.sendMessage(Style.RED + "That packet does not have that permission.");
		} else {
			rank.save();
			sender.sendMessage(Style.GREEN + "Successfully removed permission from packet.");
		}
	}

}
