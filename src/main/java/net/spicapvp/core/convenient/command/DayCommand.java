package net.spicapvp.core.convenient.command;

import net.spicapvp.core.util.Style;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "day")
public class DayCommand {

	public void execute(Player player) {
		player.setPlayerTime(6000L, false);
		player.sendMessage(Style.GREEN + "It's now day time.");
	}

}
