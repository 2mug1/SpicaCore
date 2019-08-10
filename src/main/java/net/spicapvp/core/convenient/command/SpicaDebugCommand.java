package net.spicapvp.core.convenient.command;

import net.spicapvp.core.SpicaCore;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "spicaCore debug", permission = "spicaCore.admin")
public class SpicaDebugCommand {

	public void execute(CommandSender sender) {
		SpicaCore.get().setDebug(!SpicaCore.get().isDebug());
		sender.sendMessage("Debug: " + SpicaCore.get().isDebug());
	}

}
