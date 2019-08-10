package net.spicapvp.core.punishment.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.network.packet.punishment.PacketBroadcastPunishment;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.punishment.Punishment;
import net.spicapvp.core.punishment.PunishmentType;
import net.spicapvp.core.util.Style;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "unmute", permission = "spicaCore.staff.unmute", async = true)
public class UnmuteCommand {

	public void execute(CommandSender sender, @CPL("player") Profile profile, String reason) {
		if (profile == null || !profile.isLoaded()) {
			sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		if (profile.getActivePunishmentByType(PunishmentType.MUTE) == null) {
			sender.sendMessage(Style.RED + "That player is not muted.");
			return;
		}

		String staffName = sender instanceof Player ? Profile.getProfiles().get(((Player) sender)
				.getUniqueId()).getColoredUsername() : Style.DARK_RED + "Console";

		Punishment punishment = profile.getActivePunishmentByType(PunishmentType.MUTE);
		punishment.setRemovedAt(System.currentTimeMillis());
		punishment.setRemovedReason(reason);
		punishment.setRemoved(true);

		if (sender instanceof Player) {
			punishment.setRemovedBy(((Player) sender).getUniqueId());
		}

		profile.save();

		SpicaCore.get().getPidgin().sendPacket(new PacketBroadcastPunishment(punishment, staffName,
				profile.getColoredUsername(), profile.getUuid(), false, true));
	}

}
