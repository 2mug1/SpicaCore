package net.spicapvp.core.clan.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketClanBroadcast implements Packet {

    private Clan clan;

    private String message;

    public PacketClanBroadcast(){

    }

    public PacketClanBroadcast(Clan clan, String message){
        this.clan = clan;
        this.message = message;
    }

    @Override
    public int id() {
        return 15;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .add("clan", Clan.SERIALIZER.serialize(clan))
                .addProperty("message", message).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        clan = Clan.DESERIALIZER.deserialize(object.get("clan").getAsJsonObject());
        message = object.get("message").getAsString();
    }
}
