package net.spicapvp.core.clan.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketClanInviteDenied implements Packet {

    private Clan clan;

    private String deniedPlayerName;

    public PacketClanInviteDenied(){

    }

    public PacketClanInviteDenied(Clan clan, String deniedPlayer){
        this.clan = clan;
        this.deniedPlayerName = deniedPlayer;
    }

    @Override
    public int id() {
        return 19;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .add("clan", Clan.SERIALIZER.serialize(clan))
                .addProperty("deniedPlayer", deniedPlayerName).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        clan = Clan.DESERIALIZER.deserialize(object.get("clan").getAsJsonObject());
        deniedPlayerName = object.get("deniedPlayerName").getAsJsonObject().getAsString();
    }
}
