package net.spicapvp.core.clan.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.clan.ClanPlayerProcedureStage;
import net.spicapvp.core.clan.ClanPlayerRole;
import net.spicapvp.core.network.packet.clan.PacketClanInvite;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandMeta(label = { "clan invite" }, async = true)
public class ClanInviteCommand {

    public void execute(Player player, String username){
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

        if(!clan.getClanPlayerByUuid(player.getUniqueId()).isMoreThanLeader()){
            player.sendMessage(Style.RED + "You are not owner or leader.");
            return;
        }

        Profile other = Profile.getByUsername(username);

        if(other == null){
            player.sendMessage(Style.GRAY + "Has " + username + " joined network once?");
            return;
        }

        if(clan.isInviting(other.getUuid())){
            player.sendMessage(Style.RED + other.getUsername() + " has already been invited.");
            return;
        }

        if(clan.isMember(other.getUuid())){
            player.sendMessage(Style.RED + other.getUsername() + " has already in your clan.");
            return;
        }

        new ConfirmMenu(Style.BLUE + "Invite " + other.getUsername(), (TypeCallback<Boolean>) data -> {
            if(data){
                clan.getPlayers().add(new ClanPlayer(other.getUuid(), other.getUsername(), ClanPlayerRole.Regular, ClanPlayerProcedureStage.INVITING));
                clan.save();
                player.sendMessage(Style.GREEN + "You have been invited " + username + " to your clan.");

                SpicaCore.get().getPidgin().sendPacket(new PacketClanInvite(clan, clan.getClanPlayerByUuid(other.getUuid())));
            }
        }, true, "Invite", "Cancel").openMenu(player);
    }
}
