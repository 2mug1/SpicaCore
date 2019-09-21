package net.spicapvp.core.punishment.command;

import com.qrakn.honcho.command.CPL;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.punishment.packet.PacketBroadcastPunishment;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.punishment.Punishment;
import net.spicapvp.core.punishment.PunishmentType;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.duration.Duration;
import com.qrakn.honcho.command.CommandMeta;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandMeta(label = "ban", permission = "spicaCore.staff.ban", async = true)
public class BanCommand {

	public void execute(CommandSender sender, @CPL("player") Profile profile, Duration duration, String reason) {
		if (profile == null || !profile.isLoaded()) {
			sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		if (profile.getActivePunishmentByType(PunishmentType.BAN) != null) {
			sender.sendMessage(Style.RED + "That player is already banned.");
			return;
		}

		if (duration.getValue() == -1) {
			sender.sendMessage(Style.RED + "That duration is not valid.");
			sender.sendMessage(Style.RED + "Example: [perm/1y1m1w1d]");
			return;
		}

		String staffName = sender instanceof Player ? Profile.getProfiles().get(((Player) sender)
				.getUniqueId()).getColoredUsername() : Style.DARK_RED + "Console";

		Punishment punishment = new Punishment(Punishment.getCurrentPunishmentSize() + 1, PunishmentType.BAN, System.currentTimeMillis(),
				reason, duration.getValue());

		if (sender instanceof Player) {
			punishment.setAddedBy(((Player) sender).getUniqueId());
		}

		profile.getPunishments().add(punishment);
		profile.save();

		SpicaCore.get().getPidgin().sendPacket(new PacketBroadcastPunishment(punishment, staffName,
				profile.getColoredUsername(), profile.getUuid(), false, false));

		Player player = profile.getPlayer();

		if (player != null) {
			new BukkitRunnable() {
				@Override
				public void run() {
					player.kickPlayer(punishment.getKickMessage());
				}
			}.runTask(SpicaCore.get());
		}
	}

}
