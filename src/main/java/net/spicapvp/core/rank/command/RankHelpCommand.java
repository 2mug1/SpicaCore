package net.spicapvp.core.rank.command;

import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = { "packet", "packet help" }, permission = "spicaCore.admin.packet")
public class RankHelpCommand {

	private static final String[][] HELP;

	static {
		HELP = new String[][]{
				new String[]{ "ranks", "List all existing ranks" },
				new String[]{ "packet create <name>", "Create a new packet" },
				new String[]{ "packet delete <packet>", "Delete an existing packet" },
				new String[]{ "packet setcolor <packet> <color>", "Set a packet's color" },
				new String[]{ "packet setprefix <packet> <prefix>", "Set a packet's prefix" },
				new String[]{ "packet setsuffix <packet> <suffix>", "Set a packet's suffix" },
				new String[]{ "packet setweight <packet> <weight>", "Set a packet's weight" },
				new String[]{ "packet addperm <packet> <permission>", "Add a permission to a packet" },
				new String[]{ "packet delperm <packet> <permission>", "Remove a permission from a packet" },
				new String[]{ "packet inherit <parent> <child>", "Make a parent packet inherit a child packet" },
				new String[]{ "packet uninherit <parent> <child>", "Make a parent packet uninherit a child packet" }
		};
	}

	public void execute(CommandSender sender) {
		sender.sendMessage(Style.CHAT_BAR);
		sender.sendMessage(Style.GOLD + "Rank Help");

		for (String[] help : HELP) {
			sender.sendMessage(Style.BLUE + help[0] + Style.GRAY + " - " + Style.RESET + help[1]);
		}

		sender.sendMessage(Style.CHAT_BAR);
	}

}
