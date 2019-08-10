package net.spicapvp.core.clan.json;

import com.google.gson.JsonObject;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.clan.ClanPlayerProcedureStage;
import net.spicapvp.core.clan.ClanPlayerRole;
import net.spicapvp.core.profile.Profile;
import net.spicapvp.core.util.json.JsonDeserializer;

import java.util.UUID;

public class ClanPlayerJsonDeserializer implements JsonDeserializer<ClanPlayer> {

    @Override
    public ClanPlayer deserialize(JsonObject object) {
        UUID uuid = UUID.fromString(object.get("uuid").getAsString());
        return new ClanPlayer(
                uuid,
                Profile.getByUuid(uuid).getUsername(),
                ClanPlayerRole.valueOf(object.get("role").getAsString()),
                ClanPlayerProcedureStage.valueOf(object.get("procedureStage").getAsString())
        );
    }
}
