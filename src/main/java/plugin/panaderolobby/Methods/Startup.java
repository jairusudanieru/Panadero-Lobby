package plugin.panaderolobby.Methods;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import plugin.panaderolobby.Commands.*;
import plugin.panaderolobby.Events.*;

import java.util.Objects;

public class Startup {

   public static void registerEvents() {
      Bukkit.getPluginManager().registerEvents(new ChangeWorld(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new InventoryMove(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new LaunchPads(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new PlayerInteract(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new PlayerJoin(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new PlayerLogin(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new PlayerLogout(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new PlayerMove(), LobbyConfig.plugin);
      Bukkit.getPluginManager().registerEvents(new PlayerQuit(), LobbyConfig.plugin);
   }

   public static void registerCommands() {
      registerCommand("lobby", new Lobby(), new Lobby());
      registerCommand("spawn", new Spawn(), new Spawn());
      registerCommand("setlocation", new SetLocation(), new SetLocation());
   }

   private static void registerCommand(String command, CommandExecutor executor, TabCompleter completer) {
      PluginCommand pluginCommand = Objects.requireNonNull(Bukkit.getPluginCommand(command));
      pluginCommand.setExecutor(executor);
      pluginCommand.setTabCompleter(completer);
   }

}
