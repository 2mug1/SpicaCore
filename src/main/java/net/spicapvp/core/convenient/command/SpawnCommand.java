package net.spicapvp.core.convenient.command;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "spawn", permission = "spicaCore.staff.spawn")
public class SpawnCommand {

	public void execute(Player player) {
		SpicaCore.get().getConvenient().teleportToSpawn(player);
		player.sendMessage(Style.GREEN + "You teleported to this world's spawn.");
	}

}
