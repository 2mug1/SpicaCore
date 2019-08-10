package net.spicapvp.core.punishment.command;

import com.qrakn.honcho.command.CPL;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.network.packet.punishment.PacketBroadcastPunishment;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.punishment.Punishment;
import net.spicapvp.core.punishment.PunishmentType;
import net.spicapvp.core.util.Style;

import java.util.UUID;

import com.qrakn.honcho.command.CommandMeta;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "warn", permission = "spicaCore.staff.warn", async = true)
public class WarnCommand {

	public void execute(CommandSender sender, @CPL("player") Profile profile, String reason) {
		if (profile == null || !profile.isLoaded()) {
			sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		String staffName = sender instanceof Player ? Profile.getProfiles().get(((Player) sender)
				.getUniqueId()).getColoredUsername() : Style.DARK_RED + "Console";

		Punishment punishment = new Punishment(UUID.randomUUID(), PunishmentType.WARN, System.currentTimeMillis(),
				reason, -1);

		if (sender instanceof Player) {
			punishment.setAddedBy(((Player) sender).getUniqueId());
		}

		profile.getPunishments().add(punishment);
		profile.save();

		Player player = profile.getPlayer();

		if (player != null) {
			String senderName = sender instanceof Player ? Profile.getProfiles().get(((Player) sender).getUniqueId()).getColoredUsername() : Style.DARK_RED + "Console";
			player.sendMessage(Style.RED + "You have been warned by " + senderName + Style.RED + ".");
			player.sendMessage(Style.RED + "The reason for this punishment: " + Style.WHITE + punishment.getAddedReason());
		}

		SpicaCore.get().getPidgin().sendPacket(new PacketBroadcastPunishment(punishment, staffName,
				profile.getColoredUsername(), profile.getUuid(), false, false));
	}

}
