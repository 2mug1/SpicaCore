package net.spicapvp.core.strap;

import net.spicapvp.core.SpicaCore;
import lombok.Getter;

@Getter
public class Strapped {

	protected final SpicaCore spicaCore;

	public Strapped(SpicaCore spicaCore) {
		this.spicaCore = spicaCore;
	}

}
