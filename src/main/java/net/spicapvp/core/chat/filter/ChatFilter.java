package net.spicapvp.core.chat.filter;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.strap.Strapped;
import org.bukkit.entity.Player;

public abstract class ChatFilter extends Strapped {

	private String command;

	public ChatFilter(SpicaCore spicaCore, String command) {
		super(spicaCore);

		this.command = command;
	}

	public abstract boolean isFiltered(String message, String[] words);

	public void punish(Player player) {
		if (command != null) {
			spicaCore.getServer().dispatchCommand(spicaCore.getServer().getConsoleSender(), command
					.replace("{player}", player.getName())
					.replace("{player-uuid}", player.getUniqueId().toString()));
		}
	}

}
