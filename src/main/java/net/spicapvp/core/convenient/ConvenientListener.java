package net.spicapvp.core.convenient;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.strap.StrappedListener;
import net.spicapvp.core.util.Style;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ConvenientListener extends StrappedListener {

	private static List<String> BLOCKED_COMMANDS = Arrays.asList(
			"//calc",
			"//eval",
			"//solve",
			"/bukkit:",
			"/me",
			"/bukkit:me",
			"/minecraft:",
			"/minecraft:me",
			"/version",
			"/ver"
	);

	public ConvenientListener(SpicaCore spicaCore) {
		super(spicaCore);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommandProcess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = (event.getMessage().startsWith("/") ? "" : "/") + event.getMessage();

		for (String blockedCommand : BLOCKED_COMMANDS) {
			if (message.startsWith(blockedCommand)) {
				if (message.equalsIgnoreCase("/version") || message.equalsIgnoreCase("/ver")) {
					if (event.getPlayer().isOp()) {
						return;
					}
				}

				player.sendMessage(Style.RED + "You cannot perform this command.");
				event.setCancelled(true);
				return;
			}
		}
	}

}
