package spleef.Events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;
import spleef.Blueprints.ArenaBlueprint;
import spleef.Blueprints.FfaBlueprint;
import spleef.spluff;

import java.util.Objects;

import static spleef.spluff.*;

public class FFAevents implements Listener {

    spluff plugin;

    FfaBlueprint arena;

    public static BukkitTask regenerateBlocks;
    public FFAevents(FfaBlueprint arena, spluff plugin){

        this.arena = arena;

        this.plugin = plugin;

    }
        /*
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

                if(arena.active.contains(event.getPlayer())){

            if(event.getPlayer().getLocation().getY() < arena.locationy){

                killFfaPlayer(arena, event.getPlayer(), plugin);

            }

        }else if(arena.que.size() < 3 && arena.active.isEmpty() && arena.que.contains(event.getPlayer())){

            if(event.getPlayer().getLocation().getY() < arena.locationy){

                killFfaPlayer(arena, event.getPlayer(), plugin);

            }

        }

    }
             */

    @EventHandler
    public void onClickFfa(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), arena.icon)){

            if(arena.que.contains( (Player) event.getWhoClicked())) {

                if(arena.active.contains(((Player) event.getWhoClicked()))){

                    killFfaPlayer(arena, ((Player) event.getWhoClicked()), plugin);

                }

                ((Player) event.getWhoClicked()).setAllowFlight(true);
                ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have left &cFFA!"));

                event.getWhoClicked().teleport(spawn);
                event.getWhoClicked().getInventory().clear();
                event.getWhoClicked().getInventory().setItem(0, guide);
                event.getWhoClicked().getInventory().setItem(4, compass);

                arena.que.remove( (Player) event.getWhoClicked());
                arena.spectator.remove( (Player) event.getWhoClicked());

            }else{

                for(ArenaBlueprint arenaBlueprint : arenaList){

                    arenaBlueprint.que.remove( (Player) event.getWhoClicked());

                }

                arena.que.add( (Player) event.getWhoClicked());

                if(arena.que.size() > 2){

                    startFfa(arena, plugin);

                }else{

                    startZenMode(arena, (Player) event.getWhoClicked());


                }

            }

            event.setCancelled(true);

        }

        if(event.getCurrentItem().equals(leave)) {

            if(arena.active.contains(((Player) event.getWhoClicked()))){

                killFfaPlayer(arena, ((Player) event.getWhoClicked()), plugin);

            }

            event.getWhoClicked().setGameMode(GameMode.ADVENTURE);
            event.getWhoClicked().teleport(spawn);
            event.getWhoClicked().getInventory().clear();
            event.getWhoClicked().getInventory().setItem(0, guide);
            event.getWhoClicked().getInventory().setItem(4, compass);

            ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have left &cFFA!"));

            arena.que.remove( (Player) event.getWhoClicked());
            arena.spectator.remove( (Player) event.getWhoClicked());
            arena.active.remove( (Player) event.getWhoClicked());

            if(arena.active.size() == 2){

                arena.active.clear();

            }

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        /*

                if(arena.que != null){

            if(arena.que.contains(event.getPlayer().getPlayer())){

                if(!event.getBlock().getType().equals(Material.SNOW_BLOCK)){

                    event.setCancelled(true);

                }

            }

            for(Player player : arena.que){

                if(arena.allowBreak){

                    if(event.getPlayer().equals(player) && event.getBlock().getType().equals(Material.SNOW_BLOCK)){

                        regenerateBlocks = new ffaBlockGenerator(event.getBlock()).runTaskLater(plugin, 150);

                    }

                }else{

                    event.setCancelled(true);

                }

            }

        }

         */

    }

}
