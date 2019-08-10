package net.spicapvp.core.convenient.command;

import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "hideplayer", permission = "spicaCore.admin.hideplayer")
public class HidePlayerCommand {

	public void execute(Player player, Player target) {
		player.hidePlayer(target);
	}

}
