package spleef.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.SpleefBlueprint;

import java.util.Objects;

import static spleef.spluff.*;
import static spleef.spluff.compass;

public class spectateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(sender instanceof Player){

            Player player = ((Player) sender).getPlayer();
            Player target = Bukkit.getPlayer(args[0]);
            if(args[0] != null){

                target = Bukkit.getPlayer(args[0]);

            }

            for(SpleefBlueprint arena : spleefArenaList){

                if(arena.active.contains(target) && target != null){

                    if(arena.active.contains(player)){

                        player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou can't Spectate yourself!"));
                        return false;

                    }

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are now spectating! Use &f&l/spectate 0 &bto stop spectating!"));

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
                    Score e = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bPlaying to&c: &b" + arena.winPoints));
                    Score f = spleefMatch.getScore(" ");
                    Score g = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &bDecaystate&c: &b" + arena.decayState));
                    Score h = spleefMatch.getScore("");

                    spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
                    spleefMatch.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

                    a.setScore(9);

                    b = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b&lMap&c: ") + Objects.requireNonNull(arena.icon.getItemMeta()).getDisplayName());
                    b.setScore(8);

                    c.setScore(7);

                    player1 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arena.que.get(0).getPlayerListName() + "&c: " + arena.points1));
                    player1.setScore(6);
                    player2 = spleefMatch.getScore(ChatColor.translateAlternateColorCodes('&', "  &b" + arena.que.get(1).getPlayerListName() + "&c: " + arena.points2));
                    player2.setScore(5);

                    d.setScore(4);
                    e.setScore(3);
                    f.setScore(2);
                    g.setScore(1);
                    h.setScore(0);

                    player.setScoreboard(spleefBoard);

                    player.teleport(target);
                    arena.spectators.add(player);
                    player.setGameMode(GameMode.SPECTATOR);
                    player.setScoreboard(arena.spleefBoard);
                    return true;


                }else if(arena.spectators.contains(player)){

                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are no longer spectating!"));

                    arena.spectators.remove(player);
                    ScoreboardManager lobbyboardmanager = Bukkit.getScoreboardManager();
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
                    return true;


                }

            }

        }


        return false;
    }
}
