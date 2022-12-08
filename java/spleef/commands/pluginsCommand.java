package spleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class pluginsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            ((Player) sender).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&fPlugins (1): &aSnowCentral-Core"));

        }

        return false;
    }
}
