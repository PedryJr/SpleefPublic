package spleef.Miscs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Blueprints.ArenaBlueprint;
import spleef.spluff;

public class BeginMinispleef extends BukkitRunnable {

    spluff plugin;
    ArenaBlueprint arena;
    int seconds;
    int resetsec;
    Location minispleefSpawn1;
    Location minispleefSpawn2;
    Location minispleefLoc;
    int sizex;
    int sizez;
    boolean frequent = false;

    ArenaBlueprint minispleef;

    public BeginMinispleef(spluff plugin, ArenaBlueprint arena){

        this.plugin = plugin;
        this.arena = arena;
        seconds = 0;
        resetsec = 0;



        minispleefSpawn1 = new Location(Bukkit.getWorld("world"), arena.s1.getX() - 0.5, arena.s1.getY() + 2, arena.s1.getZ() + 10);
        minispleefSpawn2 = new Location(Bukkit.getWorld("world"), arena.s2.getX() - 0.5, arena.s2.getY() + 2, arena.s2.getZ() - 10);
        minispleefSpawn2.setYaw(180);
        minispleefLoc = arena.loc;

        sizex = 7;
        sizez = 9;

        minispleef = new ArenaBlueprint((int) minispleefLoc.getX() + 7,(int) minispleefLoc.getY(),(int) minispleefLoc.getZ() + 10, sizex, sizez, "null", new ItemStack(Material.STONE));

        this.arena.ms = minispleef;

    }

    @Override
    public void run() {

        if(arena.minispleef){

            switch (resetsec){

                case 7:
                    arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&b3.."), 5, 10, 5);
                    arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&b3.."), 5, 10, 5);
                    break;
                case 8:
                    arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&b2.."), 5, 10, 5);
                    arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&b2.."), 5, 10, 5);
                    break;
                case 9:
                    arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&b1.."), 5, 10, 5);
                    arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&b1.."), 5, 10, 5);
                    break;
                case 10:
                    resetsec = 0;
                    seconds = 0;
                    spluff.regenerateArena(minispleef);
            }

        }else{

            switch (seconds){

                case 1497:
                    arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b3.."), 5, 10, 5);
                    arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b3.."), 5, 10, 5);
                    break;
                case 1498:
                    arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b2.."), 5, 10, 5);
                    arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b2.."), 5, 10, 5);
                    break;
                case 1499:
                    arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b1.."), 5, 10, 5);
                    arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b1.."), 5, 10, 5);
                    break;
                case 1500:
                    resetsec = 0;
                    seconds = 0;
                    arena.minispleef = true;
                    frequent = true;

                    arena.active.get(1).teleport(minispleefSpawn2);
                    arena.active.get(0).teleport(minispleefSpawn1);

                    for(Block block : arena.blocks){

                        if(!block.getType().equals(Material.AIR)){
                            block.setType(Material.AIR);
                        }

                    }
                    arena.blocks.clear();

                    spluff.regenerateArena(minispleef);
                    arena.blocks = minispleef.blocks;

                    break;

                case 117:
                    if(frequent){
                        arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b3.."), 5, 10, 5);
                        arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b3.."), 5, 10, 5);
                    }
                    break;
                case 118:
                    if(frequent){
                        arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b2.."), 5, 10, 5);
                        arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b2.."), 5, 10, 5);
                    }
                    break;
                case 119:
                    if(frequent){
                        arena.active.get(0).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b1.."), 5, 10, 5);
                        arena.active.get(1).sendTitle(ChatColor.translateAlternateColorCodes('&', "&fMinispleef"), ChatColor.translateAlternateColorCodes('&', "&b1.."), 5, 10, 5);
                    }
                    break;
                case 120:
                    if(frequent){
                        resetsec = 0;
                        seconds = 0;
                        arena.minispleef = true;

                        arena.active.get(1).teleport(minispleefSpawn2);
                        arena.active.get(0).teleport(minispleefSpawn1);

                        for(Block block : arena.blocks){

                            if(!block.getType().equals(Material.AIR)){
                                block.setType(Material.AIR);
                            }

                        }
                        arena.blocks.clear();

                        spluff.regenerateArena(minispleef);
                        arena.blocks = minispleef.blocks;

                    }
                    break;

            }

        }

        if(arena.minispleef){
            resetsec++;
        }else{
            resetsec = 0;
            seconds++;
        }

    }
}
