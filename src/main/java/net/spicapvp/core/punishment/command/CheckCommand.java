package net.spicapvp.core.punishment.command;

import com.qrakn.honcho.command.CPL;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.cache.RedisPlayerData;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.punishment.menu.PunishmentsListMenu;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = { "check", "c" }, permission = "spicaCore.staff.check", async = true)
public class CheckCommand {

	public void execute(Player player, @CPL("player") Profile profile) {
		if (profile == null || !profile.isLoaded()) {
			player.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		RedisPlayerData redisPlayerData = SpicaCore.get().getRedisCache().getPlayerData(profile.getUuid());

		if (redisPlayerData == null) {
			player.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		new PunishmentsListMenu(profile, redisPlayerData).openMenu(player);
	}

}
