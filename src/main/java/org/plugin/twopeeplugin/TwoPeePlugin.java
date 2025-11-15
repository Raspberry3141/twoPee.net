package org.plugin.twopeeplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.plugin.twopeeplugin.Commands.*;
import org.plugin.twopeeplugin.Commands.builderCommnads.*;
import org.plugin.twopeeplugin.Core.housing.*;
import org.plugin.twopeeplugin.Core.parkour.*;
import org.plugin.twopeeplugin.Utils.placeHolderAPIExpansion;

public final class TwoPeePlugin extends JavaPlugin {
    private static TwoPeePlugin instance;
    private parkourTimer timer;

    @Override
    public void onEnable() {
        instance = this;
        itemManager itemmanager = new itemManager();
        timer = new parkourTimer();
        pracManager pracmanager = new pracManager();
        courseBuilder coursebuilder = new courseBuilder();
		PlayerCountTracker playerCounter = new PlayerCountTracker();
        guiEventListener guiListener = new guiEventListener(playerCounter);
        giveItemManager giveitemmanager  = new giveItemManager(pracmanager);
        tpLocationManager tpmanager = new tpLocationManager();
        whiteListWorldManager whitelister = new whiteListWorldManager();
        groupManager groupManager = new groupManager();
        progressManager progressmanager = new progressManager(timer,itemmanager,pracmanager);
		checkpointListener checkpointlistener = new checkpointListener(progressmanager, pracmanager, whitelister);
		pracmanager.getProgressManager(progressmanager);
        getServer().getPluginManager().registerEvents(guiListener,this);
        getServer().getPluginManager().registerEvents(checkpointlistener, this);
        getServer().getPluginManager().registerEvents(tpmanager,this);
        getServer().getPluginManager().registerEvents(new znpcListener(guiListener,groupManager, playerCounter),this);
        getServer().getPluginManager().registerEvents(new globalSettingEvents(progressmanager,groupManager,itemmanager),this);
        getServer().getPluginManager().registerEvents(new knockbackEvent(pracmanager),this);
        getServer().getPluginManager().registerEvents(giveitemmanager,this);
        getServer().getPluginManager().registerEvents(pracmanager,this);
        getServer().getPluginManager().registerEvents(new itemEvent(pracmanager,progressmanager),this);
        this.getCommand("test").setExecutor(new testCommand(coursebuilder,guiListener,progressmanager));
        this.getCommand("browse").setExecutor(new browseCourse(guiListener));
        this.getCommand("help").setExecutor(new help());
        this.getCommand("lobby").setExecutor(new lobby());
        this.getCommand("pcp").setExecutor(new previousCheckpoint());
        this.getCommand("reset").setExecutor(new reset(checkpointlistener));
        this.getCommand("sword").setExecutor(new sword());
        this.getCommand("pcpitem").setExecutor(new pcpitem(giveitemmanager));
        this.getCommand("mapbuilder").setExecutor(new mapbuilder(coursebuilder));
        this.getCommand("setnewspawn").setExecutor(new setSpawn());
        this.getCommand("setdisplay").setExecutor(new setDisplayWorldName());
        this.getCommand("verify").setExecutor(new verifyCourse(coursebuilder,groupManager,progressmanager,checkpointlistener));
        this.getCommand("cancelverify").setExecutor(new cancelVerifyCourse(coursebuilder,groupManager));
        this.getCommand("gms").setExecutor(new gamemodeAdventure());
        this.getCommand("gmc").setExecutor(new gamemodeCreative());
        this.getCommand("fly").setExecutor(new fly());
        this.getCommand("tpblock").setExecutor(new tpBlock(tpmanager));
        this.getCommand("platform").setExecutor(new platform());
        this.getCommand("invite").setExecutor(new invitePlayer());
        this.getCommand("join").setExecutor(new join());
        this.getCommand("whitelist").setExecutor(new whitelistCommand(whitelister));
        this.getCommand("itemblock").setExecutor(new itemBlock(giveitemmanager));
        this.getCommand("prac").setExecutor(new prac(pracmanager));
        this.getCommand("unprac").setExecutor(new unprac(pracmanager));
        this.getCommand("gap").setExecutor(new goldenApple());
        this.getCommand("delete").setExecutor(new delete());
		this.getCommand("rate").setExecutor(new rate());
        new placeHolderAPIExpansion(progressmanager).register();

    }

    @Override
    public void onDisable() {
        timer.writeAllTimeToDisk();
    }

    public static TwoPeePlugin getInstance() {
        return instance;
    }
}
