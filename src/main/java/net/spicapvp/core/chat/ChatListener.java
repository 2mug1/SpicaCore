package net.spicapvp.core.chat;

import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.strap.StrappedListener;
import net.spicapvp.core.chat.event.ChatAttemptEvent;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends StrappedListener {

	public ChatListener(SpicaCore spicaCore) {
		super(spicaCore);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		ChatAttempt chatAttempt = spicaCore.getChat().attemptChatMessage(event.getPlayer(), event.getMessage());
		ChatAttemptEvent chatAttemptEvent = new ChatAttemptEvent(event.getPlayer(), chatAttempt, event.getMessage());

		spicaCore.getServer().getPluginManager().callEvent(chatAttemptEvent);

		if (!chatAttemptEvent.isCancelled()) {
			switch (chatAttempt.getResponse()) {
				case ALLOWED: {
					event.setFormat("%1$s" + Style.RESET + ": %2$s");
				}
				break;
				case MESSAGE_FILTERED: {
					event.setCancelled(true);
					chatAttempt.getFilterFlagged().punish(event.getPlayer());
				}
				break;
				case PLAYER_MUTED: {
					event.setCancelled(true);

					if (chatAttempt.getPunishment().isPermanent()) {
						event.getPlayer().sendMessage(Style.RED + "You are muted for forever.");
					} else {
						event.getPlayer().sendMessage(Style.RED + "You are muted for another " +
								chatAttempt.getPunishment().getTimeRemaining() + ".");
					}
				}
				break;
				case CHAT_MUTED: {
					event.setCancelled(true);
					event.getPlayer().sendMessage(Style.RED + "The public chat is currently muted.");
				}
				break;
				case CHAT_DELAYED: {
					event.setCancelled(true);
					event.getPlayer().sendMessage(Locale.CHAT_DELAYED.format(
							TimeUtil.millisToSeconds((int) chatAttempt.getValue())) + " seconds");
				}
				break;
			}
		}
	}

}
