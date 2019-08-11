package net.spicapvp.core.nametag.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.nametag.packet.PacketSetSuffix;
import net.spicapvp.core.profile.Profile;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "setsuffix", async = true, permission = "spicaCore.staff.setsuffix")
public class SetSuffixCommand {

    public void execute(CommandSender sender, @CPL("player") Profile profile, String suffix) {
        if(suffix.length() > 16){
            sender.sendMessage("Suffixは16文字以下まで");
            return;
        }

        sender.sendMessage(profile.getUsername() + " のSuffixを設定しました: " + suffix);

        SpicaCore.get().getPidgin().sendPacket(new PacketSetSuffix(profile.getUuid(), suffix));
    }
}
