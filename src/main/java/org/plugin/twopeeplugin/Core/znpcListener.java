package org.plugin.twopeeplugin.Core;

import lol.pyr.znpcsplus.api.event.NpcInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.plugin.twopeeplugin.Utils.chatMessenger;

public class znpcListener implements Listener {
    guiEventListener guilistner;
    groupManager groupmanager;

    public znpcListener(guiEventListener ge, groupManager gm) {
        guilistner = ge;
        groupmanager = gm;
    }
    @EventHandler
    public void onNPCClick(NpcInteractEvent event) {
        if (event.getEntry().getId()==null) return;
        if (event.getPlayer()==null) return;
        if (event.getEntry().getId().equals("1")) {
            guilistner.open(event.getPlayer());
        } else if (event.getEntry().getId().equals("2")) {
            chatMessenger.sendDiscordLink(event.getPlayer());
        } else if (event.getEntry().getId().equals("3")) {
            groupmanager.promote(event.getPlayer());
        }
    }
 }
