package net.spicapvp.core.profile.register.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import org.bukkit.entity.Player;

@CommandMeta(label = "register")
public class RegisterCommand {

    public void execute(Player player, String discordID) {
        long id;
        try {
            id = Long.parseLong(discordID);
        } catch (NumberFormatException e){
            player.sendMessage(Style.RED + "DiscordIDの形式が間違っています");
            return;
        }

        Profile profile = Profile.getByUuid(player.getUniqueId());
        if(profile == null){
            player.sendMessage(Style.RED + "Profileの取得に失敗しました");
            return;
        }

        if(profile.isRegistered()){
            player.sendMessage(Style.RED + "既に登録済みです");
            return;
        }

        if(System.currentTimeMillis() <= profile.getLastRegisterCommand() + 1000*600) {
            player.sendMessage(Style.RED + "エラー: 10分後に再度お試しください");
            return;
        }

        profile.setLastRegisterCommand(System.currentTimeMillis());
    }
}
