package net.spicapvp.core.convenient.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.util.Style;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandMeta(label = { "broadcast", "bc" }, permission = "spicaCore.admin.broadcast")
public class BroadcastCommand {

	public void execute(CommandSender sender, String broadcast) {
		String message = broadcast.replaceAll("(&([a-f0-9l-or]))", "\u00A7$2");
		Bukkit.broadcastMessage(Style.translate("&6[Broadcast] &r") + message);
	}

}
