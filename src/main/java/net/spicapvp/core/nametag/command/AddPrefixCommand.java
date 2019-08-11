package net.spicapvp.core.nametag.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.nametag.packet.PacketAddPrefix;
import net.spicapvp.core.profile.Profile;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "addprefix", async = true, permission = "spicaCore.staff.addprefix")
public class AddPrefixCommand {

    public void execute(CommandSender sender, @CPL("player") Profile profile, String prefix) {
        sender.sendMessage(profile.getUsername() + " のPrefixを追加しました: " + prefix);

        SpicaCore.get().getPidgin().sendPacket(new PacketAddPrefix(profile.getUuid(), prefix));
    }
}
