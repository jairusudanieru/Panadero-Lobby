package plugin.panaderolobby.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import plugin.panaderocore.Methods.CoreConfig;
import plugin.panaderocore.Methods.WorldGroups;
import plugin.panaderolobby.Methods.LobbyConfig;
import plugin.panaderolobby.Methods.LobbyItem;

import java.util.List;

public class ChangeWorld implements Listener {

   @EventHandler
   public void onChangeWorld(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      World previousWorld = event.getFrom();

      List<String> worldGroup = WorldGroups.sameGroupWorlds(playerWorld);
      List<String> fromGroup = WorldGroups.sameGroupWorlds(previousWorld);

      if (worldGroup.contains(playerWorld.getName()) && !worldGroup.contains(previousWorld.getName())) {
         if (isLobbyWorld(playerWorld)) LobbyItem.giveLobbyItems(player);
         for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
            player.showPlayer(LobbyConfig.plugin, onlinePlayer);
            onlinePlayer.showPlayer(LobbyConfig.plugin, player);
            sendJoinMessage(player, playerWorld, onlinePlayer);
         }
      }

      if (fromGroup.contains(previousWorld.getName()) && !fromGroup.contains(playerWorld.getName())) {
         for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!fromGroup.contains(onlinePlayer.getWorld().getName())) continue;
            player.hidePlayer(LobbyConfig.plugin, onlinePlayer);
            onlinePlayer.hidePlayer(LobbyConfig.plugin, player);
            sendQuitMessage(player, previousWorld, onlinePlayer);
         }
      }
   }

   private boolean isLobbyWorld(World world) {
      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) return false;
      List<String> worldGroup = WorldGroups.sameGroupWorlds(lobbyWorld);
      return worldGroup.contains(world.getName());
   }

   private void sendJoinMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = WorldGroups.sameGroupWorlds(world);
      if (!AuthMeApi.getInstance().isAuthenticated(player)) return;
      String joinMessage = CoreConfig.getString("message.joinMessage");
      if (joinMessage == null || joinMessage.isEmpty()) return;
      joinMessage = joinMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (isLobbyWorld(world)) return;
      onlinePlayer.sendMessage(LobbyConfig.text(joinMessage));
   }

   private void sendQuitMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = WorldGroups.sameGroupWorlds(world);
      if (!AuthMeApi.getInstance().isAuthenticated(player)) return;
      String quitMessage = CoreConfig.getString("message.quitMessage");
      if (quitMessage == null || quitMessage.isEmpty()) return;
      quitMessage = quitMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (isLobbyWorld(world)) return;
      onlinePlayer.sendMessage(LobbyConfig.text(quitMessage));
   }

}