package net.spicapvp.core.clan.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketClanJoin implements Packet {

    private Clan clan;

    private ClanPlayer joinPlayer;

    public PacketClanJoin(){

    }

    public PacketClanJoin(Clan clan, ClanPlayer joinPlayer){
        this.clan = clan;
        this.joinPlayer = joinPlayer;
    }

    @Override
    public int id() {
        return 20;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .add("clan", Clan.SERIALIZER.serialize(clan))
                .add("joinPlayer", ClanPlayer.SERIALIZER.serialize(joinPlayer)).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        clan = Clan.DESERIALIZER.deserialize(object.get("clan").getAsJsonObject());
        joinPlayer = ClanPlayer.DESERIALIZER.deserialize(object.get("joinPlayer").getAsJsonObject());
    }
}
