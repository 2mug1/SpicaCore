package net.spicapvp.core.punishment.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.punishment.menu.PunishmentLogsMenu;
import org.bukkit.entity.Player;

@CommandMeta(label = "history",  permission = "spicaCore.staff.history", async = true)
public class LogsCommand {

    public void execute(Player player) {
        new PunishmentLogsMenu().openMenu(player, 54);
    }
}
