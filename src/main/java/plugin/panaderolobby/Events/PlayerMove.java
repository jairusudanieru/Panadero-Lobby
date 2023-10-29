package plugin.panaderolobby.Events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import plugin.panaderolobby.Methods.LobbyConfig;

public class PlayerMove implements Listener {

   @EventHandler
   public void onPlayerVoid(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      double voidLevel = LobbyConfig.getDouble("config.voidYLevel");
      Location spawnLocation = LobbyConfig.getLocation("location.spawnLocation");
      if (spawnLocation == null) spawnLocation = player.getWorld().getSpawnLocation();
      if (!player.getWorld().equals(lobbyWorld)) return;
      if (player.getLocation().getY() > voidLevel) return;
      player.setFallDistance(0);
      player.teleport(spawnLocation);
   }

}
