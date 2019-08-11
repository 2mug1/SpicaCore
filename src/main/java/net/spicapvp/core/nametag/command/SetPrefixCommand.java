package net.spicapvp.core.nametag.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.nametag.packet.PacketSetPrefix;
import net.spicapvp.core.profile.Profile;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "setprefix", async = true, permission = "spicaCore.staff.setprefix")
public class SetPrefixCommand {

    public void execute(CommandSender sender, @CPL("player") Profile profile, String prefix) {
        sender.sendMessage(profile.getUsername() + " のPrefixを上書きしました: " + prefix);

        SpicaCore.get().getPidgin().sendPacket(new PacketSetPrefix(profile.getUuid(), prefix));
    }
}
