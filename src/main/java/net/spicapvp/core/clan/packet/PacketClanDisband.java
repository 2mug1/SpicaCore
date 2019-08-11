package net.spicapvp.core.clan.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketClanDisband implements Packet {

    private Clan clan;

    public PacketClanDisband(){

    }

    public PacketClanDisband(Clan clan){
        this.clan = clan;
    }

    @Override
    public int id() {
        return 17;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain().add("clan", Clan.SERIALIZER.serialize(clan)).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        clan = Clan.DESERIALIZER.deserialize(object.get("clan").getAsJsonObject());
    }
}
