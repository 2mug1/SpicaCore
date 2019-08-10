package net.spicapvp.core.convenient.command;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "setspawn", permission = "spicaCore.admin.setspawn")
public class SetSpawnCommand {

	public void execute(Player player) {
		SpicaCore.get().getConvenient().setSpawn(player.getLocation());
		player.sendMessage(Style.GREEN + "You updated this world's spawn.");
	}

}
