package net.spicapvp.core.grant.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.grant.packet.PacketAddGrant;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.grant.event.GrantAppliedEvent;
import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import net.spicapvp.core.util.duration.Duration;
import java.util.UUID;

import net.spicapvp.core.grant.Grant;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "grant", async = true, permission = "spicaCore.staff.grant")
public class GrantCommand {

	public void execute(CommandSender sender, @CPL("player") Profile profile, Rank rank, Duration duration, String reason) {
		if (rank == null) {
			sender.sendMessage(Locale.RANK_NOT_FOUND.format());
			return;
		}

		if (profile == null || !profile.isLoaded()) {
			sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
			return;
		}

		if (duration.getValue() == -1) {
			sender.sendMessage(Style.RED + "That duration is not valid.");
			sender.sendMessage(Style.RED + "Example: [perm/1y1m1w1d]");
			return;
		}

		UUID addedBy = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
		Grant grant = new Grant(UUID.randomUUID(), rank, addedBy, System.currentTimeMillis(), reason,
				duration.getValue());

		profile.getGrants().add(grant);
		profile.save();
		profile.activateNextGrant();

		SpicaCore.get().getPidgin().sendPacket(new PacketAddGrant(profile.getUuid(), grant));

		sender.sendMessage(Style.GREEN + "You applied a `{packet}` packet to `{player}` for {time-remaining}."
				.replace("{packet}", rank.getDisplayName())
				.replace("{player}", profile.getUsername())
				.replace("{time-remaining}", duration.getValue() == Integer.MAX_VALUE ? "forever"
						: TimeUtil.millisToRoundedTime(duration.getValue())));

		Player player = profile.getPlayer();

		if (player != null) {
			new GrantAppliedEvent(player, grant).call();
		}
	}

}
