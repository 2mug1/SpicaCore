package net.spicapvp.core.nametag.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.nametag.packet.PacketResetPrefix;
import net.spicapvp.core.profile.Profile;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "resetprefix", async = true, permission = "spicaCore.staff.resetprefix")
public class ResetPrefixCommand {

    public void execute(CommandSender sender, @CPL("player") Profile profile) {
        sender.sendMessage(profile.getUsername() + " のPrefixをリセットしました");

        SpicaCore.get().getPidgin().sendPacket(new PacketResetPrefix(profile.getUuid()));
    }
}
