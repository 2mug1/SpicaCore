package net.spicapvp.core.team;

import lombok.Getter;
import lombok.Setter;
import net.spicapvp.core.player.PlayerInfo;

import java.util.UUID;

@Getter
public class TeamPlayer extends PlayerInfo {

    @Setter
    private boolean alive;

    public TeamPlayer(UUID uuid, String name) {
        super(uuid, name);
    }

}
