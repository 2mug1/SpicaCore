package net.spicapvp.core.convenient.command;

import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "more", permission = "spicaCore.admin.more")
public class MoreCommand {

	public void execute(Player player) {
		if (player.getItemInHand() == null) {
			player.sendMessage(Style.RED + "There is nothing in your hand.");
			return;
		}

		player.getItemInHand().setAmount(64);
		player.updateInventory();
		player.sendMessage(Style.GREEN + "You gave yourself more of the item in your hand.");
	}

}
