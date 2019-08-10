package net.spicapvp.core.clan.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandMeta(label = { "clan chat", "clanchat", "cc"}, async = true)
public class ClanChatCommand {

    public void execute(Player player, String message){
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
            player.sendMessage(Style.RED + "Your clan has already disbanded at " + TimeUtil.dateToString(new Date(clan.getDisbandedAt()), null));
            return;
        }

        clan.chat(message, clan.getClanPlayerByUuid(player.getUniqueId()));
    }
}
