package plugin.panaderolobby.Methods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.panaderolobby.Panadero_Lobby;

public class LobbyConfig {

   public static JavaPlugin plugin = JavaPlugin.getPlugin(Panadero_Lobby.class);

   public static Component getComponent(String key) {
      String string = plugin.getConfig().getString(key);
      if (string == null) return null;
      MiniMessage miniMessage = MiniMessage.miniMessage();
      return miniMessage.deserialize(string);
   }

   public static Component text(String text) {
      MiniMessage miniMessage = MiniMessage.miniMessage();
      return miniMessage.deserialize(text).decoration(TextDecoration.ITALIC,false);
   }

   public static String getString(String key) {
      return plugin.getConfig().getString(key);
   }

   public static double getDouble(String key) {
      return plugin.getConfig().getDouble(key);
   }

   public static ConfigurationSection getConfigSection(String key) {
      return plugin.getConfig().getConfigurationSection(key);
   }

   public static Location getLocation(String key) {
      return plugin.getConfig().getLocation(key);
   }

   public static World getWorld(String key) {
      String string = plugin.getConfig().getString(key);
      if (string == null) return null;
      return Bukkit.getWorld(string);
   }

   public static void setLocation(String key, Location location) {
      plugin.getConfig().set(key, location);
      plugin.getConfig().options().copyDefaults(true);
      plugin.saveConfig();
   }

}
