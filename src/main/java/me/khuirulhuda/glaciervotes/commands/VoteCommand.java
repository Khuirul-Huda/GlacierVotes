package me.khuirulhuda.glaciervotes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.khuirulhuda.glaciervotes.Main;

public class VoteCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final String cmdmsg = Main.getInstance().getConfig().getString("commandmsg");
		if (cmdmsg != null) {
			sender.sendMessage(cmdmsg);
		}
		return true;
	}
}