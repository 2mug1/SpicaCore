package net.spicapvp.core.punishment.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.punishment.packet.PacketClearPunishments;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.duration.Duration;
import org.bukkit.command.CommandSender;

@CommandMeta(label = {"clearpunishments", "cp"}, permission = "spicaCore.staff.clearpunishments")
public class ClearPunishmentsCommand {

    public void execute(CommandSender sender, @CPL("player") Profile profile) {
        if (profile == null || !profile.isLoaded()) {
            sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
            return;
        }

        profile.getPunishments().clear();
        profile.save();

        SpicaCore.get().getPidgin().sendPacket(new PacketClearPunishments(profile.getUuid()));

        sender.sendMessage(Style.GREEN + profile.getUsername() + "'s punishment history has been all removed.");
    }
}
