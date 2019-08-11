package net.spicapvp.core.grant.packet;

import com.google.gson.JsonObject;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.grant.Grant;
import net.spicapvp.core.util.json.JsonChain;
import java.util.UUID;
import lombok.Getter;

public class PacketAddGrant implements Packet {

	@Getter private UUID playerUuid;
	@Getter private Grant grant;

	public PacketAddGrant() {

	}

	public PacketAddGrant(UUID playerUuid, Grant grant) {
		this.playerUuid = playerUuid;
		this.grant = grant;
	}

	@Override
	public int id() {
		return 1;
	}

	@Override
	public JsonObject serialize() {
		return new JsonChain()
				.addProperty("playerUuid", playerUuid.toString())
				.add("packet", Grant.SERIALIZER.serialize(grant))
				.get();
	}

	@Override
	public void deserialize(JsonObject jsonObject) {
		playerUuid = UUID.fromString(jsonObject.get("playerUuid").getAsString());
		grant = Grant.DESERIALIZER.deserialize(jsonObject.get("packet").getAsJsonObject());
	}

}
