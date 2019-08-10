package net.spicapvp.core.clan.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.network.packet.clan.PacketClanDisband;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandMeta(label = { "clan disband" }, async = true)
public class ClanDisbandCommand {

    public void execute(Player player, String reason){
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

        new ConfirmMenu(Style.BLUE + "Disband Clan", (TypeCallback<Boolean>) data -> {
            if(data){
                clan.setDisbanded(true);
                clan.setDisbandedAt(System.currentTimeMillis());
                clan.save();

                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
                player.sendMessage(Style.RED + Style.BOLD + " Your clan has been disbanded.");

                SpicaCore.get().getPidgin().sendPacket(new PacketClanDisband(clan));

                clan.getPlayers().clear();
                clan.save();
            }
        }, true, "Disband", "Cancel").openMenu(player);
    }
}
