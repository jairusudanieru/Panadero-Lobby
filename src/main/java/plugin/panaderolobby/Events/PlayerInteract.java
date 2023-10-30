package plugin.panaderolobby.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugin.panaderocore.Methods.WorldGroups;
import plugin.panaderolobby.Methods.InventoryGUI;
import plugin.panaderolobby.Methods.LobbyConfig;
import plugin.panaderolobby.Methods.LobbyItem;

import java.util.List;

public class PlayerInteract implements Listener {

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) return;
      Player player = event.getPlayer();
      ItemStack item = event.getItem();

      if (!player.getWorld().equals(lobbyWorld)) return;
      Action action = event.getAction();
      if (!action.isRightClick() || item == null) return;

      if (item.equals(LobbyItem.COMPASS())) {
         Inventory inventory = InventoryGUI.getInventory();
         player.openInventory(inventory);
      } else if (item.equals(LobbyItem.LIME_DYE())) {
         hideOthers(player);
         player.getInventory().setItem(8, LobbyItem.GRAY_DYE());
      } else if (item.equals(LobbyItem.GRAY_DYE())) {
         showOthers(player);
         player.getInventory().setItem(8, LobbyItem.LIME_DYE());
      } else if (item.equals(LobbyItem.INFO_BOOK())) {
         player.sendMessage(LobbyConfig.text("<reset>Welcome To Pandesal!"));
      }
   }

   private void showOthers(Player player) {
      List<String> worldGroup = WorldGroups.sameGroupWorlds(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         if (!AuthMeApi.getInstance().isAuthenticated(onlinePlayer)) continue;
         player.showPlayer(LobbyConfig.plugin, onlinePlayer);
      }
   }

   private void hideOthers(Player player) {
      List<String> worldGroup = WorldGroups.sameGroupWorlds(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         player.hidePlayer(LobbyConfig.plugin, onlinePlayer);
      }
   }

}
