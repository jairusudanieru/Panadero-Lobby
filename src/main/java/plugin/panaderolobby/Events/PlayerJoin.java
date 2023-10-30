package plugin.panaderolobby.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.panaderolobby.Methods.LobbyConfig;

public class PlayerJoin implements Listener {

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      event.joinMessage(null);
      sendPlayerToAuth(player);
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         player.hidePlayer(LobbyConfig.plugin, onlinePlayer);
         onlinePlayer.hidePlayer(LobbyConfig.plugin, player);
      }
   }

   private void sendPlayerToAuth(Player player) {
      Location authLocation = LobbyConfig.getLocation("location.authLocation");
      if (authLocation == null) return;
      player.teleport(authLocation);
   }

}
