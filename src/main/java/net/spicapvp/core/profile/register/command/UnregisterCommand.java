package net.spicapvp.core.profile.register.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import org.bukkit.entity.Player;

@CommandMeta(label = "unregister")
public class UnregisterCommand {

    public void execute(Player player) {
        Profile profile = Profile.getByUuid(player.getUniqueId());

        if(!profile.isRegistered()){
            player.sendMessage(Style.RED + "Not registered.");
            return;
        }

        profile.setRegistered(false);
        profile.setRegisterToken(null);
        profile.setDiscord(0);
        profile.setPassword(null);
        profile.save();

        player.sendMessage(Style.AQUA + "登録を解除しました.");
    }
}
