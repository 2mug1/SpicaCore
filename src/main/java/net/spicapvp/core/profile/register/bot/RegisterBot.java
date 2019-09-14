package net.spicapvp.core.profile.register.bot;

import com.mongodb.client.model.Filters;
import lombok.Data;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.Style;
import org.apache.commons.lang.RandomStringUtils;
import org.bson.Document;
import org.bukkit.entity.Player;
import javax.security.auth.login.LoginException;
import java.awt.*;

@Data
public class RegisterBot {

    private JDA jda;

    public RegisterBot(){
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("NjE0OTIzNzM5MjEzMDcwMzM2.XWGjRA.LT5QETtm9sMFwkXym9Xy373Jd3I").setStatus(OnlineStatus.ONLINE).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void register(Player player, Profile profile, long id){
        Document document = SpicaCore.get().getMongo().getProfiles().find(Filters.and(Filters.eq("registered", true), Filters.eq("discord", id))).first();
        if(document != null){
            player.sendMessage(Style.RED + "そのDiscordアカウントは既に登録されてます");
            return;
        }

        Guild guild = getJda().getGuildById(490883854140440577L);
        Member member = guild.getMemberById(id);

        if(member == null){
            player.sendMessage(Style.RED + "Discordに参加してください: https://discord.io/spicapvp");
            return;
        }

        User user = member.getUser();
        PrivateChannel privateChannel = user.openPrivateChannel().complete();
        if(privateChannel == null){
            player.sendMessage(Style.RED + "送信に失敗しました");
            return;
        }

        String token = RandomStringUtils.randomAlphanumeric(10);

        profile.setDiscord(id);
        profile.setRegisterToken(token);
        profile.save();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.CYAN);
        builder.setAuthor("Hey. " + player.getName() + "!");
        builder.setDescription("ユーザー登録の準備が整いました: https://spicapvp.net/signup/" + token);

        privateChannel.sendMessage(builder.build()).queue();

        player.sendMessage(Style.GREEN + "Discordに登録用ページURLを送信しました");
    }
}
