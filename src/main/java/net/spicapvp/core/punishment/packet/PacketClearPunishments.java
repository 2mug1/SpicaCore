package net.spicapvp.core.punishment.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.punishment.Punishment;
import net.spicapvp.core.util.json.JsonChain;

import java.util.UUID;

public class PacketClearPunishments implements Packet {

    @Getter
    private UUID uuid;

    public PacketClearPunishments() {

    }

    public PacketClearPunishments(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public int id() {
        return 31;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain().addProperty("uuid", uuid.toString()).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        this.uuid = UUID.fromString(object.get("uuid").getAsString());
    }
}
