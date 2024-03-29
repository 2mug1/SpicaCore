package net.spicapvp.core.staff.packet;

import com.google.gson.JsonObject;

import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacketStaffChat implements Packet {

	private String playerName;
	private String serverName;
	private String chatMessage;

	public PacketStaffChat() {

	}

	public PacketStaffChat(String playerName, String serverName, String chatMessage) {
		this.playerName = playerName;
		this.serverName = serverName;
		this.chatMessage = chatMessage;
	}

	public int id() {
		return 6;
	}

	@Override
	public JsonObject serialize() {
		return new JsonChain()
				.addProperty("playerName", playerName)
				.addProperty("serverName", serverName)
				.addProperty("chatMessage", chatMessage)
				.get();
	}

	@Override
	public void deserialize(JsonObject jsonObject) {
		playerName = jsonObject.get("playerName").getAsString();
		serverName = jsonObject.get("serverName").getAsString();
		chatMessage = jsonObject.get("chatMessage").getAsString();
	}

}
