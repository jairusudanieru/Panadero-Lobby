package plugin.panaderolobby;

import org.bukkit.plugin.java.JavaPlugin;
import plugin.panaderolobby.Methods.InventoryGUI;
import plugin.panaderolobby.Methods.Startup;

public final class Panadero_Lobby extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Startup.registerCommands();
        Startup.registerEvents();
        InventoryGUI.makeInventory();
    }

    @Override
    public void onDisable() {
    }

}
