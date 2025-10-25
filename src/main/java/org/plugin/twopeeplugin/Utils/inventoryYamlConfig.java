package org.plugin.twopeeplugin.Utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.plugin.twopeeplugin.TwoPeePlugin;

import java.io.File;
import java.io.IOException;

public class inventoryYamlConfig {
    private static inventoryYamlConfig invConfig;
    private File file;
    private YamlConfiguration config;
    private inventoryYamlConfig() {
        setupFileConfig();
    }

    public static YamlConfiguration getConfig() {
        if (invConfig ==null) {
            invConfig = new inventoryYamlConfig();
        }
        return invConfig.config;
    }

    public static inventoryYamlConfig getInstance() {
        if (invConfig ==null) {
            invConfig = new inventoryYamlConfig();
        }
        return invConfig;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void setupFileConfig() {
        file = new File(TwoPeePlugin.getInstance().getDataFolder(),"inventory.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }



}
