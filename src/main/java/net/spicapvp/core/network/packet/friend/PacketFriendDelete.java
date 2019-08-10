package net.spicapvp.core.network.packet.friend;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketFriendDelete implements Packet {

    private String targetUUID;
    private String senderUUID;

    public PacketFriendDelete(){

    }

    public PacketFriendDelete(String targetUUID, String senderUUID){
        this.targetUUID = targetUUID;
        this.senderUUID = senderUUID;
    }

    @Override
    public int id() {
        return 12;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain().
                addProperty("targetUUID", targetUUID)
                .addProperty("senderUUID", senderUUID)
                .get();
    }

    @Override
    public void deserialize(JsonObject object) {
        targetUUID = object.get("targetUUID").getAsString();
        senderUUID = object.get("senderUUID").getAsString();
    }
}
