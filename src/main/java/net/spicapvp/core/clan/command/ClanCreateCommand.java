package net.spicapvp.core.clan.command;


import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaAPI;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.clan.ClanPlayerProcedureStage;
import net.spicapvp.core.clan.ClanPlayerRole;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.TimeUtil;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandMeta(label = { "clan create" }, async = true)
public class ClanCreateCommand {

    public void execute(Player player, String name, String tag){
        Profile profile = Profile.getByUuid(player.getUniqueId());

        if(profile.isInClan()) {
            player.sendMessage(Style.RED + "You've already been in clan.");
            return;
        }

        if (SpicaAPI.getEconomyOfPlayer(player).getCoins() < 100) {
            player.sendMessage(Style.RED + "Needed 100 Coins to create a Clan. / クラン作成には100コイン必要です");
            return;
        }

        if(Clan.getByName(name) != null){
            player.sendMessage(Style.RED + "That name clan is already exists.");
            return;
        }

        if(name.length() > 10){
            player.sendMessage(Style.RED + "Clan name length is up to 10 / クラン名は最大10文字まで");
            return;
        }

        if(tag.length() > 6){
            player.sendMessage(Style.RED + "Clan tag length is up to 6 / クランタグは最大6文字まで");
            return;
        }

        new ConfirmMenu(Style.BLUE + "Clan Create: 100 Coins", (TypeCallback<Boolean>) data -> {
            if(data){
                Clan clan = new Clan(name, tag);
                clan.getPlayers().clear();
                clan.getPlayers().add(new ClanPlayer(player.getUniqueId(), player.getName(), ClanPlayerRole.Owner, ClanPlayerProcedureStage.ACCEPTED));
                clan.setCreatedAt(System.currentTimeMillis());
                clan.setDisbanded(false);
                clan.save();

                profile.setClanName(name);
                profile.getEconomy().removeCoin(100);
                profile.save();

                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1F, 1F);

                player.sendMessage(Style.HORIZONTAL_SEPARATOR);
                player.sendMessage("");
                player.sendMessage(Style.YELLOW + Style.BOLD + " You've been created new your clan.");
                player.sendMessage(Style.GOLD + " Name: " + Style.WHITE + clan.getName());
                player.sendMessage(Style.GOLD + " Tag: " + Style.WHITE + clan.getTag());
                player.sendMessage(Style.GOLD + " Owner: " + Style.WHITE + clan.getOwner().getName());
                player.sendMessage("");
                player.sendMessage(Style.GRAY + " Created at: " + Style.WHITE + TimeUtil.dateToString(new Date(clan.getCreatedAt()), null));
                player.sendMessage("");
                player.sendMessage(Style.HORIZONTAL_SEPARATOR);
            }
        }, true, "Create", "Cancel").openMenu(player);
    }
}
