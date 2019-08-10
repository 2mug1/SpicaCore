package net.spicapvp.core.clan.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayerRole;
import net.spicapvp.core.network.packet.clan.PacketClanPlayerRoleChange;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandMeta(label = { "clan role" }, async = true)
public class ClanRoleCommand {

    public void execute(Player player, String username, ClanPlayerRole role){
        Profile profile = Profile.getByUuid(player.getUniqueId());

        if(!profile.isInClan()){
            player.sendMessage(Style.RED + "You're not in a clan.");
            return;
        }

        Clan clan = profile.getClan();

        if(!clan.isLoaded()){
            player.sendMessage(Style.RED + "Clan data hasn't loaded yet.");
            return;
        }

        if(clan.isDisbanded()){
            player.sendMessage(Style.RED + "Your clan did disband at " + TimeUtil.dateToString(new Date(clan.getDisbandedAt()), null));
            return;
        }

        if(!clan.getClanPlayerByUuid(player.getUniqueId()).isOwner()){
            player.sendMessage(Style.RED + "You are not owner.");
            return;
        }

        if(clan.getClanPlayerByUuid(player.getUniqueId()).isOwner()){
            if(username.equalsIgnoreCase(player.getName())){
                player.sendMessage(Style.RED + "You can't change role yourself.");
                return;
            }
        }

        Profile other = Profile.getByUsername(username);

        if(other == null){
            player.sendMessage(Style.GRAY + "Has " + username + " joined network once?");
            return;
        }

        if(clan.isInviting(other.getUuid())){
            player.sendMessage(Style.RED + other.getUsername() + " is inviting.");
            return;
        }

        if(!clan.isMember(other.getUuid())){
            player.sendMessage(Style.RED + other.getUsername() + " isn't in your clan.");
            return;
        }

        if(role == ClanPlayerRole.Owner){
            player.sendMessage(Style.RED + "Can't it change to owner");
            return;
        }

        new ConfirmMenu(Style.BLUE + "Change Role", (TypeCallback<Boolean>) data -> {
            if(data){
                clan.getClanPlayerByUuid(other.getUuid()).setRole(role);
                clan.save();

                player.sendMessage(Style.GREEN + other.getUsername() + "'s role has been changed to " + role.getColor() + role.name());

                SpicaCore.get().getPidgin().sendPacket(new PacketClanPlayerRoleChange(clan, clan.getClanPlayerByUuid(other.getUuid())));
            }
        }, true, "Confirm", "Cancel").openMenu(player);
    }
}
