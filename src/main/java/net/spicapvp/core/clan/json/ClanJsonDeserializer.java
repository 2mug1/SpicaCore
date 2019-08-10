package net.spicapvp.core.clan.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.spicapvp.core.clan.Clan;
import net.spicapvp.core.clan.ClanPlayer;
import net.spicapvp.core.util.json.JsonDeserializer;

import java.util.LinkedList;
import java.util.List;

public class ClanJsonDeserializer implements JsonDeserializer<Clan> {

    @Override
    public Clan deserialize(JsonObject object) {
        List<ClanPlayer> players = new LinkedList<>();

        Clan clan = new Clan(object.get("name").getAsString(), object.get("tag").getAsString());

        JsonArray array = new JsonParser().parse(object.get("players").getAsString()).getAsJsonArray();
        for (JsonElement element : array) {
            players.add(ClanPlayer.DESERIALIZER.deserialize(element.getAsJsonObject()));
        }

        clan.setPlayers(players);

        return clan;
    }
}
