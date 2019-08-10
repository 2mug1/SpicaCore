package net.spicapvp.core.network.packet.network;

import com.google.gson.JsonObject;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;
import lombok.Getter;

@Getter
public class PacketStaffLeaveNetwork implements Packet {

	private String playerName;
	private String serverName;

	public PacketStaffLeaveNetwork() {

	}

	public PacketStaffLeaveNetwork(String playerName, String serverName) {
		this.playerName = playerName;
		this.serverName = serverName;
	}

	@Override
	public int id() {
		return 8;
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
