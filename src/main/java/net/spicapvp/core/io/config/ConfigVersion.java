package net.spicapvp.core.io.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.spicapvp.core.io.config.impl.ConfigConversion1;
import net.spicapvp.core.io.config.impl.ConfigConversion2;
import net.spicapvp.core.io.config.impl.ConfigConversion3;

@AllArgsConstructor
@Getter
public enum ConfigVersion {

	VERSION_1(1, new ConfigConversion1()),
	VERSION_2(2, new ConfigConversion2()),
	VERSION_3(3, new ConfigConversion3());

	private int number;
	private ConfigConversion conversion;

}
