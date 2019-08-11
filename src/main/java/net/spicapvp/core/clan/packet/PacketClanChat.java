package net.spicapvp.core.clan.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketClanChat implements Packet {

    private Clan clan;

    private ClanPlayer sender;

    private String message, fromServer;

    public PacketClanChat(){

    }

    public PacketClanChat(Clan clan, ClanPlayer sender, String message, String fromServer){
        this.clan = clan;
        this.sender = sender;
        this.message = message;
        this.fromServer = fromServer;
    }

    @Override
    public int id() {
        return 16;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .add("clan", Clan.SERIALIZER.serialize(clan))
                .add("sender", ClanPlayer.SERIALIZER.serialize(sender))
                .addProperty("message", message)
                .addProperty("fromServer", fromServer).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        clan = Clan.DESERIALIZER.deserialize(object.get("clan").getAsJsonObject());
        sender = ClanPlayer.DESERIALIZER.deserialize(object.get("sender").getAsJsonObject());
        message = object.get("message").getAsString();
        fromServer = object.get("fromServer").getAsString();
    }
}
