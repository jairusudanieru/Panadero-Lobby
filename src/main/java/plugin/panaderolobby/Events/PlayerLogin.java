package plugin.panaderolobby.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plugin.panaderocore.Methods.CoreConfig;
import plugin.panaderocore.Methods.WorldGroups;
import plugin.panaderolobby.Methods.LobbyConfig;
import plugin.panaderolobby.Methods.LobbyItem;

import java.util.List;
import java.util.Objects;

public class PlayerLogin implements Listener {

   @EventHandler
   public void onPlayerLogin(LoginEvent event) {
      Player player = event.getPlayer();
      sendPlayerToSpawn(player);
      Bukkit.getScheduler().runTaskLater(LobbyConfig.plugin, () -> {
         World playerWorld = player.getWorld();
         showOtherPlayers(player, playerWorld);
         sendJoinMessage(player, playerWorld);
         LobbyItem.giveLobbyItems(player);
      }, 1L);
   }

   private void showOtherPlayers(Player player, World world) {
      List<String> worldGroup = WorldGroups.sameGroupWorlds(world);
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         if (!AuthMeApi.getInstance().isAuthenticated(onlinePlayer)) continue;
         player.showPlayer(LobbyConfig.plugin, onlinePlayer);
         if (Objects.equals(onlinePlayer.getInventory().getItem(8), LobbyItem.GRAY_DYE())) continue;
         onlinePlayer.showPlayer(LobbyConfig.plugin, player);
      }
   }

   private boolean isLobbyWorld(Player player) {
      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) return false;
      List<String> worldGroup = WorldGroups.sameGroupWorlds(lobbyWorld);
      return worldGroup.contains(player.getWorld().getName());
   }

   private void sendPlayerToSpawn(Player player) {
      Location spawnLocation = LobbyConfig.getLocation("location.spawnLocation");
      if (spawnLocation == null) return;
      player.teleport(spawnLocation);
   }

   private void sendJoinMessage(Player player, World world) {
      List<String> worldGroup = WorldGroups.sameGroupWorlds(world);
      if (!AuthMeApi.getInstance().isAuthenticated(player)) return;
      String joinMessage = CoreConfig.getString("message.joinMessage");
      if (joinMessage == null || joinMessage.isEmpty()) return;
      joinMessage = joinMessage.replace("%player%",player.getName());

      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         if (isLobbyWorld(player)) continue;
         onlinePlayer.sendMessage(LobbyConfig.text(joinMessage));
      }
   }

}
