package net.spicapvp.core.convenient;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.strap.Strapped;
import net.spicapvp.core.convenient.event.SpawnTeleportEvent;
import net.spicapvp.core.util.LocationUtil;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Convenient extends Strapped {

	private Location spawn;

	public Convenient(SpicaCore spicaCore) {
		super(spicaCore);

		spawn = LocationUtil.deserialize(spicaCore.getMainConfig().getStringOrDefault("ESSENTIAL.SPAWN_LOCATION", null));
	}

	public void setSpawn(Location location) {
		spawn = location;

		if (spawn == null) {
			spicaCore.getMainConfig().getConfiguration().set("ESSENTIAL.SPAWN_LOCATION", null);
		} else {
			spicaCore.getMainConfig().getConfiguration().set("ESSENTIAL.SPAWN_LOCATION", LocationUtil.serialize(this.spawn));
		}

		try {
			spicaCore.getMainConfig().getConfiguration().save(spicaCore.getMainConfig().getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void teleportToSpawn(Player player) {
		Location location = spawn == null ? spicaCore.getServer().getWorlds().get(0).getSpawnLocation() : spawn;

		SpawnTeleportEvent event = new SpawnTeleportEvent(player, location);
		event.call();

		if (!event.isCancelled() && event.getLocation() != null) {
			player.teleport(event.getLocation());
		}
	}

	public int clearEntities(World world) {
		int removed = 0;

		for (Entity entity : world.getEntities()) {
			if (entity.getType() == EntityType.PLAYER) {
				continue;
			}

			removed++;
			entity.remove();
		}

		return removed;
	}

	public int clearEntities(World world, EntityType... excluded) {
		int removed = 0;

		entityLoop:
		for (Entity entity : world.getEntities()) {
			for (EntityType type : excluded) {
				if (entity.getType() == EntityType.PLAYER) {
					continue entityLoop;
				}

				if (entity.getType() == type) {
					continue entityLoop;
				}
			}

			removed++;
			entity.remove();
		}

		return removed;
	}

}
