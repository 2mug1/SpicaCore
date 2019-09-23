package net.spicapvp.core.profile.experience.packet;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.pidgin.packet.Packet;
import net.spicapvp.core.profile.experience.ExpBooster;
import net.spicapvp.core.util.json.JsonChain;

import java.util.UUID;

public class PacketExpBoosterApply implements Packet {

    @Getter
    private UUID uuid;
    @Getter
    private ExpBooster expBooster;

    public PacketExpBoosterApply() {

    }

    public PacketExpBoosterApply(UUID uuid, ExpBooster expBooster) {
        this.uuid = uuid;
        this.expBooster = expBooster;
    }

    @Override
    public int id() {
        return 32;
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain()
                .addProperty("uuid", uuid.toString())
                .add("expBooster", ExpBooster.SERIALIZER.serialize(expBooster)).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        this.uuid = UUID.fromString(object.get("uuid").getAsString());
        this.expBooster = ExpBooster.DESERIALIZER.deserialize(object.get("expBooster").getAsJsonObject());
    }
}
