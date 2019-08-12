package net.spicapvp.core.socket;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpicaServerStatusAPI {

    private final static Map<String, SpicaServerStatus> servers = new HashMap<>();

    public static void init(JavaPlugin plugin, int refreshIntervalSec) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> servers.values().forEach(SpicaServerStatus::refresh), 0L, refreshIntervalSec * 20L);
    }

    public static void register(String name, String address, int port) {
        servers.put(name, new SpicaServerStatus(address, port));
    }

    public static SpicaServerStatus getServerStatusByName(String name) {
        return servers.get(name);
    }

    public static int getTotalOnline() {
        int count = servers.values().stream().filter(SpicaServerStatus::isServerUp).mapToInt(SpicaServerStatus::getCurrentPlayers).sum();

        count += Bukkit.getOnlinePlayers().size();

        return count;
    }

    public static Map<String, SpicaServerStatus> getFilteredServers(String filterName) {
        return servers.entrySet().stream().filter(entry -> entry.getKey().contains(filterName)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, SpicaServerStatus> getFilteredOnlineServers(String filterName) {
        return getFilteredServers(filterName).entrySet().stream().filter(entry -> entry.getValue().isServerUp()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static int getPlayersByFilteredOnlineServers(String filterName) {
        return getFilteredOnlineServers(filterName).values().stream().mapToInt(SpicaServerStatus::getCurrentPlayers).sum();
    }
}
