package net.spicapvp.core.nametag.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

import java.util.UUID;

@Getter
public class PacketResetSuffix implements Packet {

    private UUID uuid;

    public PacketResetSuffix(){

    }

    public PacketResetSuffix(UUID uuid){
        this.uuid = uuid;
    }

    @Override
    public int id() {
        return 28;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain().
                addProperty("uuid", uuid.toString()).
                get();
    }

    @Override
    public void deserialize(JsonObject object) {
        this.uuid =  UUID.fromString(object.get("uuid").getAsString());
    }
}
