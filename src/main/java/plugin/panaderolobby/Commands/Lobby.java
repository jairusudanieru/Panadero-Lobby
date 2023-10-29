package plugin.panaderolobby.Commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import plugin.panaderolobby.Methods.LobbyConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lobby implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (sender.isOp()) return Arrays.asList("ping");
      if (args.length != 1) return new ArrayList<>();
      return Arrays.asList("reload","ping");
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length != 1) {
         Component message = LobbyConfig.getComponent("message.invalidCommand");
         if (message == null) return true;
         sender.sendMessage(message);
         return true;
      }

      if (args[0].equals("reload")) {
         LobbyConfig.plugin.reloadConfig();
         Component message = LobbyConfig.getComponent("message.reloadMessage");
         if (message == null) return true;
         sender.sendMessage(message);
      } else if (args[0].equals("ping")) {
         sender.sendMessage(Component.text("ping"));
      }
      return true;
   }

}
