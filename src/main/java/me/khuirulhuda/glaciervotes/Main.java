package me.khuirulhuda.glaciervotes;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import me.khuirulhuda.glaciervotes.bstats.Metrics;
import me.khuirulhuda.glaciervotes.events.Vote;
import me.khuirulhuda.glaciervotes.commands.Reload;
import me.khuirulhuda.glaciervotes.commands.VoteCommand;

public class Main extends JavaPlugin implements Listener {
  
  private static Main INSTANCE;
  
	@Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        this.getCommand("gvreload").setExecutor(new Reload());
        this.getCommand("vote").setExecutor(new VoteCommand());
        Bukkit.getPluginManager().registerEvents(new Vote(), this);
        this.getLogger().info(ChatColor.GREEN+"GlacierVotes Successfully Enabled");
        int pluginId = 11531; //DON'T TOUCH!
        
        @SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginId);
        
    }
    
    @Override
    public void onDisable() {

    }
    
    
    public static Main getInstance() {
      return INSTANCE;
    }

}