package plugin.panaderolobby.Commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import plugin.panaderolobby.Methods.LobbyConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Spawn implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (args.length != 1) return new ArrayList<>();
      return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         Component message = LobbyConfig.getComponent("message.senderNotPlayer");
         if (message == null) return true;
         sender.sendMessage(message);
         return true;
      }

      Location location = LobbyConfig.getLocation("location.spawnLocation");
      if (location == null) {
         Component message = LobbyConfig.getComponent("message.locationNotFound");
         if (message == null) return true;
         sender.sendMessage(message);
         return true;
      }

      Player player = (Player) sender;
      if (args.length == 0) {
         player.teleport(location);
      } else if (args.length == 1) {
         Player target = Bukkit.getPlayer(args[0]);
         if (target == null) {
            Component message = LobbyConfig.getComponent("message.playerNotFound");
            if (message == null) return true;
            player.sendMessage(message);
         } else {
            target.teleport(location);
         }
      } else {
         Component message = LobbyConfig.getComponent("message.invalidCommand");
         if (message == null) return true;
         sender.sendMessage(message);
      }
      return true;
   }

}
