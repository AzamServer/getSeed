
/*
File Name: Main.java
Part of package: com.azamserver.xptransfer
Description: This file is the root of the plugin and tells the plugins what to do on enabling and disabling
*/

// Declare package name
package com.azamserver.xptransfer;

// Import all needed libraries
import org.bukkit.plugin.java.JavaPlugin;
import com.azamserver.xptransfer.Commands.XPTransfer;

// Start java class
public final class Main extends JavaPlugin {

    // Code will run on plugin enable
    @Override
    public void onEnable() {
        // Allow command "/xptransfer" to work
        getServer().getPluginCommand("xptransfer").setExecutor(new XPTransfer(this));
    }

    // Code will run on plugin disable
    @Override
    public void onDisable() {
        // Since there isn't anything for the plugin to do on disable, this has been left empty
    }
}
