package net.spicapvp.core.network.packet.friend;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.util.json.JsonChain;

@Getter
public class PacketFriendSendRequest implements Packet {

    private String targetUUID;
    private String senderUUID;

    public PacketFriendSendRequest(){

    }

    public PacketFriendSendRequest(String targetUUID, String senderUUID){
        this.targetUUID = targetUUID;
        this.senderUUID = senderUUID;
    }

    @Override
    public int id() {
        return 11;
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
