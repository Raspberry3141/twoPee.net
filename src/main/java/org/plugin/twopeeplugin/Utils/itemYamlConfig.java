package org.plugin.twopeeplugin.Utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.plugin.twopeeplugin.TwoPeePlugin;

import java.io.File;
import java.io.IOException;

public class itemYamlConfig {
    private static itemYamlConfig itemYaml;
    private File file;
    private YamlConfiguration config;
    private itemYamlConfig() {
        setupFileConfig();
    }

    public static YamlConfiguration getConfig() {
        if (itemYaml ==null) {
            itemYaml = new itemYamlConfig();
        }
        return itemYaml.config;
    }

    public static itemYamlConfig getInstance() {
        if (itemYaml ==null) {
            itemYaml = new itemYamlConfig();
        }
        return itemYaml;
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
        file = new File(TwoPeePlugin.getInstance().getDataFolder(),"item.yml");
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
