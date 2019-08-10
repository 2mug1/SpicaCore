package net.spicapvp.core.clan.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayerProcedureStage;
import net.spicapvp.core.network.packet.clan.PacketClanInviteDenied;
import net.spicapvp.core.network.packet.clan.PacketClanJoin;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.ItemBuilder;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.callback.TypeCallback;
import net.spicapvp.core.menu.Button;
import net.spicapvp.core.menu.menus.ConfirmMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@CommandMeta(label = { "clan join" }, async = true)
public class ClanJoinCommand {

    public void execute(Player player, String clanName){
        Profile profile = Profile.getByUuid(player.getUniqueId());

        if(profile.isInClan()){
            player.sendMessage(Style.RED + "You've already in the clan.");
            return;
        }

        Clan clan = Clan.getByName(clanName);

        if(clan == null){
            player.sendMessage(Style.RED + "That name's not found.");
            return;
        }

        if(!clan.isLoaded()){
            player.sendMessage(Style.RED + "Clan data hasn't loaded yet.");
            return;
        }

        if(!clan.isInviting(player.getUniqueId())){
            player.sendMessage(Style.RED + "You're not invited from " + clan.getName());
            return;
        }

        new ConfirmMenu(Style.BLUE + "Join " + clan.getName(), (TypeCallback<Boolean>) data -> {
            if (data) {
                clan.getClanPlayerByUuid(player.getUniqueId()).setProcedureStage(ClanPlayerProcedureStage.ACCEPTED);
                clan.save();
                profile.setClanName(clanName);
                profile.save();
                player.sendMessage(Style.GREEN + "You've joined " + clan.getName());
                SpicaCore.get().getPidgin().sendPacket(new PacketClanJoin(clan, clan.getClanPlayerByUuid(player.getUniqueId())));
            }else{
                clan.getPlayers().remove(clan.getClanPlayerByUuid(player.getUniqueId()));
                clan.save();
                SpicaCore.get().getPidgin().sendPacket(new PacketClanInviteDenied(clan, player.getName()));
            }
        }, true, "Join", "Deny", new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.WOOL).durability(4).name(Style.YELLOW + "Ignore").build();
            }
            @Override
            public void clicked(Player player, ClickType clickType) {
                player.closeInventory();
            }
        }).openMenu(player);

    }
}
