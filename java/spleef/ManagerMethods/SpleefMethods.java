package spleef.ManagerMethods;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.ChunkSection;
import net.minecraft.server.v1_12_R1.IBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import spleef.Blueprints.FfaBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Miscs.BeginDecay;
import spleef.Miscs.BeginMinispleef;
import spleef.spluff;

import java.sql.*;
import java.util.Objects;

import static spleef.ManagerMethods.StupidlyLargeMethods.renewArena;
import static spleef.spluff.*;

public class SpleefMethods {

    spluff plugin;
    public SpleefMethods(spluff plugin){

        this.plugin = plugin;

    }

    public static void setBlockInNativeWorld(World world, int x, int y, int z, int blockId, byte data, boolean applyPhysics) {

        net.minecraft.server.v1_12_R1.World nmsWorld = ((CraftWorld) world).getHandle();
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = net.minecraft.server.v1_12_R1.Block.getByCombinedId(blockId + (data << 12));
        nmsWorld.setTypeAndData(bp, ibd, applyPhysics ? 3 : 2);

    }

    public static void fastBlockPlacer(World world, int x, int y, int z, int blockId, byte data, boolean flag) {
        net.minecraft.server.v1_12_R1.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_12_R1.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
        IBlockData ibd = net.minecraft.server.v1_12_R1.Block.getByCombinedId(blockId + (data << 12));

        ChunkSection cs = nmsChunk.getSections()[y >> 4];
        if (cs == net.minecraft.server.v1_12_R1.Chunk.a) {
            cs = new ChunkSection(y >> 4 << 4, flag);
            nmsChunk.getSections()[y >> 4] = cs;
        }
        cs.setType(x & 15, y & 15, z & 15, ibd);
    }

    public static void regenerateArena(SpleefBlueprint arena){

        Block block = Bukkit.getWorld("world").getBlockAt(0, 256, 0);
        block.setType(Material.SNOW_BLOCK);

        byte data = block.getData();

        int z = 0;
        int x = 0;

        arena.blocks.clear();

        while(arena.sizex >= x){

            while(arena.sizez >= z){

                block = Bukkit.getWorld("world").getBlockAt(arena.locationx + x, arena.locationy, arena.locationz + z);

                if(block.getType().equals(Material.AIR)){

                    setBlockInNativeWorld(Bukkit.getWorld("world"), arena.locationx + x, arena.locationy, arena.locationz + z, 80, data, false);

                }
                arena.blocks.add(block);
                z++;

            }

            z = 0;
            x++;

        }

    }

    public static void regenerateArena(FfaBlueprint arena){

        int z = 0;
        int x = 0;

        while(arena.sizex >= x){

            while(arena.sizez >= z){

                Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(x + arena.locationx, arena.locationy, z + arena.locationz).setType(Material.SNOW_BLOCK);
                z++;

            }

            z = 0;
            x++;

        }

    }

    public static void respawnArena(SpleefBlueprint arena){

        regenerateArena(arena);

        arena.que.get(0).closeInventory();
        arena.que.get(0).getInventory().clear();
        arena.que.get(0).getInventory().setItem(0, shovel);
        arena.que.get(0).setGameMode(GameMode.SURVIVAL);
        arena.que.get(0).teleport(arena.s1);

        arena.que.get(1).closeInventory();
        arena.que.get(1).getInventory().clear();
        arena.que.get(1).getInventory().setItem(0, shovel);
        arena.que.get(1).setGameMode(GameMode.SURVIVAL);
        arena.que.get(1).teleport(arena.s2);


    }

    public static void matchPoint(SpleefBlueprint arena, Player victor, Player loser, int winnerScore, int loserScore) throws SQLException {

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

        arena.que.get(0).setScoreboard(spleefBoard);
        arena.que.get(1).setScoreboard(spleefBoard);

        if(!arena.spectators.isEmpty()){

            for(Player player : arena.spectators){

                player.setScoreboard(spleefBoard);

            }

        }


        respawnArena(arena);

        if(winnerScore == arena.winPoints){

            if(arena.beginDecay != null){
                arena.beginDecay.cancel();
                if(arena.ms.beginDecay != null){
                    arena.ms.beginDecay.cancel();
                }
            }
            if(arena.beginMinispleef != null){
                arena.beginMinispleef.cancel();
                if(arena.ms.beginMinispleef != null){
                    arena.ms.beginMinispleef.cancel();
                }
            }

            renewArena(arena);

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

            victor.setScoreboard(lobbyboard);
            loser.setScoreboard(lobbyboard);

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', victor.getPlayerListName() + " &ahas beaten " + loser.getPlayerListName() + " &awith a score of &f" + winnerScore + "&f/" + loserScore) + " !");

            victor.teleport(spawn);
            victor.getInventory().clear();
            victor.getInventory().setItem(0, guide);
            victor.getInventory().setItem(4, compass);
            victor.removePotionEffect(PotionEffectType.NIGHT_VISION);
            victor.setGameMode(GameMode.ADVENTURE);

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet getWinner = statement.executeQuery("select * from players");

            while(getWinner.next()){

                if(getWinner.getString("UUID").equals(victor.getUniqueId().toString())){

                    int wins = getWinner.getInt("wins");

                    wins++;

                    statement.executeUpdate("update players set wins = '" + wins + "' where UUID = '" + victor.getUniqueId() + "'");

                    break;

                }

            }

            if(!arena.spectators.isEmpty()){

                for(Player player : arena.spectators){

                    player.teleport(spawn);
                    player.setScoreboard(lobbyboard);

                }

            }

            loser.teleport(spawn);
            loser.getInventory().clear();
            loser.getInventory().setItem(0, guide);
            loser.getInventory().setItem(4, compass);
            loser.removePotionEffect(PotionEffectType.NIGHT_VISION);
            loser.setGameMode(GameMode.ADVENTURE);

            int money = 0;

            ResultSet lresult = statement.executeQuery("select * from players order by money DESC");

            while(lresult.next()){

                if(lresult.getString("UUID").equals(loser.getUniqueId().toString())){

                    money = Integer.parseInt(lresult.getString("money"));

                    if(money >= 4){

                        money--;
                        money--;
                        money--;
                        money--;

                        loser.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou lost &f&l4&c$ &bfor losing&f&l!"));

                        statement.executeUpdate("update players set money = '" + money + "' where UUID = '" + loser.getUniqueId() + "'");

                    }else{

                        victor.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYour oponent does not have enough money&f&l!"));
                        loser.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou dont have enough money left, your oponent did not get a reward&f&l!"));

                    }
                    break;

                }

            }

            ResultSet wresult = statement.executeQuery("select * from players order by money DESC");

            while(wresult.next()){

                if(wresult.getString("UUID").equals(victor.getUniqueId().toString())){

                    if(money >= 4){

                        money = Integer.parseInt(wresult.getString("money"));

                        money++;
                        money++;
                        money++;
                        money++;
                        money++;

                        victor.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&bYou won &f&l5&c$ &bfor winning&f&l!"));

                        statement.executeUpdate("update players set money = '" + money + "' where UUID = '" + victor.getUniqueId() + "'");

                    }
                    break;

                }

            }

        }

    }
    public void startMatch(SpleefBlueprint arena){

        arena.beginDecay = new BeginDecay(plugin, arena).runTaskTimer(plugin, 0, 20);
        arena.beginMinispleef = new BeginMinispleef(plugin, arena).runTaskTimer(plugin, 0, 20);


        arena.que.get(0).addPotionEffect( new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5));
        arena.active.add(arena.que.get(0));

        arena.que.get(1).addPotionEffect( new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5));
        arena.active.add(arena.que.get(1));

        respawnArena(arena);

    }

    public static void resetArena(SpleefBlueprint arena, Player resetter){

        if(arena.resetQue.contains(resetter)){

            arena.resetQue.remove(resetter);

            resetter.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&9You cancelled your reset of the field!"));

        }else{

            arena.resetQue.add(resetter);

            resetter.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&9You sent a reset request to your opponent!"));

            if(arena.resetQue.size() == 2) {

                if(arena.minispleef){

                    respawnArena(arena.ms);

                }else{

                    respawnArena(arena);

                }

                arena.resetQue.clear();

            }else{

                for(Player player : arena.active){

                    if(!arena.resetQue.contains(player)){

                        player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&9Your oponent wants to reset the field!"));

                    }

                }

            }

        }

    }

}
