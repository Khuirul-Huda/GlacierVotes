package me.khuirulhuda.glaciervotes;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import me.khuirulhuda.glaciervotes.events.*;
import me.khuirulhuda.glaciervotes.commands.*;
import org.bukkit.ChatColor;

public class Main extends JavaPlugin implements Listener {
  
  private static Main INSTANCE;
  
    @Override
    public void onEnable() {
        this.getCommand("reload").setExecutor(new Reload());
        Bukkit.getPluginManager().registerEvents(new Vote(), this);
        INSTANCE = this;
        this.getLogger().info(ChatColor.GREEN+"GlacierVotes Successfully Enabled");
    }
    public static Main getInstance() {
      return INSTANCE;
    }
    
    @Override
    public void onDisable() {
        this.getLogger().info("GlacierVotes RenderyCrafty Successfully Disabled");
    }
}