package net.spicapvp.core.socket;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpicaServerStatusAPI {

    @Getter
    private final static Map<String, SpicaServerStatus> servers = new HashMap<>();

    private static int totalOnline = 0;

    public static void init(JavaPlugin plugin) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            servers.values().forEach(SpicaServerStatus::refresh);
            int count = servers.values().stream().filter(SpicaServerStatus::isServerUp).mapToInt(SpicaServerStatus::getCurrentPlayers).sum();
            count += Bukkit.getOnlinePlayers().size();
            totalOnline = count;
        }, 0L, 60L);
    }

    public static void register(String name, String address, int port) {
        servers.put(name, new SpicaServerStatus(address, port));
    }

    public static SpicaServerStatus getServerStatusByName(String name) {
        return servers.get(name);
    }

    public static int getTotalOnline() {
        return totalOnline;
    }

    public static Map<String, SpicaServerStatus> getFilteredServers(String filterName) {
        return getFilteredServers(filterName).entrySet().stream().filter(entry -> entry.getValue().isServerUp()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, SpicaServerStatus> getFilteredOnlineServers(String filterName) {
        return getFilteredServers(filterName).entrySet().stream().filter(entry -> entry.getValue().isServerUp()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static int getPlayersByFilteredOnlineServers(String filterName) {
        return getFilteredOnlineServers(filterName).values().stream().mapToInt(SpicaServerStatus::getCurrentPlayers).sum();
    }
}
