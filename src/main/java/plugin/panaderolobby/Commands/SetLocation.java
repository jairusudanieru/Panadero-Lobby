package plugin.panaderolobby.Commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import plugin.panaderolobby.Methods.LobbyConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetLocation implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (args.length != 1) return new ArrayList<>();
      ConfigurationSection locationSection = LobbyConfig.getConfigSection("location");
      if (locationSection == null) return new ArrayList<>();
      return new ArrayList<>(locationSection.getKeys(false));
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         Component message = LobbyConfig.getComponent("message.senderNotPlayer");
         if (message == null) return true;
         sender.sendMessage(message);
         return true;
      }

      if (args.length != 1) {
         Component message = LobbyConfig.getComponent("message.invalidCommand");
         if (message == null) return true;
         sender.sendMessage(message);
         return true;
      }

      ConfigurationSection locationSection = LobbyConfig.getConfigSection("location");
      if (locationSection == null) return true;
      Set<String> locations = locationSection.getKeys(false);
      if (!locations.contains(args[0])) {
         Component message = LobbyConfig.getComponent("message.invalidCommand");
         if (message == null) return true;
         sender.sendMessage(message);
         return true;
      }

      Player player = (Player) sender;
      LobbyConfig.setLocation("location." + args[0], player.getLocation());
      String stringMessage = LobbyConfig.getString("message.locationSetMessage");
      if (stringMessage == null) return true;
      stringMessage = stringMessage.replace("%location%",args[0]);
      Component message = LobbyConfig.text(stringMessage);
      sender.sendMessage(message);
      return true;
   }

}
