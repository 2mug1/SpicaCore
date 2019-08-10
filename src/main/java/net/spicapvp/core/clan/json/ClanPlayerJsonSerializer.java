package net.spicapvp.core.clan.json;

import com.google.gson.JsonObject;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.util.json.JsonSerializer;

public class ClanPlayerJsonSerializer implements JsonSerializer<ClanPlayer> {

    @Override
    public JsonObject serialize(ClanPlayer clanPlayer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", clanPlayer.getUuid().toString());
        jsonObject.addProperty("role", clanPlayer.getRole().name());
        jsonObject.addProperty("procedureStage", clanPlayer.getProcedureStage().name());
        return jsonObject;
    }
}
