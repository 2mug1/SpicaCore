package net.spicapvp.core.punishment;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

public class Punishment {

	public static PunishmentJsonSerializer SERIALIZER = new PunishmentJsonSerializer();
	public static PunishmentJsonDeserializer DESERIALIZER = new PunishmentJsonDeserializer();

	@Getter private final UUID uuid;
	@Getter private final PunishmentType type;
	@Getter @Setter private UUID addedBy;
	@Getter final private long addedAt;
	@Getter private final String addedReason;
	@Getter final private long duration;
	@Getter @Setter private UUID removedBy;
	@Getter @Setter private long removedAt;
	@Getter @Setter private String removedReason;
	@Getter @Setter private boolean removed;

	public Punishment(UUID uuid, PunishmentType type, long addedAt, String addedReason, long duration) {
		this.uuid = uuid;
		this.type = type;
		this.addedAt = addedAt;
		this.addedReason = addedReason;
		this.duration = duration;
	}

	public boolean isPermanent() {
		return type == PunishmentType.BLACKLIST || duration == Integer.MAX_VALUE;
	}

	public boolean hasExpired() {
		return (!isPermanent()) && (System.currentTimeMillis() >= addedAt + duration);
	}

	public String getDurationText() {
		if (removed) {
			return "Removed";
		}

		if (isPermanent()) {
			return "Permanent";
		}

		return TimeUtil.millisToRoundedTime(duration);
	}

	public String getTimeRemaining() {
		if (removed) {
			return "Removed";
		}

		if (isPermanent()) {
			return "Permanent";
		}

		if (hasExpired()) {
			return "Expired";
		}

		return TimeUtil.millisToRoundedTime((addedAt + duration) - System.currentTimeMillis());
	}

	public String getContext() {
		if (!(type == PunishmentType.BAN || type == PunishmentType.MUTE)) {
			return removed ? type.getUndoContext() : type.getContext();
		}

		if (isPermanent()) {
			return (removed ? type.getUndoContext() : "permanently " + type.getContext());
		} else {
			return (removed ? type.getUndoContext() : "temporarily " + type.getContext());
		}
	}

	public void broadcast(String sender, String target, boolean silent, boolean removed) {
		if (silent) {
			Bukkit.getOnlinePlayers().forEach(player -> {
				if (player.hasPermission("spicaCore.staff")) {
					player.sendMessage(SpicaCore.get().getMainConfig().getString("PUNISHMENTS.BROADCAST_SILENT")
					                       .replace("{context}", getContext())
					                       .replace("{target}", target)
					                       .replace("{sender}", sender)
							               .replace("{reason}",  removed ? removedReason : addedReason));
				}
			});
		} else {
			Bukkit.broadcastMessage(SpicaCore.get().getMainConfig().getString("PUNISHMENTS.BROADCAST")
			                            .replace("{context}", getContext())
			                            .replace("{target}", target)
			                            .replace("{sender}", sender)
					                    .replace("{reason}",  removed ? removedReason : addedReason));
		}
	}

	public String getKickMessage() {
		String kickMessage;

		if (type == PunishmentType.BAN) {
			kickMessage = SpicaCore.get().getMainConfig().getString("PUNISHMENTS.BAN.KICK");
			String temporary = "";

			if (!isPermanent()) {
				temporary = SpicaCore.get().getMainConfig().getString("PUNISHMENTS.BAN.TEMPORARY");
				temporary = temporary.replace("{time-remaining}", getTimeRemaining());
			}

			kickMessage = kickMessage.replace("{context}", getContext())
			                         .replace("{temporary}", temporary);
		} else if (type == PunishmentType.KICK) {
			kickMessage = SpicaCore.get().getMainConfig().getString("PUNISHMENTS.KICK.KICK")
			                  .replace("{context}", getContext())
			                  .replace("{reason}", addedReason);
		} else {
			kickMessage = null;
		}

		return Style.translate(kickMessage);
	}

	@Override
	public boolean equals(Object object) {
		return object != null && object instanceof Punishment && ((Punishment) object).uuid.equals(uuid);
	}

}
