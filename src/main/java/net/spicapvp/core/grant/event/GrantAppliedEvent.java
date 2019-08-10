package net.spicapvp.core.grant.event;

import net.spicapvp.core.grant.Grant;
import net.spicapvp.core.util.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class GrantAppliedEvent extends BaseEvent {

	private Player player;
	private Grant grant;

}
