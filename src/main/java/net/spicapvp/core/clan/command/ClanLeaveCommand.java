package net.spicapvp.core.clan.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.network.packet.clan.PacketClanLeave;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandMeta(label = { "clan leave" }, async = true)
public class ClanLeaveCommand {

    public void execute(Player player){
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

        if(clan.getClanPlayerByUuid(player.getUniqueId()).isOwner()){
            player.sendMessage(Style.RED + "You are owner. Can't it.");
            return;
        }

        new ConfirmMenu(Style.BLUE + "Leave Clan", (TypeCallback<Boolean>) data -> {
            if(data){
                clan.getPlayers().remove(clan.getClanPlayerByUuid(player.getUniqueId()));
                clan.save();

                player.sendMessage(Style.GREEN + "You have left from clan.");

                SpicaCore.get().getPidgin().sendPacket(new PacketClanLeave(clan, player.getName()));
            }
        }, true, "Confirm", "Cancel").openMenu(player);
    }
}
