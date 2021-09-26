package me.khuirulhuda.glaciervotes;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import me.khuirulhuda.glaciervotes.events.Vote;
import me.khuirulhuda.glaciervotes.commands.Reload;
import org.bukkit.ChatColor;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import me.khuirulhuda.glaciervotes.bstats.Metrics;


public class Main extends JavaPlugin implements Listener {
  
  private File customConfigFile;
  private FileConfiguration customConfig;
  private static Main INSTANCE;
  
    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        this.getCommand("gvreload").setExecutor(new Reload());
        Bukkit.getPluginManager().registerEvents(new Vote(), this);
        this.getLogger().info(ChatColor.GREEN+"GlacierVotes Successfully Enabled");
        int pluginId = 11531; //DON'T TOUCH!
        Metrics metrics = new Metrics(this, pluginId);
    }
    
    @Override
    public void onDisable() {
        this.getLogger().info("GlacierVotes RenderyCrafty Successfully Disabled");
    }
    
    public static Main getInstance() {
      return INSTANCE;
    }

}