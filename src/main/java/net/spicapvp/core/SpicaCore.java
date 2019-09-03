package net.spicapvp.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qrakn.honcho.Honcho;
import net.spicapvp.core.board.BoardManager;
import net.spicapvp.core.chat.Chat;
import net.spicapvp.core.chat.command.ClearChatCommand;
import net.spicapvp.core.chat.command.MuteChatCommand;
import net.spicapvp.core.chat.ChatListener;
import net.spicapvp.core.chat.command.SlowChatCommand;
import net.spicapvp.core.clan.ClanPlayerRole;
import net.spicapvp.core.clan.ClanPlayerRoleTypeAdapter;
import net.spicapvp.core.clan.command.*;
import net.spicapvp.core.clan.packet.*;
import net.spicapvp.core.convenient.packet.PacketClickableBroadcast;
import net.spicapvp.core.fix.EnderpearlFixListener;
import net.spicapvp.core.io.file.ConfigValidation;
import net.spicapvp.core.convenient.Convenient;
import net.spicapvp.core.convenient.ConvenientListener;
import net.spicapvp.core.io.file.type.BasicConfigurationFile;
import net.spicapvp.core.nametag.command.*;
import net.spicapvp.core.nametag.packet.*;
import net.spicapvp.core.network.NetworkPacketListener;
import net.spicapvp.core.friend.packet.PacketFriendAccepted;
import net.spicapvp.core.friend.packet.PacketFriendDelete;
import net.spicapvp.core.friend.packet.PacketFriendJoinNetwork;
import net.spicapvp.core.friend.packet.PacketFriendSendRequest;
import net.spicapvp.core.grant.packet.PacketAddGrant;
import net.spicapvp.core.grant.packet.PacketDeleteGrant;
import net.spicapvp.core.punishment.packet.PacketBroadcastPunishment;
import net.spicapvp.core.rank.packet.PacketDeleteRank;
import net.spicapvp.core.rank.packet.PacketRefreshRank;
import net.spicapvp.core.pidgin.Pidgin;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.profile.ProfileTypeAdapter;
import net.spicapvp.core.profile.option.command.OptionsCommand;
import net.spicapvp.core.profile.conversation.command.MessageCommand;
import net.spicapvp.core.profile.conversation.command.ReplyCommand;
import net.spicapvp.core.grant.command.GrantCommand;
import net.spicapvp.core.grant.command.GrantsCommand;
import net.spicapvp.core.grant.GrantListener;
import net.spicapvp.core.profile.ProfileListener;
import net.spicapvp.core.profile.option.command.ToggleGlobalChatCommand;
import net.spicapvp.core.profile.option.command.TogglePrivateMessagesCommand;
import net.spicapvp.core.profile.option.command.ToggleSoundsCommand;
import net.spicapvp.core.punishment.command.*;
import net.spicapvp.core.profile.staff.command.AltsCommand;
import net.spicapvp.core.punishment.listener.PunishmentListener;
import net.spicapvp.core.profile.staff.command.StaffModeCommand;
import net.spicapvp.core.rank.Rank;
import net.spicapvp.core.rank.RankTypeAdapter;
import net.spicapvp.core.profile.staff.command.StaffChatCommand;
import net.spicapvp.core.rank.command.RankAddPermissionCommand;
import net.spicapvp.core.rank.command.RankCreateCommand;
import net.spicapvp.core.rank.command.RankDeleteCommand;
import net.spicapvp.core.rank.command.RankHelpCommand;
import net.spicapvp.core.rank.command.RankInfoCommand;
import net.spicapvp.core.rank.command.RankInheritCommand;
import net.spicapvp.core.rank.command.RankRemovePermissionCommand;
import net.spicapvp.core.rank.command.RankSetColorCommand;
import net.spicapvp.core.rank.command.RankSetPrefixCommand;
import net.spicapvp.core.rank.command.RankSetSuffixCommand;
import net.spicapvp.core.rank.command.RankSetWeightCommand;
import net.spicapvp.core.rank.command.RankUninheritCommand;
import net.spicapvp.core.rank.command.RanksCommand;
import net.spicapvp.core.socket.SpicaServerStatusAPI;
import net.spicapvp.core.staff.packet.*;
import net.spicapvp.core.task.MenuUpdateTask;
import net.spicapvp.core.util.Style;
import net.spicapvp.core.util.adapter.ChatColorTypeAdapter;
import net.spicapvp.core.util.duration.Duration;
import net.spicapvp.core.util.duration.DurationTypeAdapter;
import net.spicapvp.core.menu.MenuListener;
import net.spicapvp.core.cache.RedisCache;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.spicapvp.core.convenient.command.*;
import net.spicapvp.core.world.WorldListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Getter
public class SpicaCore extends JavaPlugin {

	public static final Gson GSON = new Gson();
	public static final Type LIST_STRING_TYPE = new TypeToken<List<String>>() {}.getType();

	private static SpicaCore spicaCore;

	private BasicConfigurationFile mainConfig;

	private String serverName;

	private Pidgin pidgin;

	private SpicaMongo mongo;
	private JedisPool jedisPool;
	private RedisCache redisCache;

	private Convenient convenient;
	private Chat chat;

	private BoardManager boardManager;

	@Setter private boolean debug;

	private Honcho honcho;

	@Override
	public void onEnable() {
		spicaCore = this;

		honcho = new Honcho(this);

		mainConfig = new BasicConfigurationFile(this, "config");

		serverName = mainConfig.getString("SERVER_NAME");

		new ConfigValidation(mainConfig.getFile(), mainConfig.getConfiguration(), 3).check();

		loadMongo();
		loadRedis();

		redisCache = new RedisCache(this);
		convenient = new Convenient(this);
		chat = new Chat(this);

		Arrays.asList(
				new BroadcastCommand(),
				new ClearCommand(),
				new DayCommand(),
				new HealCommand(),
				new HidePlayerCommand(),
				new LocationCommand(),
				new MoreCommand(),
				new NightCommand(),
				new RenameCommand(),
				new SetSlotsCommand(),
				new SetSpawnCommand(),
				new ShowPlayerCommand(),
				new SpawnCommand(),
				new SunsetCommand(),
				new ClearChatCommand(),
				new SlowChatCommand(),
				new AltsCommand(),
				new BanCommand(),
				new CheckCommand(),
				new KickCommand(),
				new MuteCommand(),
				new UnbanCommand(),
				new UnmuteCommand(),
				new WarnCommand(),
				new GrantCommand(),
				new GrantsCommand(),
				new StaffChatCommand(),
				new StaffModeCommand(),
				new MuteChatCommand(),
				new OptionsCommand(),
				new RankAddPermissionCommand(),
				new RankCreateCommand(),
				new RankDeleteCommand(),
				new RankHelpCommand(),
				new RankInfoCommand(),
				new RankInheritCommand(),
				new RankRemovePermissionCommand(),
				new RanksCommand(),
				new RankSetColorCommand(),
				new RankSetPrefixCommand(),
				new RankSetSuffixCommand(),
				new RankSetWeightCommand(),
				new RankUninheritCommand(),
				new SpicaDebugCommand(),
				new TeleportWorldCommand(),
				new MessageCommand(),
				new ReplyCommand(),
				new ToggleGlobalChatCommand(),
				new TogglePrivateMessagesCommand(),
				new ToggleSoundsCommand(),
				new PingCommand(),
				new ListCommand(),
				new LogsCommand(),
				new ClanCommand(),
				new ClanChatCommand(),
				new ClanCreateCommand(),
				new ClanDisbandCommand(),
				new ClanInfoCommand(),
				new ClanInviteCommand(),
				new ClanJoinCommand(),
				new ClanKickCommand(),
				new ClanLeaveCommand(),
				new ClanRoleCommand(),
				new ClanTagCommand(),
				new ReportCommand(),
				new AddPrefixCommand(),
				new AddSuffixCommand(),
				new SetPrefixCommand(),
				new SetSuffixCommand(),
				new ResetPrefixCommand(),
				new ResetSuffixCommand()
		).forEach(honcho::registerCommand);

		honcho.registerTypeAdapter(Rank.class, new RankTypeAdapter());
		honcho.registerTypeAdapter(Profile.class, new ProfileTypeAdapter());
		honcho.registerTypeAdapter(Duration.class, new DurationTypeAdapter());
		honcho.registerTypeAdapter(ChatColor.class, new ChatColorTypeAdapter());
		honcho.registerTypeAdapter(ClanPlayerRole.class, new ClanPlayerRoleTypeAdapter());

		pidgin = new Pidgin("spicanetwork",
				mainConfig.getString("REDIS.HOST"),
				mainConfig.getInteger("REDIS.PORT"),
				mainConfig.getBoolean("REDIS.AUTHENTICATION.ENABLED") ?
						mainConfig.getString("REDIS.AUTHENTICATION.PASSWORD") : null
		);

		Arrays.asList(
				PacketAddGrant.class,
				PacketBroadcastPunishment.class,
				PacketDeleteGrant.class,
				PacketDeleteRank.class,
				PacketRefreshRank.class,
				PacketStaffChat.class,
				PacketStaffJoinNetwork.class,
				PacketStaffLeaveNetwork.class,
				PacketStaffSwitchServer.class,
				PacketClickableBroadcast.class,
				PacketFriendAccepted.class,
				PacketFriendDelete.class,
				PacketFriendSendRequest.class,
				PacketFriendJoinNetwork.class,
				PacketClanBroadcast.class,
				PacketClanChat.class,
				PacketClanDisband.class,
				PacketClanInvite.class,
				PacketClanInviteDenied.class,
				PacketClanJoin.class,
				PacketClanLeave.class,
				PacketClanPlayerRoleChange.class,
				PacketReportPlayer.class,
				PacketAddPrefix.class,
				PacketAddSuffix.class,
				PacketResetPrefix.class,
				PacketResetSuffix.class,
				PacketSetPrefix.class,
				PacketSetSuffix.class
		).forEach(pidgin::registerPacket);

		pidgin.registerListener(new NetworkPacketListener(this));

		loadServerStatus();

		Arrays.asList(
				new ProfileListener(this),
				new MenuListener(this),
				new ConvenientListener(this),
				new ChatListener(this),
				new GrantListener(this),
				new PunishmentListener(this),
				new WorldListener(),
				new EnderpearlFixListener()
		).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

		Rank.init();

		new BukkitRunnable() {
			@Override
			public void run() {
				for (Profile profile : Profile.getProfiles().values()) {
					profile.checkGrants();
				}
			}
		}.runTaskTimerAsynchronously(this, 20L, 20L);

		this.getServer().getScheduler().runTaskTimer(this, new MenuUpdateTask(), 20L, 20L);

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	public void setBoardManager(BoardManager manager) {
		this.boardManager = manager;
		this.boardManager.runTaskTimerAsynchronously(this, manager.getAdapter().getInterval(), manager.getAdapter().getInterval());
	}

	@Override
	public void onDisable() {
		try {
			jedisPool.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void debug(Level level, String message, Exception exception) {
		getLogger().log(level, message);
		exception.printStackTrace();
	}

	public void debug(String message) {
		if (debug) {
			broadcastOps(Style.translate("&e(Debug) &r" + message));
		}
	}


	public void debug(Player player, String message) {
		if (debug) {
			broadcastOps(Style.translate("&e(Debug) &r" + player.getDisplayName() + ": " + message));
		}
	}

	public static void broadcastOps(String message) {
		Bukkit.getOnlinePlayers().stream().filter(Player::isOp).forEach(op -> op.sendMessage(message));
	}

	public static void broadcast(String message) {
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
	}

	public static void playsound(Sound sound, float volume, float pitch) {
		Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, volume, pitch));
	}

	private void loadMongo() {
		mongo = new SpicaMongo(mainConfig);
	}

	private void loadRedis() {
		jedisPool = new JedisPool(mainConfig.getString("REDIS.HOST"), mainConfig.getInteger("REDIS.PORT"));

		if (mainConfig.getBoolean("REDIS.AUTHENTICATION.ENABLED")) {
			try (Jedis jedis = jedisPool.getResource()) {
				jedis.auth(mainConfig.getString("REDIS.AUTHENTICATION.PASSWORD"));
			}
		}
	}

	public static SpicaCore get() {
		return spicaCore;
	}

	private void loadServerStatus(){
		List<String> list = mainConfig.getStringList("SERVER_STATUS.SERVERS");

		if(list == null) return;

		if(!list.isEmpty()){
			list.forEach(data -> {

				String[] split = data.split(":");

				if(split[0] == null || split[1] == null || split[2] == null) return;

				String name = split[0];
				String address = split[1];
				int port = Integer.parseInt(split[2]);

				SpicaServerStatusAPI.register(name, address, port);

				this.getLogger().info(data + " has been added to cache for server status.");
			});
		}
	}
}
