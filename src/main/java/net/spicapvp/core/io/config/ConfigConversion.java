package net.spicapvp.core.io.config;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigConversion {

	void convert(File file, FileConfiguration fileConfiguration);

}
