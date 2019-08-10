package net.spicapvp.core.profile.staff.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.network.packet.staff.PacketStaffChat;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import org.bukkit.entity.Player;

@CommandMeta(label = { "staffchat", "sc" }, permission = "spicaCore.staff")
public class StaffChatCommand {

	public void execute(Player player) {
		Profile profile = Profile.getProfiles().get(player.getUniqueId());
		profile.getStaffOptions().staffChatModeEnabled(!profile.getStaffOptions().staffChatModeEnabled());

		player.sendMessage(profile.getStaffOptions().staffChatModeEnabled() ?
				Style.GREEN + "You are now talking in staff chat." : Style.RED + "You are no longer talking in staff chat.");
	}

	public void execute(Player player, String message) {
		Profile profile = Profile.getProfiles().get(player.getUniqueId());

		if (!profile.getStaffOptions().staffModeEnabled()) {
			player.sendMessage(Style.RED + "You are not in staff mode.");
			return;
		}

		SpicaCore.get().getPidgin().sendPacket(new PacketStaffChat(player.getDisplayName(),
				SpicaCore.get().getMainConfig().getString("SERVER_NAME"), message));
	}

}
