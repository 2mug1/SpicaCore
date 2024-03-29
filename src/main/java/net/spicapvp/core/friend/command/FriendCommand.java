package net.spicapvp.core.friend.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.friend.packet.PacketFriendSendRequest;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import org.bukkit.entity.Player;


@CommandMeta(label = { "friend", "f", "friend add", "friend request" })
public class FriendCommand {

    public void execute(Player player, String username) {
        if (username.equalsIgnoreCase(player.getName())) {
            player.sendMessage(Style.RED + "Can't yourself.");
            return;
        }

        Profile other = Profile.getByUsername(username);
        Profile me = Profile.getByUuid(player.getUniqueId());

        if (other == null) {
            player.sendMessage(Style.GRAY + "Has " + username + " joined network once?");
            return;
        }

        if (other.getFriend().getAcceptedPlayersUUID().contains(player.getUniqueId().toString())) {
            player.sendMessage(other.getColoredUsername() + Style.GRAY + " is currently your friend.");

        } else if (other.getFriend().getRequestingPlayersUUID().contains(player.getUniqueId().toString()) ||
        me.getFriend().getReceivingPlayersUUID().contains(other.getUuid().toString())){
            player.sendMessage(other.getColoredUsername() + Style.GRAY + " has already sent request.");

        } else if(other.getFriend().getReceivingPlayersUUID().contains(player.getUniqueId().toString())
        || me.getFriend().getRequestingPlayersUUID().contains(other.getUuid().toString())){
            player.sendMessage(Style.GRAY + "You've already sent request.");

        }else{

            me.getFriend().addToRequestingPlayers(other.getUuid().toString());
            other.getFriend().addToReceivingPlayers(player.getUniqueId().toString());
            player.sendMessage(Style.GREEN + "Successfully you sent friend request to " + other.getColoredUsername());
            SpicaCore.get().getPidgin().sendPacket(new PacketFriendSendRequest(other.getUuid().toString(), player.getUniqueId().toString()));
        }
    }
}
