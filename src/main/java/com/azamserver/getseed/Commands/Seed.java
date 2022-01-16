
/*
File Name: Seed.java
Part of package: com.azamserver.getseed
Description: This file alerts the plugins on what to do when a player issues the command "/seed"
*/

// Declare package name
package com.azamserver.getseed.Commands;

// Import all needed libraries
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.azamserver.getseed.Main;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

// Start java class
public class Seed implements CommandExecutor {

    // Declare all needed variables
    private final Main main;
    private static final String messageStart = "" + ChatColor.BOLD + "" + ChatColor.GREEN + "[" + ChatColor.RED + "Seed" + ChatColor.GREEN + "]: ";

    // This constructor allows Main.java to access the command "/seed"
    public Seed(Main main)
    {
        // Set all needed variables
        this.main = main;
    }

    // This method alerts the plugins on what to do when the command "/seed" is run
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        // Check if the command "/seed" had arguments
        if (args.length == 0)
        {
            // Check if console executed the command
            if (sender instanceof ConsoleCommandSender)
            {
                // If console did execute the command, respond to console with a message saying that only players can run the command
                sender.sendMessage(messageStart + "Only players (not console) are allowed to execute this command");
                return true;
            }
            // Check if a player executed the command
            else if (sender instanceof Player)
            {
                // Check if said player has the permissions to execute "/seed"
                if(sender.isOp() || sender.hasPermission("seed.getSeed"))
                {
                    // If the player has the correct permissions, alert the player of what the seed of their current world is
                    sender.sendMessage(messageStart + ((Player) sender).getWorld().getSeed());
                    return true;
                }
                else
                {
                    // If the player does not have the correct permissions, alert the player that they do not have the correct permissions to run the "/seed" command
                    sender.sendMessage(messageStart + "You don't have the permissions to execute this command");
                    return true;
                }
            }
        }
        else
        {
            // If the command "/seed" had >= 1 arguments, check for sender's permissions
            if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("seed.helpMenu"))
            {
                // If the sender does have the correct permissions, send the user a help menu
                sender.sendMessage(messageStart + "Help Menu");
                sender.sendMessage(messageStart + "/seed : Will output the seed of the world the player is currently in");
                sender.sendMessage(messageStart + "/seed help : Will output this help menu");
                return true;
            }
            else
            {
                // If the sender does not have the correct permissions, alert the sender that they do not have the correct permissions to run the "/seed" command
                sender.sendMessage(messageStart + "You don't have the permissions to execute this command");
                return true;
            }
        }

        // Line of code is here to pass Java Syntax Check
        return false;
    }
}
