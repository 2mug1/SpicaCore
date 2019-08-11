package net.spicapvp.core.socket;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SpicaServerStatusAPI {

    private final static Map<String, SpicaServerStatus> servers = new HashMap<>();

    public static void init(JavaPlugin plugin, int refreshIntervalSec){
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> servers.values().forEach(SpicaServerStatus::refresh), 0L, refreshIntervalSec * 20L);
    }

    public static void register(String name, String address, int port){
        servers.put(name, new SpicaServerStatus(address, port));
    }

    public static SpicaServerStatus getServerStatusByName(String name){
        return servers.get(name);
    }

    public static int getTotalOnline() {
        int count = 0;

        for(SpicaServerStatus status : servers.values()){
            if(status.isServerUp()){
                count+=status.getCurrentPlayers();
            }
        }

        count+= Bukkit.getOnlinePlayers().size();

        return count;
    }
}
