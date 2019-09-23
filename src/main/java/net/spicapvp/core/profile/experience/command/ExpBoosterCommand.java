package net.spicapvp.core.profile.experience.command;

import com.qrakn.honcho.command.CPL;
import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.Locale;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.profile.experience.ExpBooster;
import net.spicapvp.core.profile.experience.packet.PacketExpBoosterApply;
import net.spicapvp.core.profile.experience.packet.PacketExpBoosterRemove;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.duration.Duration;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = {"expbooster"}, permission = "spicaCore.expbooster", subcommands = true)
public class ExpBoosterCommand {

    @CommandMeta(label = {"apply"}, permission = "spicaCore.expbooster")
    public class ExpBoosterApplyCommand extends ExpBoosterCommand {

        public void execute(CommandSender sender, @CPL("player") Profile profile, Integer increaseRate, Duration duration, String reason) {
            if (increaseRate < 2 || increaseRate > 5) {
                sender.sendMessage(ChatColor.RED + "A increaseRate can only be between 2-5.");
                return;
            }

            if (profile == null || !profile.isLoaded()) {
                sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
                return;
            }

            if (profile.getExpBooster() != null && profile.getExpBooster().isActive()) {
                sender.sendMessage(Style.RED + "That player is already boosting.");
                return;
            }

            if (duration.getValue() == -1) {
                sender.sendMessage(Style.RED + "That duration is not valid.");
                sender.sendMessage(Style.RED + "Example: [perm/1y1m1w1d]");
                return;
            }

            ExpBooster expBooster = new ExpBooster(System.currentTimeMillis(), duration.getValue(), increaseRate, reason);

            if (sender instanceof Player) {
                expBooster.setAddedBy(((Player) sender).getUniqueId());
            }

            SpicaCore.get().getPidgin().sendPacket(new PacketExpBoosterApply(profile.getUuid(), expBooster));

            sender.sendMessage(Style.GREEN + "A new exp booster" + Style.GRAY + "(" + increaseRate + "x)" + Style.GREEN + " has been applied to " + profile.getActiveRank().getColor() + profile.getUsername());
        }
    }

    @CommandMeta(label = {"remove"}, permission = "spicaCore.expbooster")
    public class ExpBoosterRemoveCommand extends ExpBoosterCommand {

        public void execute(CommandSender sender, @CPL("player") Profile profile, String reason) {
            if (profile == null || !profile.isLoaded()) {
                sender.sendMessage(Locale.COULD_NOT_RESOLVE_PLAYER.format());
                return;
            }

            if (profile.getExpBooster() == null || !profile.getExpBooster().isActive()) {
                sender.sendMessage(Style.RED + "That player isn't boosting");
                return;
            }

            PacketExpBoosterRemove packet = new PacketExpBoosterRemove(profile.getUuid(), System.currentTimeMillis(), reason);

            if (sender instanceof Player) {
                packet.setRemovedBy(((Player) sender).getUniqueId());
            }

            SpicaCore.get().getPidgin().sendPacket(packet);
        }
    }

}
