package net.spicapvp.core.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.spicapvp.core.SpicaCore;
import org.bukkit.entity.Player;

public class BungeeUtil {

    public static void connect(Player player, String serverName) {
        player.sendMessage(Style.AQUA + "Connecting to " + serverName + "...");
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(serverName);
        player.sendPluginMessage(SpicaCore.get(), "BungeeCord", output.toByteArray());
    }
}
