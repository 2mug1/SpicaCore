package net.spicapvp.core.punishment.listener;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.strap.StrappedListener;
import net.spicapvp.core.punishment.procedure.PunishmentProcedure;
import net.spicapvp.core.punishment.procedure.PunishmentProcedureStage;
import net.spicapvp.core.punishment.procedure.PunishmentProcedureType;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PunishmentListener extends StrappedListener {

	public PunishmentListener(SpicaCore spicaCore) {
		super(spicaCore);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		if (!event.getPlayer().hasPermission("spicaCore.staff.grant")) {
			return;
		}

		PunishmentProcedure procedure = PunishmentProcedure.getByPlayer(event.getPlayer());

		if (procedure != null && procedure.getStage() == PunishmentProcedureStage.REQUIRE_TEXT) {
			event.setCancelled(true);

			if (event.getMessage().equalsIgnoreCase("cancel")) {
				PunishmentProcedure.getProcedures().remove(procedure);
				event.getPlayer().sendMessage(Style.RED + "You have cancelled the punishment procedure.");
				return;
			}

			if (procedure.getType() == PunishmentProcedureType.PARDON) {
				new ConfirmMenu(Style.YELLOW + "Pardon this punishment?", new TypeCallback<Boolean>() {
					@Override
					public void callback(Boolean data) {
						if (data) {
							procedure.getPunishment().setRemovedBy(event.getPlayer().getUniqueId());
							procedure.getPunishment().setRemovedAt(System.currentTimeMillis());
							procedure.getPunishment().setRemovedReason(event.getMessage());
							procedure.getPunishment().setRemoved(true);
							procedure.finish();

							event.getPlayer().sendMessage(Style.GREEN + "The punishment has been removed.");
						} else {
							procedure.cancel();
							event.getPlayer().sendMessage(Style.RED + "You did not confirm to pardon the punishment.");
						}
					}
				}, true) {
					@Override
					public void onClose(Player player) {
						if (!isClosedByMenu()) {
							procedure.cancel();
							event.getPlayer().sendMessage(Style.RED + "You did not confirm to pardon the punishment.");
						}
					}
				}.openMenu(event.getPlayer());
			}
		}
	}

}
