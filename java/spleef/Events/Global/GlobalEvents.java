package spleef.Events.Global;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;
import spleef.Events.Parkour.ParkourEvents;
import spleef.Events.Spleef.SpleefEvents;
import spleef.ManagerMethods.StupidlyLargeMethods;
import spleef.spluff;

import java.sql.*;
import java.util.Objects;

import static spleef.spluff.*;
import static spleef.spluff.spawn;

public class GlobalEvents {

    spluff plugin;

    public GlobalEvents(spluff plugin){

        this.plugin = plugin;

    }

    /** Connect this class with game methods **/

    public void InitializePlayer(PlayerJoinEvent event) throws SQLException {

        Player player = event.getPlayer();

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("select * from players order by money DESC");

        boolean isNew = true;

        while(result.next()){

            if(result.getString("UUID").equals(player.getUniqueId().toString())){

                isNew = false;

            }

        }

        if(isNew){

            int i = statement.executeUpdate("insert into players VALUES('"
                    + player.getUniqueId()
                    + "', '"
                    + player.getPlayerListName()
                    + "', '0', '200', '0'"
                    + ", 'en')");


            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bSomeone new has appeared! Their name is &f&l"
                    + player.getPlayerListName()));


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

        player.setScoreboard(lobbyboard);

        player.getInventory().clear();
        player.getInventory().setItem(0, guide);
        player.getInventory().setItem(4, compass);
        player.teleport(spawn);
        player.setGameMode(GameMode.ADVENTURE);

    }

    public void Chat(AsyncPlayerChatEvent event){

        StupidlyLargeMethods collection = new StupidlyLargeMethods(plugin);

        try {

            collection.chatLevelDisplay(event);

        } catch (Exception e) {

            System.out.println(e.getMessage());

            throw new RuntimeException(e);
        }

    }


    /** Methods for handling damage **/
    public void PreventPVP(EntityDamageEvent event){

        Player player;

        if(event.getEntity() instanceof Player){

            player = (Player) event.getEntity();

            double x = player.getLocation().getX();
            double z = player.getLocation().getZ();

            if(x > -500 && x < 500 && z > -500 && z < 500){

                event.setCancelled(true);

            }

        }

    }

    public void preventFallDamage(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

            if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){

                event.setCancelled(true);

            }

        }

    }

    public void PreventVoidDeath(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){

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

    public void Cancel(InventoryClickEvent event){

        if(Objects.equals(event.getCurrentItem(), padding)){

            event.setCancelled(true);

        }

        if(Objects.equals(event.getCurrentItem(), exitQue)){

            event.setCancelled(true);

        }

    }

}
