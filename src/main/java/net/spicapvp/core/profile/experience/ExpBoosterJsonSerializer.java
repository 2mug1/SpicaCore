package net.spicapvp.core.profile.experience;

import com.google.gson.JsonObject;
import net.spicapvp.core.punishment.Punishment;
import net.spicapvp.core.util.json.JsonSerializer;

public class ExpBoosterJsonSerializer implements JsonSerializer<ExpBooster> {

	@Override
	public JsonObject serialize(ExpBooster expBooster) {
		JsonObject object = new JsonObject();
		object.addProperty("addedAt", expBooster.getAddedAt());
		object.addProperty("duration", expBooster.getDuration());
		object.addProperty("increaseRate", expBooster.getIncreaseRate());
		object.addProperty("addedReason", expBooster.getAddedReason());
		object.addProperty("addedBy", expBooster.getAddedBy() == null ? null : expBooster.getAddedBy().toString());
		object.addProperty("removed", expBooster.isRemoved());
		object.addProperty("removedAt", expBooster.getRemovedAt());
		object.addProperty("removedBy", expBooster.getRemovedBy() == null ? null : expBooster.getRemovedBy().toString());
		object.addProperty("removedReason", expBooster.getRemovedReason());
		return object;
	}

}
