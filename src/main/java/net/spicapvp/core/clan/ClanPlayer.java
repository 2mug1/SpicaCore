package net.spicapvp.core.clan;

import lombok.Getter;
import lombok.Setter;
import net.spicapvp.core.clan.json.ClanPlayerJsonDeserializer;
import net.spicapvp.core.clan.json.ClanPlayerJsonSerializer;
import net.spicapvp.core.player.PlayerInfo;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Setter
public class ClanPlayer extends PlayerInfo {

    public static ClanPlayerJsonSerializer SERIALIZER = new ClanPlayerJsonSerializer();
    public static ClanPlayerJsonDeserializer DESERIALIZER = new ClanPlayerJsonDeserializer();

    private ClanPlayerRole role;
    private ClanPlayerProcedureStage procedureStage;

    public ClanPlayer(@NotNull UUID uuid, @NotNull String name, ClanPlayerRole role, ClanPlayerProcedureStage procedureStage) {
        super(uuid, name);
        this.role = role;
        this.procedureStage = procedureStage;
    }

    public boolean isMoreThanLeader(){
        return role.getWeight() >= 1;
    }

    public boolean isOwner(){
        return role == ClanPlayerRole.Owner;
    }
}
