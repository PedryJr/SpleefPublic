package spleef.Events.Parkour;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Events.Global.GlobalEvents;
import spleef.ManagerMethods.ParkourMethods;
import spleef.ManagerMethods.StupidlyLargeMethods;
import spleef.spluff;

import java.util.Objects;

import static spleef.spluff.*;

public class ParkourEvents {

    spluff plugin;
    ParkourMethods parkourMethods;

    GlobalEvents globalEvents;
    public ParkourEvents(spluff plugin){

        this.plugin = plugin;

        parkourMethods = new ParkourMethods(plugin);

        globalEvents = new GlobalEvents(plugin);

    }

    public void PreventInGamePVP(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

            for(ParkourBlueprint parkourBlueprint : parkourArenaList){

                if(parkourBlueprint.active.contains( (Player) event.getEntity())){

                    event.setCancelled(true);

                }

            }

        }

    }

    public void finish(PlayerMoveEvent event){

        for(ParkourBlueprint parkourBlueprint : parkourArenaList){

            if(parkourBlueprint.active.contains(event.getPlayer())){

                if(event.getPlayer().getLocation().distance(parkourBlueprint.finish) < 1.5){

                    parkourBlueprint.active.remove(event.getPlayer());
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + event.getPlayer().getDisplayName() + " &foutran &b" + parkourBlueprint.active.get(0).getDisplayName() + " &fin parkour!"));

                    event.getPlayer().teleport(spawn);
                    event.getPlayer().getInventory().clear();
                    event.getPlayer().getInventory().setItem(4, compass);

                    parkourBlueprint.active.get(0).teleport(spawn);
                    parkourBlueprint.active.get(0).getInventory().clear();
                    parkourBlueprint.active.get(0).getInventory().setItem(4, compass);

                    parkourMethods.RenewArena(parkourBlueprint);


                }

            }

        }

    }

    public void Cancel(InventoryClickEvent event){



    }

    public void InitiateGame(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), playParkour)){

            event.getWhoClicked().closeInventory();
            event.getWhoClicked().openInventory(parkourMenu);
            event.setCancelled(true);

        }

        for (ParkourBlueprint parkourBlueprint : parkourArenaList){

            if(parkourBlueprint.que.contains( (Player) event.getWhoClicked())){

                if (Objects.equals(event.getCurrentItem(), parkourBlueprint.icon) && parkourBlueprint.que.size() < 2) {

                    event.getWhoClicked().closeInventory();

                    ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have already joined this que!"));

                    event.setCancelled(true);

                }else if(Objects.equals(event.getCurrentItem(), parkourBlueprint.icon)){

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThis arena is already in use&c! &bPlease select another one&c..."));
                    event.setCancelled(true);

                }

            }else{

                if (Objects.equals(event.getCurrentItem(), parkourBlueprint.icon) && parkourBlueprint.que.size() < 2) {

                    for(SpleefBlueprint spleefBlueprint1 : spleefArenaList){

                        if(spleefBlueprint1.que.contains( (Player) event.getWhoClicked())){

                            spleefBlueprint1.que.remove( (Player) event.getWhoClicked());

                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l"
                                    + (Objects.requireNonNull(((Player) event.getWhoClicked()).getPlayer()).getPlayerListName()
                                    + " &fhas left the "
                                    + Objects.requireNonNull(spleefBlueprint1.icon.getItemMeta()).getDisplayName()
                                    + " &fqueue!")));


                        }

                    }

                    event.getWhoClicked().closeInventory();

                    parkourBlueprint.que.add((Player) event.getWhoClicked());

                    if(parkourBlueprint.que.size() == 2){

                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fA game has started between &b&l"
                                + parkourBlueprint.que.get(0).getPlayerListName()
                                + " &fand &b&l"
                                + parkourBlueprint.que.get(1).getPlayerListName()));

                        parkourBlueprint.que.get(0).teleport(parkourBlueprint.loc);
                        parkourBlueprint.que.get(1).teleport(parkourBlueprint.loc);

                        parkourBlueprint.que.get(0).getInventory().clear();
                        parkourBlueprint.que.get(1).getInventory().clear();

                        parkourBlueprint.active.add(parkourBlueprint.que.get(0));
                        parkourBlueprint.active.add(parkourBlueprint.que.get(1));


                    }else{

                        event.getWhoClicked().getInventory().setItem(8, exitQue);
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l"
                                + ((Player) event.getWhoClicked()).getPlayerListName()
                                + " &fHas joined the "
                                + Objects.requireNonNull(parkourBlueprint.icon.getItemMeta()).getDisplayName()
                                + " &fque&b&l! &b(" + parkourBlueprint.que.size()
                                +"/2)"));


                    }

                    event.setCancelled(true);

                }else if(Objects.equals(event.getCurrentItem(), parkourBlueprint.icon)){

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThis arena is already in use&c! &bPlease select another one&c..."));
                    event.setCancelled(true);

                }

            }

        }


    }

    public void LeaveQue(PlayerInteractEvent event){

        if(!(event.getItem() == null)){

            if(event.getAction().equals(Action.LEFT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.RIGHT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(exitQue)){


                ParkourBlueprint i = null;

                for(ParkourBlueprint parkourBlueprint : parkourArenaList){

                    if(parkourBlueprint.que.contains(event.getPlayer())){

                        parkourBlueprint.que.remove(event.getPlayer());

                        event.getPlayer().getInventory().setItem(8, new ItemStack(Material.AIR));

                        i = parkourBlueprint;

                    }

                }

                if(i != null){

                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l" + (event.getPlayer().getPlayerListName() + " &fhas left the " + Objects.requireNonNull(i.icon.getItemMeta()).getDisplayName() + " &fqueue!")));

                }

            }

        }

    }

    public void SuspendFromGame(PlayerQuitEvent event){

        for(ParkourBlueprint parkourBlueprint : parkourArenaList){

            if(parkourBlueprint.active.contains(event.getPlayer())){

                for(Player player : parkourBlueprint.active){

                    parkourBlueprint.spectators.remove(event.getPlayer());

                    ScoreboardManager lobbyboardmanager = Bukkit.getScoreboardManager();
                    assert lobbyboardmanager != null;
                    Scoreboard lobbyboard = lobbyboardmanager.getNewScoreboard();
                    Objective lobby = lobbyboard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

                    Score i = lobby.getScore("   ");
                    i.setScore(4);

                    Score j = lobby.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lWelcome&f&l!"));
                    j.setScore(3);

                    Score k = lobby.getScore("  ");
                    k.setScore(2);

                    Score l = lobby.getScore(ChatColor.translateAlternateColorCodes('&', "  &c&nIn development"));
                    l.setScore(1);

                    Score m = lobby.getScore("");
                    m.setScore(0);

                    lobby.setDisplaySlot(DisplaySlot.SIDEBAR);
                    lobby.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

                    player.setScoreboard(lobbyboard);
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&b&l" + event.getPlayer().getPlayerListName()
                            + " &fhas left the game!"));

                    player.getInventory().clear();
                    player.getInventory().setItem(0, guide);
                    player.getInventory().setItem(4, compass);

                    player.setGameMode(GameMode.ADVENTURE);

                    parkourBlueprint.que.get(0).teleport(spawn);
                    parkourBlueprint.que.get(0).setScoreboard(lobbyboard);
                    parkourBlueprint.que.get(1).teleport(spawn);
                    parkourBlueprint.que.get(1).setScoreboard(lobbyboard);

                    if(!parkourBlueprint.spectators.isEmpty()){

                        for(Player p : parkourBlueprint.spectators){

                            p.getInventory().clear();
                            p.getInventory().setItem(0, guide);
                            p.getInventory().setItem(4, compass);
                            p.setGameMode(GameMode.ADVENTURE);
                            p.teleport(spawn);
                            p.setScoreboard(lobbyboard);


                        }

                    }

                }

                parkourBlueprint.active.clear();
                parkourBlueprint.que.clear();

            }

            else if(parkourBlueprint.que.contains(event.getPlayer())){

                parkourBlueprint.que.clear();
                parkourBlueprint.active.clear();

            }

        }

    }

}
