package net.spicapvp.core.rank.command;

import net.spicapvp.core.rank.Rank;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "packet inherit", permission = "spicaCore.admin.packet", async = true)
public class RankInheritCommand {

	public void execute(CommandSender sender, Rank parent, Rank child) {
		if (parent == null) {
			sender.sendMessage(ChatColor.RED + "A packet with that name does not exist (parent).");
			return;
		}

		if (child == null) {
			sender.sendMessage(ChatColor.RED + "A packet with that name does not exist (child).");
			return;
		}

		if (parent.canInherit(child)) {
			parent.getInherited().add(child);
			parent.save();
			sender.sendMessage(ChatColor.GREEN + "You made the parent packet " + parent.getDisplayName() +
			                   " inherit the child packet " + child.getDisplayName() + ".");
		} else {
			sender.sendMessage(ChatColor.RED + "That parent packet cannot inherit that child packet.");
		}
	}

}
