package net.spicapvp.core.staff.packet;

import com.google.gson.JsonObject;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;
import lombok.Getter;

@Getter
public class PacketStaffJoinNetwork implements Packet {

	private String playerName;
	private String serverName;

	public PacketStaffJoinNetwork() {

	}

	public PacketStaffJoinNetwork(String playerName, String serverName) {
		this.playerName = playerName;
		this.serverName = serverName;
	}

	@Override
	public int id() {
		return 7;
	}

	@Override
	public JsonObject serialize() {
		return new JsonChain()
				.addProperty("playerName", playerName)
				.addProperty("serverName", serverName)
				.get();
	}

	@Override
	public void deserialize(JsonObject jsonObject) {
		playerName = jsonObject.get("playerName").getAsString();
		serverName = jsonObject.get("serverName").getAsString();
	}

}
