package plugin.panaderolobby.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugin.panaderolobby.Methods.InventoryGUI;
import plugin.panaderolobby.Methods.LobbyConfig;

import java.util.Arrays;
import java.util.List;

public class InventoryMove implements Listener {

   @EventHandler
   public void onInventoryDrag(InventoryDragEvent event) {
      Player player = (Player) event.getWhoClicked();
      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) return;

      if (!player.getWorld().equals(lobbyWorld)) return;
      if (player.getGameMode().equals(GameMode.CREATIVE)) return;
      if (!event.getInventory().equals(InventoryGUI.getInventory())) return;
      event.setCancelled(true);
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      Player player = (Player) event.getWhoClicked();
      Inventory customInventory = InventoryGUI.getInventory();
      Inventory playerInventory = player.getInventory();

      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) return;
      if (!player.getWorld().equals(lobbyWorld)) return;
      if (player.getGameMode().equals(GameMode.CREATIVE)) return;

      Inventory clickedInventory = event.getClickedInventory();
      if (clickedInventory == null) return;

      ItemStack cursorItem = event.getCursor();
      if (cursorItem == null) return;
      if (clickedInventory.equals(customInventory)) event.setCancelled(true);

      List<ItemStack> selectorItems = Arrays.asList(
              customInventory.getItem(10),
              customInventory.getItem(13),
              customInventory.getItem(16)
      );

      List<ItemStack> lobbyItems = Arrays.asList(
              playerInventory.getItem(0),
              playerInventory.getItem(4),
              playerInventory.getItem(8)
      );

      if (event.getClick().equals(ClickType.SWAP_OFFHAND)) {
         event.setCancelled(true);
         return;
      }

      if (clickedInventory.equals(customInventory)) {
         if (selectorItems.contains(event.getCurrentItem())) event.setCancelled(true);
         if (event.getClick().isKeyboardClick()) {
            int slot = event.getHotbarButton();
            ItemStack itemStack = player.getInventory().getItem(slot);
            if (itemStack == null) return;
            if (lobbyItems.contains(itemStack)) event.setCancelled(true);
         }

         if (!event.getClick().isLeftClick()) return;
         if (event.getSlot() == 10) {
            event.setCancelled(true);
            Location location = LobbyConfig.getLocation("location.creativeLocation");
            if (location == null) return;
            player.teleport(location);
            player.sendMessage("Teleported to Creative!");
            Bukkit.getScheduler().runTask(LobbyConfig.plugin, () -> player.closeInventory());
         } else if (event.getSlot() == 13) {
            event.setCancelled(true);
            Location location = LobbyConfig.getLocation("location.survivalLocation");
            if (location == null) return;
            player.teleport(location);
            player.sendMessage("Teleported to Survival!");
            Bukkit.getScheduler().runTask(LobbyConfig.plugin, () -> player.closeInventory());
         } else if (event.getSlot() == 16) {
            event.setCancelled(true);
            Location location = LobbyConfig.getLocation("location.pvpArenaLocation");
            if (location == null) return;
            player.teleport(location);
            player.sendMessage("Teleported to PVP Arena!");
            Bukkit.getScheduler().runTask(LobbyConfig.plugin, () -> player.closeInventory());
         }
      }

      if (clickedInventory.equals(playerInventory)) {
         if (lobbyItems.contains(event.getCurrentItem())) event.setCancelled(true);
         if (event.getClick().isKeyboardClick()) {
            int slot = event.getHotbarButton();
            ItemStack itemStack = player.getInventory().getItem(slot);
            if (itemStack == null) return;
            if (lobbyItems.contains(itemStack)) event.setCancelled(true);
         }
      }
   }

   @EventHandler
   public void onItemSwap(PlayerSwapHandItemsEvent event) {
      Player player = event.getPlayer();
      World lobbyWorld = LobbyConfig.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) return;
      if (!player.getWorld().equals(lobbyWorld)) return;
      if (player.getGameMode().equals(GameMode.CREATIVE)) return;
      event.setCancelled(true);
   }

}
