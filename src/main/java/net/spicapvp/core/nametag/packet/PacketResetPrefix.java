package net.spicapvp.core.nametag.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

import java.util.UUID;

@Getter
public class PacketResetPrefix implements Packet {

    private UUID uuid;

    public PacketResetPrefix(){

    }

    public PacketResetPrefix(UUID uuid){
        this.uuid = uuid;
    }

    @Override
    public int id() {
        return 27;
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
