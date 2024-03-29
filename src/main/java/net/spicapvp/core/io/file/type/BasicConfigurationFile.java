package net.spicapvp.core.io.file.type;

import net.spicapvp.core.io.file.AbstractConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class BasicConfigurationFile extends AbstractConfigurationFile {
    private final File file;
    private final YamlConfiguration configuration;

    public File getFile() { return this.file; }
    public YamlConfiguration getConfiguration() { return this.configuration; }

    public BasicConfigurationFile(JavaPlugin plugin, String name, boolean overwrite) {
        super(plugin, name);

        this.file = new File(plugin.getDataFolder(), name + ".yml");
        plugin.saveResource(name + ".yml", overwrite);


        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public BasicConfigurationFile(JavaPlugin plugin, String name) {
        this(plugin, name, false);
    }

    public String getString(String path)
    {
        if (this.configuration.contains(path)) {
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
        }
        return null;
    }

    public String getStringOrDefault(String path, String or)
    {
        String toReturn = getString(path);
        return toReturn == null ? or : toReturn;
    }

    public int getInteger(String path)
    {
        if (this.configuration.contains(path)) {
            return this.configuration.getInt(path);
        }
        return 0;
    }

    public boolean getBoolean(String path) {
        return (this.configuration.contains(path)) && (this.configuration.getBoolean(path));
    }

    public double getDouble(String path)
    {
        if (this.configuration.contains(path)) {
            return this.configuration.getDouble(path);
        }
        return 0.0D;
    }

    public Object get(String path)
    {
        if (this.configuration.contains(path)) {
            return this.configuration.get(path);
        }
        return null;
    }

    public java.util.List<String> getStringList(String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getStringList(path);
        }
        return null;
    }
}