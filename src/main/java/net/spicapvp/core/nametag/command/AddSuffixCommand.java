package net.spicapvp.core.nametag.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.nametag.packet.PacketAddSuffix;
import net.spicapvp.core.profile.Profile;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "addsuffix", async = true, permission = "spicaCore.staff.addsuffix")
public class AddSuffixCommand {

    public void execute(CommandSender sender, @CPL("player") Profile profile, String suffix) {
        if((profile.getSuffix() + suffix).length() > 16){
            sender.sendMessage("Suffixは16文字以下まで");
            return;
        }

        sender.sendMessage(profile.getUsername() + " のSuffixを追加しました: " + suffix);

        SpicaCore.get().getPidgin().sendPacket(new PacketAddSuffix(profile.getUuid(), suffix));
    }
}
