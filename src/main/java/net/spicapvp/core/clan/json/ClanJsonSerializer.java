package net.spicapvp.core.clan.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.util.json.JsonSerializer;

public class ClanJsonSerializer implements JsonSerializer<Clan> {

    @Override
    public JsonObject serialize(Clan clan) {
        JsonObject object = new JsonObject();

        object.addProperty("name", clan.getName());
        object.addProperty("tag", clan.getTag());
        object.addProperty("createdAt", clan.getCreatedAt());

        JsonArray array = new JsonArray();

        for(ClanPlayer clanPlayer : clan.getPlayers()){
            array.add(ClanPlayer.SERIALIZER.serialize(clanPlayer));
        }

        object.addProperty("players", array.toString());

        return object;
    }
}
