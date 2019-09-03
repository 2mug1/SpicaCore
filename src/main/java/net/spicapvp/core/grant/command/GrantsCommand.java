package net.spicapvp.core.grant.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.Locale;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.grant.menu.GrantsListMenu;
import org.bukkit.entity.Player;

@CommandMeta(label = "grants", async = true, permission = "spicaCore.staff.grants")
public class GrantsCommand {

	public void execute(Player player, @CPL("player") Profile profile) {
		if (profile == null || !profile.isLoaded()) {
			player.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		new GrantsListMenu(profile).openMenu(player);
	}

}
