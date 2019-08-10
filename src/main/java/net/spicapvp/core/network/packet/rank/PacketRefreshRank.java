package net.spicapvp.core.network.packet.rank;

import com.google.gson.JsonObject;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PacketRefreshRank implements Packet {

	private UUID uuid;
	private String name;

	public PacketRefreshRank() {

	}

	@Override
	public int id() {
		return 5;
	}

	@Override
	public JsonObject serialize() {
		return new JsonChain()
				.addProperty("uuid", uuid.toString())
				.addProperty("name", name)
				.get();
	}

	@Override
	public void deserialize(JsonObject jsonObject) {
		uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
		name = jsonObject.get("name").getAsString();
	}

}