package net.spicapvp.core.convenient.command;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.util.BukkitReflection;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "setslots", async = true, permission = "spicaCore.admin.setslots")
public class SetSlotsCommand {

	public void execute(CommandSender sender, int slots) {
		BukkitReflection.setMaxPlayers(SpicaCore.get().getServer(), slots);
		sender.sendMessage(Style.GOLD + "You set the max slots to " + slots + ".");
	}

}
