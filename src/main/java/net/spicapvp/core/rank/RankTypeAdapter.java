package net.spicapvp.core.rank;

import com.qrakn.honcho.command.adapter.CommandTypeAdapter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RankTypeAdapter implements CommandTypeAdapter {

	@Override
	public <T> T convert(String string, Class<T> type) {
		return type.cast(Rank.getRankByDisplayName(string));
	}

	@Override
	public boolean onException(Exception exception, CommandSender sender, String input) {
		return false;
	}

	@Override
	public List<String> onTabComplete(String string) {
		List<String> completed = new ArrayList<>();

		for (Rank rank : Rank.getRanks().values()) {
			if (rank.getDisplayName().toLowerCase().startsWith(string)) {
				completed.add(rank.getDisplayName());
			}
		}

		return completed;
	}
}
