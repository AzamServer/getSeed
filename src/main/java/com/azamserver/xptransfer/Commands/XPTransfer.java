
/*
File Name: XPTransfer.java
Part of package: com.azamserver.xptransfer
Description: This file alerts the plugin on what to do when a player issues the command "/xptransfer"
*/

// Declare package name
package com.azamserver.xptransfer.Commands;

// Import all needed libraries
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.azamserver.xptransfer.Main;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import java.util.Collection;

// Start java class
public class XPTransfer implements CommandExecutor
{
    // Declare all needed variables
    private final Main main;
    private static final String messageStart = "" + ChatColor.BOLD + "" + ChatColor.GREEN + "[" + ChatColor.RED + "XPTransfer" + ChatColor.GREEN + "]: ";

    // This constructor allows Main.java to access the command "/xptransfer"
    public XPTransfer(Main main)
    {
        // Set all needed variables
        this.main = main;
    }

    // This method alerts the plugins on what to do when the command "/xptransfer" is run
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        // Check if "/xptransfer" command has arguments
        if(args.length == 2)
        {
            // If "/xptransfer" command had two arguments, check who sent command
            if(sender instanceof ConsoleCommandSender)
            {
                // If command was sent by console, alert console that command cannot be executed
                sender.sendMessage(messageStart + "The console cannot execute this command with only 2 arguments");
                return true;
            }
            if(sender.isOp() || sender.hasPermission("xptransfer.ownXP"))
            {
                // If "/xptransfer" command was sent by an entity that has correct perms, check for user input error
                if(args[0].equals(sender.getName()))
                {
                    // If playerOne in "/xptransfer <playerOne> <amount>" command equals the command sender's IGN, alert the command sender that they cannot transfer XP to themselves.
                    sender.sendMessage(messageStart + "Your IGN must not be the same as the person you are sending XP to");
                    return true;
                }
                else
                {
                    // Check if playerOne in "/xptransfer <playerOne> <amount>" command is a valid IGN
                    if(checkPlayer(args[0]))
                    {
                        // If playerOne in "/xptransfer <playerOne> <amount>" command is a valid IGN, Check if amount in "/xptransfer <playerOne> <amount>" command is a valid number
                        if(checkNum(args[1]))
                        {
                            // If amount in "/xptransfer <playerOne> <amount>" command is a valid number, Save amount in "/xptransfer <playerOne> <amount>" command to a variable
                            int xpLevels = Integer.parseInt(args[1]);

                            // Check if amount in "/xptransfer <playerOne> <amount>" command is equal to zero
                            if(xpLevels == 0)
                            {
                                // If amount in "/xptransfer <playerOne> <amount>" command is equal to zero, alert command sender that XP Level amount cannot equal zero.
                                sender.sendMessage(messageStart + "The amount of levels cannot be equal to zero");
                                return true;
                            }
                            // If amount in "/xptransfer <playerOne> <amount>" command is not equal to zero, check if command sender has enough levels to transfer
                            else if(((Player)sender).getLevel() >= xpLevels)
                            {
                                // If command sender does have enough levels to transfer, transfer levels from command sender to playerOne in command "/xptransfer <playerOne> <amount>"
                                ((Player)sender).setLevel(((Player)sender).getLevel() - xpLevels);
                                Bukkit.getPlayer(args[0]).setLevel(Bukkit.getPlayer(args[0]).getLevel() + xpLevels);
                                sender.sendMessage(messageStart + xpLevels + " levels have been transferred from yourself to Player \"" + args[0] + "\"");
                                return true;
                            }
                            else
                            {
                                // If command sender does not have enough levels to transfer, alert command sender that they do not have enough levels to transfer
                                sender.sendMessage(messageStart + "You don't have at least " + xpLevels + " levels to transfer");
                                return true;
                            }
                        }
                        else
                        {
                            // If amount in "/xptransfer <playerOne> <amount>" command is not a valid number, alert command sender that amount is invalid
                            sender.sendMessage(messageStart + args[1] + " is not a valid amount of levels");
                            sender.sendMessage(messageStart + "Please make sure the amount of levels is a non-negative number that has no decimal places");
                            return true;
                        }
                    }
                    else
                    {
                        // If playerOne in "/xptransfer <playerOne> <amount>" command is not a valid IGN, alert command sender that playerOne is not a valid IGN
                        sender.sendMessage(messageStart + "\"" + args[0] + "\" is not a valid IGN");
                        sender.sendMessage(messageStart + "Please make sure the IGN is case-sensitive and the player having said IGN is online");
                        return true;
                    }
                }
            }
            else
            {
                // If "/xptransfer" command was sent by an entity that does not have the  correct perms, alert the command sender that they do not have the correct perms to execute the command
                sender.sendMessage(messageStart + "You do not have permissions to use this command");
                return true;
            }
        }
        if(args.length == 3)
        {
            // If "/xptransfer" command had three arguments, check if command sender does have perms to run command
            if(sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("xptransfer.otherXP"))
            {
                // If command sender does have perms to run command, check if playerOne in command "/xptransfer <playerOne> <playerTwo> <amount>" is the same as playerTwo
                if(args[0].equals(args[1]))
                {
                    // If playerOne in command "/xptransfer <playerOne> <playerTwo> <amount>" is the same as playerTwo, alert command sender that playerOne cannot equal playerTwo
                    sender.sendMessage(messageStart + "playerOne's IGN must not be the same as playerTwo's IGN");
                    return true;
                }
                // If playerOne in command "/xptransfer <playerOne> <playerTwo> <amount>" is not the same as playerTwo, check if playerOne is a valid IGN
                else if(checkPlayer(args[0]))
                {
                    // If playerOne in command "/xptransfer <playerOne> <playerTwo> <amount>" is a valid IGN, check if playerTwo in command "/xptransfer <playerOne> <playerTwo> <amount>" is a valid IGN
                    if(checkPlayer(args[1]))
                    {
                        // If playerTwo in command "/xptransfer <playerOne> <playerTwo> <amount>" is a valid IGN, check if amount in command "/xptransfer <playerOne> <playerTwo> <amount>" is a valid number
                        if(checkNum(args[2]))
                        {
                            // If amount in command "/xptransfer <playerOne> <playerTwo> <amount>" is a valid number, save amount to variable
                            int xpLevels = Integer.parseInt(args[2]);

                            // Check if amount in "/xptransfer <playerOne> <playerTwo> <amount>" command is equal to zero
                            if(xpLevels == 0)
                            {
                                // If amount in "/xptransfer <playerOne> <playerTwo> <amount>" command is equal to zero, alert command sender that XP Level amount cannot equal zero.
                                sender.sendMessage(messageStart + "The amount of levels cannot be equal to zero");
                                return true;
                            }
                            // If amount in "/xptransfer <playerOne> <playerTwo> <amount>" command is not equal to zero, check if playerOne has enough levels to transfer
                            else if(Bukkit.getPlayer(args[0]).getLevel() >= xpLevels)
                            {
                                // If playerOne in "/xptransfer <playerOne> <playerTwo> <amount>" command does have enough levels to transfer, transfer levels from playerOne to playerTwo in command "/xptransfer <playerOne> <playerTwo> <amount>"
                                Bukkit.getPlayer(args[0]).setLevel(Bukkit.getPlayer(args[0]).getLevel() - xpLevels);
                                Bukkit.getPlayer(args[1]).setLevel(Bukkit.getPlayer(args[1]).getLevel() + xpLevels);
                                sender.sendMessage(messageStart + xpLevels + " levels have been transferred from Player \"" + args[0] + "\" to Player \"" + args[1] + "\"");
                                return true;
                            }
                            else
                            {
                                // If playerOne in "/xptransfer <playerOne> <playerTwo> <amount>" command does not have enough levels to transfer, alert command sender that playerOne does not have enough levels to transfer
                                sender.sendMessage(messageStart + "Player \"" + args[0] + "\" doesn't have at least " + xpLevels + " levels to transfer");
                                return true;
                            }
                        }
                        else
                        {
                            // If amount in command "/xptransfer <playerOne> <playerTwo> <amount>" is not a valid number, alert command sender that amount is invalid
                            sender.sendMessage(messageStart + args[2] + " is not a valid amount of levels");
                            sender.sendMessage(messageStart + "Please make sure the amount of levels is a non-negative number that has no decimal places");
                            return true;
                        }
                    }
                    else
                    {
                        // If playerTwo in command "/xptransfer <playerOne> <playerTwo> <amount>" is not a valid IGN, alert command sender that playerTwo is not a valid IGN
                        sender.sendMessage(messageStart + "\"" + args[1] + "\" is not a valid IGN");
                        sender.sendMessage(messageStart + "Please make sure the IGN is case-sensitive and the player having said IGN is online");
                        return true;
                    }
                }
                else
                {
                    // If playerOne in command "/xptransfer <playerOne> <playerTwo> <amount>" is not a valid IGN, alert command sender that playerOne is not a valid IGN
                    sender.sendMessage(messageStart + "\"" + args[0] + "\" is not a valid IGN");
                    sender.sendMessage(messageStart + "Please make sure the IGN is case-sensitive and the player having said IGN is online");
                    return true;
                }
            }
            else
            {
                // If command sender does not have perms to run command, alert the command sender that they do not have the correct perms to execute the command
                sender.sendMessage(messageStart + "You do not have permissions to use this command");
                return true;
            }
        }
        else
        {
            // If "/xptransfer" command has 0-1 or 4+ arguments, check if command sender does have perms to run command
            if(sender instanceof  ConsoleCommandSender || sender.isOp() || sender.hasPermission("xptransfer.helpMenu"))
            {
                // If command sender does have perms to run command, display a help menu
                sender.sendMessage(messageStart + "Help Menu");
                sender.sendMessage(messageStart + "/xptransfer <player> <amount>: Transfer a specified amount of your own XP to another player");
                sender.sendMessage(messageStart + "/xptransfer <playerOne> <playerTwo> <amount> : Transfer a specified amount of XP from playerOne to playerTwo");
                sender.sendMessage(messageStart + "/xptransfer help : Displays current help menu");
                return true;
            }
            else
            {
                // If command sender does not have perms to run command, alert the command sender that they do not have the correct perms to execute the command
                sender.sendMessage(messageStart + "You do not have permissions to use this command");
                return true;
            }
        }
    }

    // This private method will check if the IGN given to it is a valid player
    private boolean checkPlayer(String IGN)
    {
        // Store all online players to a collection
        Collection<? extends Player> allPlayers = Bukkit.getOnlinePlayers();

        // Try and match IGN given with all online player's IGNs
        for(Player p : allPlayers)
        {
            // If IGN given to method matches an online player's IGNs, return true
            if (p.getName().equals(IGN)) return true;
        }

        // If IGN given to method does not match an online player's IGNs, return false
        return false;
    }

    // This method will check if a string given to it is actually a number
    private boolean checkNum(String str)
    {
        // Try to check if number is valid
        // Source: https://stackabuse.com/java-check-if-string-is-a-number/
        try
        {
            int num = Integer.parseInt(str);

            // If the string given to method is a valid number, return true
            if(num > 0) return true;
        }
        catch (NumberFormatException e){}

        // If the string given to method is not a valid number, return false
        return false;
    }
}
