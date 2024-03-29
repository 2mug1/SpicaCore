package net.spicapvp.core.profile.staff;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ProfileStaffOptions {

	@Getter @Setter private boolean staffModeEnabled = true;
	@Getter @Setter private boolean staffChatModeEnabled = false;
}
