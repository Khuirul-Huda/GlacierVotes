package me.khuirulhuda.glaciervotes.commands;

import me.khuirulhuda.glaciervotes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.RED+"No Permissions!");
        } else {
          Main.getInstance().reloadConfig();
          Main.getInstance().getLogger().info("[GlacierVotes] Config Reloaded");
        }

        
        return true;
    }
}