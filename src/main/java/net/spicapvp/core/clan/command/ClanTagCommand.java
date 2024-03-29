package net.spicapvp.core.clan.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandMeta(label = { "clan tag" }, async = true)
public class ClanTagCommand {

    public void execute(Player player, String tag){
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

        if(!clan.getClanPlayerByUuid(player.getUniqueId()).isOwner()){
            player.sendMessage(Style.RED + "You are not owner.");
            return;
        }

        if(tag.length() > 6){
            player.sendMessage(Style.RED + "Clan tag length is up to 6 / クランタグは最大6文字まで");
            return;
        }

        new ConfirmMenu(Style.BLUE + "Change Tag", (TypeCallback<Boolean>) data -> {
            if(data){
                clan.setTag(tag);
                clan.save();

                player.sendMessage(Style.GREEN + "Your clan tag has been changed to '" + clan.getTag() + "'");
            }
        }, true, "Confirm", "Cancel").openMenu(player);
    }
}
