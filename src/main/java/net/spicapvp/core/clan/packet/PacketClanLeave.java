package net.spicapvp.core.clan.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketClanLeave implements Packet {

    private Clan clan;

    private String leavePlayerName;

    public PacketClanLeave(){

    }

    public PacketClanLeave(Clan clan, String leavePlayerName){
        this.clan = clan;
        this.leavePlayerName = leavePlayerName;
    }

    @Override
    public int id() {
        return 22;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .add("clan", Clan.SERIALIZER.serialize(clan))
                .addProperty("leavePlayerName", leavePlayerName).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        clan = Clan.DESERIALIZER.deserialize(object.get("clan").getAsJsonObject());
        leavePlayerName = object.get("leavePlayerName").getAsString();
    }
}
