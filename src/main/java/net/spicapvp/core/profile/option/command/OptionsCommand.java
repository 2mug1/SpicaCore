package net.spicapvp.core.profile.option.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.profile.option.menu.ProfileOptionsMenu;
import org.bukkit.entity.Player;

@CommandMeta(label = { "options", "settings" })
public class OptionsCommand {

	public void execute(Player player) {
		new ProfileOptionsMenu().openMenu(player);
	}

}
