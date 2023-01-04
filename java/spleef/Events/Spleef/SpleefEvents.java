package spleef.Events.Spleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.QueBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Events.Global.GlobalEvents;
import spleef.ManagerMethods.SpleefMethods;
import spleef.ThreadedSQLandHandling.spleefBlockBreakThread;
import spleef.spluff;

import java.sql.SQLException;
import java.util.Objects;

import static spleef.ManagerMethods.StupidlyLargeMethods.renewArena;
import static spleef.ManagerMethods.SpleefMethods.*;
import static spleef.spluff.*;
import static spleef.spluff.challangeList;

public class SpleefEvents {

    spluff plugin;

    SpleefMethods spleefMethods;


    public SpleefEvents(spluff plugin){

        this.plugin = plugin;
        spleefMethods = new SpleefMethods(plugin);

    }

    public void ChallangeResult(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), accept)){

            for(QueBlueprint list : challangeList){

                if(list.challangeTarget.equals(event.getWhoClicked())){

                    for(SpleefBlueprint spleefBlueprint : spleefArenaList){

                        if(spleefBlueprint.name.equalsIgnoreCase(list.arenaName)){

                            if(!spleefBlueprint.que.isEmpty()){

                                if(!spleefBlueprint.que.get(0).equals(event.getWhoClicked())){

                                    spleefBlueprint.que.get(0).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bA match has started on the arena you selected, Please select a different arena!"));

                                }
                                spleefBlueprint.que.clear();

                            }
                            spleefBlueprint.que.add(list.challanger);
                            spleefBlueprint.que.add(list.challangeTarget);

                            challangeList.remove(list);

                            event.getWhoClicked().closeInventory();

                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fA game has started between &b&l"
                                    + spleefBlueprint.que.get(0).getPlayerListName()
                                    + " &fand &b&l"
                                    + spleefBlueprint.que.get(1).getPlayerListName()));


                            ScoreboardManager spleefBoardManager = Bukkit.getScoreboardManager();
                            assert spleefBoardManager != null;
                            Scoreboard spleefBoard = spleefBoardManager.getNewScoreboard();
                            Objective spleefMatch = spleefBoard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

                            Score a = spleefMatch.getScore("     ");
                            Score b;
                            Score c = spleefMatch.getScore("   ");
                            Score player1;
                            Score player2;
                            Score d = spleefMatch.getScore("  ");
                            Score e = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bPlaying to&c: &b" + spleefBlueprint.winPoints));
                            Score f = spleefMatch.getScore(" ");
                            Score g = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bDecaystate&c: " + spleefBlueprint.decayState));
                            Score h = spleefMatch.getScore("");

                            spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
                            spleefMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

                            a.setScore(9);

                            b = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(spleefBlueprint.icon.getItemMeta()).getDisplayName());
                            b.setScore(8);

                            c.setScore(7);

                            player1 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + spleefBlueprint.que.get(0).getPlayerListName() + "&c: &b0"));
                            player1.setScore(6);
                            player2 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + spleefBlueprint.que.get(1).getPlayerListName() + "&c: &b0"));
                            player2.setScore(5);

                            d.setScore(4);
                            e.setScore(3);
                            f.setScore(2);
                            g.setScore(1);
                            h.setScore(0);

                            spleefBlueprint.que.get(0).setScoreboard(spleefBoard);
                            spleefBlueprint.que.get(1).setScoreboard(spleefBoard);

                            challangeList.remove(list);

                            spleefMethods.startMatch(spleefBlueprint);

                        }

                    }

                }

            }
            event.setCancelled(true);

        }else if(Objects.equals(event.getCurrentItem(), deny)){

            for(QueBlueprint list : challangeList){

                if(list.challangeTarget.equals(event.getWhoClicked())){

                    list.challanger.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "Your oponent denied your challange!"));
                    challangeList.remove(list);
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();

                }

            }
            for(SpleefBlueprint arena : spleefArenaList){

                if(arena.active.contains(event.getWhoClicked())){

                    for(QueBlueprint list : challangeList){

                        if(arena.active.contains(list.challanger)){

                            challangeList.remove(list);

                        }
                        if(arena.active.contains(list.challangeTarget)){

                            challangeList.remove(list);

                        }

                    }
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();

                }

            }

            event.setCancelled(true);

        }

    }

    public void InitiateResetRequest(PlayerDropItemEvent event){

        if(event.getItemDrop().getItemStack().equals(shovel)){

            for(SpleefBlueprint spleefBlueprint : spleefArenaList){

                if(spleefBlueprint.active.contains(event.getPlayer())){

                    resetArena(spleefBlueprint, event.getPlayer());

                }

            }

            event.setCancelled(true);

        }

    }

    public void InitiateGame(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), playSpleef)){

            event.getWhoClicked().closeInventory();
            event.getWhoClicked().openInventory(spleefMenu);
            event.setCancelled(true);

        }

        for (SpleefBlueprint spleefBlueprint : spleefArenaList){

            if(spleefBlueprint.que.contains( (Player) event.getWhoClicked())){

                if (Objects.equals(event.getCurrentItem(), spleefBlueprint.icon) && spleefBlueprint.que.size() < 2) {

                    event.getWhoClicked().closeInventory();

                    ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have already joined this que!"));

                    event.setCancelled(true);

                }else if(Objects.equals(event.getCurrentItem(), spleefBlueprint.icon)){

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThis arena is already in use&c! &bPlease select another one&c..."));
                    event.setCancelled(true);

                }

            }else{

                if (Objects.equals(event.getCurrentItem(), spleefBlueprint.icon) && spleefBlueprint.que.size() < 2) {

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

                    spleefBlueprint.que.add((Player) event.getWhoClicked());

                    if(spleefBlueprint.que.size() == 2){

                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fA game has started between &b&l"
                                + spleefBlueprint.que.get(0).getPlayerListName()
                                + " &fand &b&l"
                                + spleefBlueprint.que.get(1).getPlayerListName()));


                        ScoreboardManager spleefBoardManager = Bukkit.getScoreboardManager();
                        assert spleefBoardManager != null;
                        Scoreboard spleefBoard = spleefBoardManager.getNewScoreboard();
                        Objective spleefMatch = spleefBoard.registerNewObjective(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

                        Score a = spleefMatch.getScore("     ");
                        Score b;
                        Score c = spleefMatch.getScore("   ");
                        Score player1;
                        Score player2;
                        Score d = spleefMatch.getScore("  ");
                        Score e = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bPlaying to&c: &b" + spleefBlueprint.winPoints));
                        Score f = spleefMatch.getScore(" ");
                        Score g = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bDecaystate&c: " + spleefBlueprint.decayState));
                        Score h = spleefMatch.getScore("");

                        spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
                        spleefMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

                        a.setScore(9);

                        b = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(spleefBlueprint.icon.getItemMeta()).getDisplayName());
                        b.setScore(8);

                        c.setScore(7);

                        player1 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + spleefBlueprint.que.get(0).getPlayerListName() + "&c: &b0"));
                        player1.setScore(6);
                        player2 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + spleefBlueprint.que.get(1).getPlayerListName() + "&c: &b0"));
                        player2.setScore(5);

                        d.setScore(4);
                        e.setScore(3);
                        f.setScore(2);
                        g.setScore(1);
                        h.setScore(0);

                        spleefBlueprint.que.get(0).setScoreboard(spleefBoard);
                        spleefBlueprint.que.get(1).setScoreboard(spleefBoard);

                        spleefMethods.startMatch(spleefBlueprint);

                    }else{

                        event.getWhoClicked().getInventory().setItem(8, exitQue);
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l"
                                + ((Player) event.getWhoClicked()).getPlayerListName()
                                + " &fHas joined the "
                                + Objects.requireNonNull(spleefBlueprint.icon.getItemMeta()).getDisplayName()
                                + " &fque&b&l! &b(" + spleefBlueprint.que.size()
                                +"/2)"));


                    }

                    event.setCancelled(true);

                }else if(Objects.equals(event.getCurrentItem(), spleefBlueprint.icon)){

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThis arena is already in use&c! &bPlease select another one&c..."));
                    event.setCancelled(true);

                }

            }

        }


    }

    public void HotbarInteraction(PlayerInteractEvent event){

        if(!(event.getItem() == null)){

            if(event.getAction().equals(Action.LEFT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(compass)
                    || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(compass)
                    || event.getAction().equals(Action.RIGHT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(compass)
                    || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(compass)){


                event.getPlayer().openInventory(menu);

            }

        }

        if(!(event.getItem() == null)){

            if(event.getAction().equals(Action.LEFT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.RIGHT_CLICK_AIR) && Objects.requireNonNull(event.getItem()).equals(exitQue)
                    || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && Objects.requireNonNull(event.getItem()).equals(exitQue)){


                SpleefBlueprint i = null;

                for(SpleefBlueprint spleefBlueprint : spleefArenaList){

                    if(spleefBlueprint.que.contains(event.getPlayer())){

                        spleefBlueprint.que.remove(event.getPlayer());

                        event.getPlayer().getInventory().setItem(8, new ItemStack(Material.AIR));

                        i = spleefBlueprint;

                    }

                }

                if(i != null){

                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l" + (event.getPlayer().getPlayerListName() + " &fhas left the " + Objects.requireNonNull(i.icon.getItemMeta()).getDisplayName() + " &fqueue!")));

                }

            }

        }

    }

    public void CancellChallange(InventoryCloseEvent event){

        challangeList.removeIf(list -> list.challangeTarget.equals(event.getPlayer()));

    }

    public void KillPlayer(PlayerMoveEvent event) throws SQLException {

        for (SpleefBlueprint spleefBlueprint : spleefArenaList) {

            if(!spleefBlueprint.active.isEmpty()){

                if (spleefBlueprint.active.contains(event.getPlayer())) {

                    if (spleefBlueprint.active.get(0).getLocation().getY() < spleefBlueprint.locationy) {

                        spleefBlueprint.points2++;

                        spleefBlueprint.minispleef = false;

                        matchPoint(spleefBlueprint, spleefBlueprint.active.get(1), spleefBlueprint.active.get(0), spleefBlueprint.points2, spleefBlueprint.points1);

                    } else if (spleefBlueprint.active.get(1).getLocation().getY() < spleefBlueprint.locationy) {

                        spleefBlueprint.points1++;

                        spleefBlueprint.minispleef = false;

                        matchPoint(spleefBlueprint, spleefBlueprint.active.get(0), spleefBlueprint.active.get(1), spleefBlueprint.points1, spleefBlueprint.points2);


                    }


                }

            }

        }

    }

    public void FixDecay(EntityChangeBlockEvent event){

        if(event.getEntityType()== EntityType.FALLING_BLOCK && event.getTo()==Material.AIR){

            if(event.getBlock().getType()==lime.getType()
                    ||event.getBlock().getType()==lime.getType()
                    ||event.getBlock().getType()==lime.getType()){

                event.setCancelled(true);
                event.getBlock().getState().update(false, false);

            }

        }

    }

    public void AenablockBreak(BlockBreakEvent event){

        if(event.getBlock().getType().equals(Material.SNOW_BLOCK)
                ||event.getBlock().getType().equals(red.getType())
                ||event.getBlock().getType().equals(yellow.getType())
                ||event.getBlock().getType().equals(lime.getType())){

            if(event.getPlayer().getItemInHand().equals(shovel)){

                event.setDropItems(false);

                spleefBlockBreakThread tr = new spleefBlockBreakThread(event);
                tr.start();

            }

        }

    }
    public void PreventInGamePVP(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

            for(SpleefBlueprint spleefBlueprint : spleefArenaList){

                if(spleefBlueprint.active.contains( (Player) event.getEntity())){

                    event.setCancelled(true);

                }

            }

        }

    }



    /** Overlapping Methods **/
    public void Cancel(InventoryClickEvent event){


        if(Objects.equals(event.getCurrentItem(), shovel)){

            event.setCancelled(true);

        }


    }
    public void Cancel(PlayerDropItemEvent event){

        if(event.getItemDrop().getItemStack().equals(guide)){

            event.setCancelled(true);
            event.getPlayer().getInventory().setItem(0, guide);

        }

        if(event.getItemDrop().getItemStack().equals(compass)){

            event.setCancelled(true);

        }

        if(event.getItemDrop().getItemStack().equals(exitQue)){

            event.setCancelled(true);

        }

    }
    public void Cancel(PlayerSwapHandItemsEvent event){

        event.setCancelled(true);

    }
    public void SuspendFromGame(PlayerQuitEvent event){

        for(SpleefBlueprint spleefBlueprint : spleefArenaList){

            if(spleefBlueprint.active.contains(event.getPlayer())){

                for(Player player : spleefBlueprint.active){

                    spleefBlueprint.spectators.remove(event.getPlayer());

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

                    spleefBlueprint.que.get(0).teleport(spawn);
                    spleefBlueprint.que.get(0).setScoreboard(lobbyboard);
                    spleefBlueprint.que.get(1).teleport(spawn);
                    spleefBlueprint.que.get(1).setScoreboard(lobbyboard);

                    spleefBlueprint.resetQue.remove(player);
                    spleefBlueprint.points2 = 0;
                    spleefBlueprint.points1 = 0;

                    if(!spleefBlueprint.spectators.isEmpty()){

                        for(Player p : spleefBlueprint.spectators){

                            p.getInventory().clear();
                            p.getInventory().setItem(0, guide);
                            p.getInventory().setItem(4, compass);
                            p.setGameMode(GameMode.ADVENTURE);
                            p.teleport(spawn);
                            p.setScoreboard(lobbyboard);


                        }

                    }

                }

                spleefBlueprint.active.clear();
                spleefBlueprint.que.clear();
                renewArena(spleefBlueprint);

            }

            else if(spleefBlueprint.que.contains(event.getPlayer())){

                spleefBlueprint.que.clear();
                spleefBlueprint.active.clear();

                if(spleefBlueprint.beginDecay != null){
                    spleefBlueprint.beginDecay.cancel();
                }
                if(spleefBlueprint.beginMinispleef != null){
                    spleefBlueprint.beginMinispleef.cancel();
                }
                renewArena(spleefBlueprint);

            }

        }

    }

}
