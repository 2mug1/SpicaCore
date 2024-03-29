package net.spicapvp.core.clan.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketClanInvite implements Packet {

    private Clan clan;

    private ClanPlayer target;

    public PacketClanInvite(){

    }

    public PacketClanInvite(Clan clan, ClanPlayer clanPlayer){
        this.clan = clan;
        this.target = clanPlayer;
    }

    @Override
    public int id() {
        return 18;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .add("clan", Clan.SERIALIZER.serialize(clan))
                .add("target", ClanPlayer.SERIALIZER.serialize(target)).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        clan = Clan.DESERIALIZER.deserialize(object.get("clan").getAsJsonObject());
        target = ClanPlayer.DESERIALIZER.deserialize(object.get("target").getAsJsonObject());
    }
}
