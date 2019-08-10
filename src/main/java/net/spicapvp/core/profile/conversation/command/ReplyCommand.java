package net.spicapvp.core.profile.conversation.command;

import com.qrakn.honcho.command.CommandMeta;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.profile.conversation.Conversation;
import net.spicapvp.core.util.Style;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandMeta(label = { "reply", "r" })
public class ReplyCommand {

    public void execute(Player player, String message) {
        Profile playerProfile = Profile.getByUuid(player.getUniqueId());
        Conversation conversation = playerProfile.getConversations().getLastRepliedConversation();

        if (conversation != null) {
            if (conversation.validate()) {
                conversation.sendMessage(player, Bukkit.getPlayer(conversation.getPartner(player.getUniqueId())), message);
            } else {
                player.sendMessage(Style.RED + "You can no longer reply to that player.");
            }
        } else {
            player.sendMessage(Style.RED + "You have nobody to reply to.");
        }
    }

}
