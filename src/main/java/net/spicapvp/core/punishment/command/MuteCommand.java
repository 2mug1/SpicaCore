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
import java.util.UUID;

import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "mute", permission = "spicaCore.staff.mute", async = true)
public class MuteCommand {

	public void execute(CommandSender sender, @CPL("player") Profile profile, Duration duration, String reason) {
		if (profile == null || !profile.isLoaded()) {
			sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		if (profile.getActivePunishmentByType(PunishmentType.MUTE) != null) {
			sender.sendMessage(Style.RED + "That player is already muted.");
			return;
		}

		if (duration.getValue() == -1) {
			sender.sendMessage(Style.RED + "That duration is not valid.");
			sender.sendMessage(Style.RED + "Example: [perm/1y1m1w1d]");
			return;
		}

		String staffName = sender instanceof Player ? Profile.getProfiles().get(((Player) sender)
				.getUniqueId()).getColoredUsername() : Style.DARK_RED + "Console";

		Punishment punishment = new Punishment(UUID.randomUUID(), PunishmentType.MUTE, System.currentTimeMillis(),
				reason, duration.getValue());

		if (sender instanceof Player) {
			punishment.setAddedBy(((Player) sender).getUniqueId());
		}

		profile.getPunishments().add(punishment);
		profile.save();

		Player player = profile.getPlayer();

		if (player != null) {
			String senderName = sender instanceof Player ? Profile.getProfiles().get(((Player) sender).getUniqueId()).getColoredUsername() : Style.DARK_RED + "Console";
			player.sendMessage(Style.RED + "You have been " + punishment.getContext() + " by " +
					senderName + Style.RED + ".");
			player.sendMessage(Style.RED + "The reason for this packet: " + Style.WHITE +
					punishment.getAddedReason());

			if (!punishment.isPermanent()) {
				player.sendMessage(Style.RED + "This mute will expire in " + Style.WHITE +
						punishment.getTimeRemaining() + Style.RED + ".");
			}
		}

		SpicaCore.get().getPidgin().sendPacket(new PacketBroadcastPunishment(punishment, staffName,
				profile.getColoredUsername(), profile.getUuid(), false, false));
	}

}
