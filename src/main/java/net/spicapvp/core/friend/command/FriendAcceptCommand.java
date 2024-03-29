package net.spicapvp.core.friend.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.friend.packet.PacketFriendAccepted;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandMeta(label = { "friend accept"})
public class FriendAcceptCommand {

    public void execute(Player player, String uuid) {
        UUID theUUID;
        try{
            theUUID = UUID.fromString(uuid);
        } catch (IllegalArgumentException exception){
            player.sendMessage(Style.RED + "Please enter with a UUID");
            return;
        }

        Profile other = Profile.getByUuid(theUUID);
        Profile me = Profile.getByUuid(player.getUniqueId());
        if (other.getFriend().getAcceptedPlayersUUID().contains(player.getUniqueId().toString())) {
            player.sendMessage(other.getColoredUsername() + Style.GRAY + " is already your friend.");
        } else if (other.getFriend().getRequestingPlayersUUID().contains(player.getUniqueId().toString()) && me.getFriend().getReceivingPlayersUUID().contains(other.getUuid().toString())) {
            other.getFriend().removeFromRequestingPlayers(player.getUniqueId().toString());
            me.getFriend().removeFromReceivingPlayers(other.getUuid().toString());
            other.getFriend().addToAcceptedPlayers(player.getUniqueId().toString());
            me.getFriend().addToAcceptedPlayers(other.getUuid().toString());
            player.sendMessage(Style.GREEN + "You have been accepted " + other.getColoredUsername() + Style.GREEN + "'s friend request.");
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
            SpicaCore.get().getPidgin().sendPacket(new PacketFriendAccepted(other.getUuid().toString(), player.getUniqueId().toString()));
        }else{
           player.sendMessage("That user isn't send friend request.");
        }
    }
}

