package net.spicapvp.core.nametag.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.nametag.packet.PacketResetSuffix;
import net.spicapvp.core.profile.Profile;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "resetsuffix", async = true, permission = "spicaCore.staff.resetsuffix")
public class ResetSuffixCommand {

    public void execute(CommandSender sender, @CPL("player") Profile profile) {
        sender.sendMessage(profile.getUsername() + " のSuffixをリセットしました");

        SpicaCore.get().getPidgin().sendPacket(new PacketResetSuffix(profile.getUuid()));
    }
}
