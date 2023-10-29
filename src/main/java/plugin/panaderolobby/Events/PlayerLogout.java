package plugin.panaderolobby.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.LogoutEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plugin.panaderocore.Methods.CoreConfig;
import plugin.panaderocore.Methods.WorldGroups;
import plugin.panaderolobby.Methods.LobbyConfig;

import java.util.List;

public class PlayerLogout implements Listener {

   @EventHandler
   public void onPlayerLogout(LogoutEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      sendQuitMessage(player, playerWorld);
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         player.hidePlayer(LobbyConfig.plugin, onlinePlayer);
         onlinePlayer.hidePlayer(LobbyConfig.plugin, player);
      }
   }

   private boolean isLobbyWorld(Player player) {
      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) return false;
      List<String> worldGroup = WorldGroups.sameGroupWorlds(lobbyWorld);
      return worldGroup.contains(player.getWorld().getName());
   }

   private void sendQuitMessage(Player player, World world) {
      List<String> worldGroup = WorldGroups.sameGroupWorlds(world);
      String quitMessage = CoreConfig.getString("message.quitMessage");
      if (quitMessage == null || quitMessage.isEmpty()) return;
      quitMessage = quitMessage.replace("%player%",player.getName());

      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         if (isLobbyWorld(player)) continue;
         onlinePlayer.sendMessage(LobbyConfig.text(quitMessage));
      }
   }

}
