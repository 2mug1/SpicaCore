package net.spicapvp.core.convenient.command;

import net.spicapvp.core.util.LocationUtil;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "loc", permission = "spicaCore.admin.loc")
public class LocationCommand {

	public void execute(Player player) {
		player.sendMessage(LocationUtil.serialize(player.getLocation()));
		System.out.println(LocationUtil.serialize(player.getLocation()));
	}

}
