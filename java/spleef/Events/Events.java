package spleef.Events;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.ArenaBlueprint;
import spleef.Blueprints.QueBlueprint;
import spleef.LargeMethods.StupidlyLargeMethods;
import spleef.Translator.Translator;
import spleef.spluff;
import spleef.ThreadedSQLandHandling.spleefBlockBreakThread;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static spleef.LargeMethods.StupidlyLargeMethods.renewArena;
import static spleef.spluff.*;
import static spleef.commands.challangeCommand.*;

public class Events implements Listener {



    spluff plugin;

    public Events(spluff plugin){

        this.plugin = plugin;

    }

    @EventHandler
    public void onItemHotbarClick(PlayerInteractEvent event){

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


                ArenaBlueprint i = null;

                for(ArenaBlueprint arenaBlueprint : arenaList){

                    if(arenaBlueprint.que.contains(event.getPlayer())){

                        arenaBlueprint.que.remove(event.getPlayer());

                        event.getPlayer().getInventory().setItem(8, new ItemStack(Material.AIR));

                        i = arenaBlueprint;

                    }

                }

                if(i != null){

                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l" + (event.getPlayer().getPlayerListName() + " &fhas left the " + Objects.requireNonNull(i.icon.getItemMeta()).getDisplayName() + " &fqueue!")));

                }

            }

        }

    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){

        if(event.getItemDrop().getItemStack().equals(shovel)){

            for(ArenaBlueprint arenaBlueprint: arenaList){

                if(arenaBlueprint.active.contains(event.getPlayer())){

                    resetArena(arenaBlueprint, event.getPlayer());

                }

            }

            event.setCancelled(true);

        }

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

        if(event.getItemDrop().getItemStack().equals(leave)){

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event){

        challangeList.removeIf(list -> list.challangeTarget.equals(event.getPlayer()));

    }

    @EventHandler
    public void menuInteraction(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), accept)){

            for(QueBlueprint list : challangeList){

                if(list.challangeTarget.equals(event.getWhoClicked())){

                    for(ArenaBlueprint arenaBlueprint : arenaList){

                        if(arenaBlueprint.name.equalsIgnoreCase(list.arenaName)){

                            arenaBlueprint.que.add(list.challanger);
                            arenaBlueprint.que.add(list.challangeTarget);
                            challangeList.remove(list);

                            event.getWhoClicked().closeInventory();

                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fA game has started between &b&l"
                                    + arenaBlueprint.que.get(0).getPlayerListName()
                                    + " &fand &b&l"
                                    + arenaBlueprint.que.get(1).getPlayerListName()));


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
                            Score e = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bPlaying to&c: &b" + arenaBlueprint.winPoints));
                            Score f = spleefMatch.getScore(" ");
                            Score g = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bDecaystate&c: " + arenaBlueprint.decayState));
                            Score h = spleefMatch.getScore("");

                            spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
                            spleefMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

                            a.setScore(9);

                            b = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(arenaBlueprint.icon.getItemMeta()).getDisplayName());
                            b.setScore(8);

                            c.setScore(7);

                            player1 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arenaBlueprint.que.get(0).getPlayerListName() + "&c: &b0"));
                            player1.setScore(6);
                            player2 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arenaBlueprint.que.get(1).getPlayerListName() + "&c: &b0"));
                            player2.setScore(5);

                            d.setScore(4);
                            e.setScore(3);
                            f.setScore(2);
                            g.setScore(1);
                            h.setScore(0);

                            arenaBlueprint.que.get(0).setScoreboard(spleefBoard);
                            arenaBlueprint.que.get(1).setScoreboard(spleefBoard);

                            challangeList.remove(list);

                            startMatch(arenaBlueprint);

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
            for(ArenaBlueprint arena : arenaList){

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

        if(Objects.equals(event.getCurrentItem(), shovel)){

            event.setCancelled(true);

        }

        if(Objects.equals(event.getCurrentItem(), padding)){

            event.setCancelled(true);

        } else if(Objects.equals(event.getCurrentItem(), play)){

            event.getWhoClicked().closeInventory();
            event.getWhoClicked().openInventory(gameMenu);
            event.setCancelled(true);

        }

        if(Objects.equals(event.getCurrentItem(), exitQue)){

            event.setCancelled(true);

        }

        for (ArenaBlueprint arenaBlueprint : arenaList){

            if(arenaBlueprint.que.contains( (Player) event.getWhoClicked())){

                if (Objects.equals(event.getCurrentItem(), arenaBlueprint.icon) && arenaBlueprint.que.size() < 2) {

                    event.getWhoClicked().closeInventory();

                    ((Player) event.getWhoClicked()).sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou have already joined this que!"));

                    event.setCancelled(true);

                }else if(Objects.equals(event.getCurrentItem(), arenaBlueprint.icon)){

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThis arena is already in use&c! &bPlease select another one&c..."));
                    event.setCancelled(true);

                }

            }else{

                if (Objects.equals(event.getCurrentItem(), arenaBlueprint.icon) && arenaBlueprint.que.size() < 2) {

                    for(ArenaBlueprint arenaBlueprint1 : arenaList){

                        if(arenaBlueprint1.que.contains( (Player) event.getWhoClicked())){

                            arenaBlueprint1.que.remove( (Player) event.getWhoClicked());

                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l"
                                    + (Objects.requireNonNull(((Player) event.getWhoClicked()).getPlayer()).getPlayerListName()
                                    + " &fhas left the "
                                    + Objects.requireNonNull(arenaBlueprint1.icon.getItemMeta()).getDisplayName()
                                    + " &fqueue!")));


                        }

                    }

                    event.getWhoClicked().closeInventory();

                    arenaBlueprint.que.add((Player) event.getWhoClicked());

                    if(arenaBlueprint.que.size() == 2){

                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fA game has started between &b&l"
                                + arenaBlueprint.que.get(0).getPlayerListName()
                                + " &fand &b&l"
                                + arenaBlueprint.que.get(1).getPlayerListName()));


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
                        Score e = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bPlaying to&c: &b" + arenaBlueprint.winPoints));
                        Score f = spleefMatch.getScore(" ");
                        Score g = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bDecaystate&c: " + arenaBlueprint.decayState));
                        Score h = spleefMatch.getScore("");

                        spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
                        spleefMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

                        a.setScore(9);

                        b = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(arenaBlueprint.icon.getItemMeta()).getDisplayName());
                        b.setScore(8);

                        c.setScore(7);

                        player1 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arenaBlueprint.que.get(0).getPlayerListName() + "&c: &b0"));
                        player1.setScore(6);
                        player2 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arenaBlueprint.que.get(1).getPlayerListName() + "&c: &b0"));
                        player2.setScore(5);

                        d.setScore(4);
                        e.setScore(3);
                        f.setScore(2);
                        g.setScore(1);
                        h.setScore(0);

                        arenaBlueprint.que.get(0).setScoreboard(spleefBoard);
                        arenaBlueprint.que.get(1).setScoreboard(spleefBoard);

                        startMatch(arenaBlueprint);

                    }else{

                        event.getWhoClicked().getInventory().setItem(8, exitQue);
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l"
                                + ((Player) event.getWhoClicked()).getPlayerListName()
                                + " &fHas joined the "
                                + Objects.requireNonNull(arenaBlueprint.icon.getItemMeta()).getDisplayName()
                                + " &fque&b&l! &b(" + arenaBlueprint.que.size()
                                +"/2)"));


                    }

                    event.setCancelled(true);

                }else if(Objects.equals(event.getCurrentItem(), arenaBlueprint.icon)){

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bThis arena is already in use&c! &bPlease select another one&c..."));
                    event.setCancelled(true);

                }

            }

        }

    }

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent event) throws SQLException {

        for (ArenaBlueprint arenaBlueprint : arenaList) {

            if(!arenaBlueprint.active.isEmpty()){

                if (arenaBlueprint.active.contains(event.getPlayer())) {

                    if (arenaBlueprint.active.get(0).getLocation().getY() < arenaBlueprint.locationy) {

                        arenaBlueprint.points2++;

                        arenaBlueprint.minispleef = false;

                        matchPoint(arenaBlueprint, arenaBlueprint.active.get(1), arenaBlueprint.active.get(0), arenaBlueprint.points2, arenaBlueprint.points1);

                    } else if (arenaBlueprint.active.get(1).getLocation().getY() < arenaBlueprint.locationy) {

                        arenaBlueprint.points1++;

                        arenaBlueprint.minispleef = false;

                        matchPoint(arenaBlueprint, arenaBlueprint.active.get(0), arenaBlueprint.active.get(1), arenaBlueprint.points1, arenaBlueprint.points2);


                    }


                }

            }

        }

    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    private void onSandFall(EntityChangeBlockEvent event){
        if(event.getEntityType()== EntityType.FALLING_BLOCK && event.getTo()==Material.AIR){
            if(event.getBlock().getType()==lime.getType()
                    ||event.getBlock().getType()==lime.getType()
                    ||event.getBlock().getType()==lime.getType()){
                event.setCancelled(true);
                event.getBlock().getState().update(false, false);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {

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

    @EventHandler
    public void antiDualWield(PlayerSwapHandItemsEvent event){

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("select * from players order by money DESC");

        boolean isNew = true;

        while(result.next()){

            if(result.getString(1).equals(event.getPlayer().getUniqueId().toString())){

                isNew = false;

            }

        }

        if(isNew){


            statement.executeUpdate("insert into players VALUES('"
                    + event.getPlayer().getUniqueId()
                    + "', '"
                    + event.getPlayer().getPlayerListName()
                    + "', '0', '200', '0')"
                    + "en");




            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bSomeone new has appeared! Their name is &f&l"
                    + event.getPlayer().getPlayerListName()));


        }




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

        event.getPlayer().setScoreboard(lobbyboard);

        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setItem(0, guide);
        event.getPlayer().getInventory().setItem(4, compass);
        event.getPlayer().teleport(spawn);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);

    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event){

        for(ArenaBlueprint arenaBlueprint : arenaList){

            if(arenaBlueprint.active.contains(event.getPlayer())){

                for(Player player : arenaBlueprint.active){

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

                    arenaBlueprint.que.get(0).teleport(spawn);
                    arenaBlueprint.que.get(1).teleport(spawn);
                    arenaBlueprint.resetQue.remove(player);
                    arenaBlueprint.points2 = 0;
                    arenaBlueprint.points1 = 0;

                }

                arenaBlueprint.active.clear();
                arenaBlueprint.que.clear();

            }

            else if(arenaBlueprint.que.contains(event.getPlayer())){

                arenaBlueprint.que.clear();
                arenaBlueprint.active.clear();

                if(arenaBlueprint.beginDecay != null){
                    arenaBlueprint.beginDecay.cancel();
                }
                if(arenaBlueprint.beginMinispleef != null){
                    arenaBlueprint.beginMinispleef.cancel();
                }
                renewArena(arenaBlueprint);

            }

        }

        if(name.active.contains(event.getPlayer()) || name.que.contains(event.getPlayer())){

            name.active.remove(event.getPlayer());
            name.que.remove(event.getPlayer());
            killFfaPlayer(name, event.getPlayer(), plugin);

        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

            for(ArenaBlueprint arenas : arenaList){

                if(arenas.active.contains(((Player) event.getEntity()).getPlayer())){

                    event.setCancelled(true);

                }

            }

        }

        if(event.getEntity() instanceof Player){

            if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){

                event.setCancelled(true);

            }

            if((EntityDamageEvent.DamageCause.VOID.equals(event.getCause()))){

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

                Objects.requireNonNull(((Player) event.getEntity()).getPlayer()).setScoreboard(lobbyboard);
                ((Player) event.getEntity()).getPlayer().getInventory().setItem(0, guide);
                ((Player) event.getEntity()).getPlayer().getInventory().setItem(4, compass);
                ((Player) event.getEntity()).getPlayer().teleport(spawn);

            }
        }

    }

    @EventHandler
    void onPlayerMesage(AsyncPlayerChatEvent event){

        StupidlyLargeMethods collection = new StupidlyLargeMethods(plugin);
        try {
            collection.chatLevelDisplay(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
