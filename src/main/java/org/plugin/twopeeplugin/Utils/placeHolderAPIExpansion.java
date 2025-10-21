package org.plugin.twopeeplugin.Utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.plugin.twopeeplugin.Core.progressManager;

public class placeHolderAPIExpansion extends PlaceholderExpansion {
    private final progressManager progressmanager;
    public placeHolderAPIExpansion(progressManager progressManager) {

        this.progressmanager = progressManager;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "twopee";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Raspberry5";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }
        if (identifier.equals("time")) {
            return progressmanager.getFormattedTime(player);
        } else if (identifier.equals("cp")) {
            int cp = progressmanager.getPreviousCheckpoint(player);
            if (cp>=0) {
                return "CP: "+ Integer.toString(cp);
            } else {
                return "Not in Parkour";
            }
        }
        return null;
    }
}
