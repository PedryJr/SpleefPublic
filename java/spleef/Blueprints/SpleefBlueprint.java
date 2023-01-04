package spleef.Blueprints;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;

public class SpleefBlueprint {

    public int locationx;
    public int locationy;
    public int locationz;
    public int sizex;
    public int sizez;
    public int points1;
    public int points2;
    public int winPoints;
    public int winPointsreq;
    public boolean minispleef;
    public BukkitTask beginDecay;
    public BukkitTask beginMinispleef;
    public Location loc;
    public Location s1;
    public Location s2;
    public String name;
    public ItemStack icon;
    public ArrayList<Player> que = new ArrayList<>();
    public ArrayList<Player> active = new ArrayList<>();
    public ArrayList<Player> spectators = new ArrayList<>();
    public ArrayList<Player> resetQue = new ArrayList<>();
    public ArrayList<Player> playtoQue = new ArrayList<>();
    public ArrayList<Block> blocks = new ArrayList<>();
    public ScoreboardManager spleefBoardManager = Bukkit.getScoreboardManager();
    public Scoreboard spleefBoard;
    public String decayState;
    public SpleefBlueprint ms;

    {
        assert spleefBoardManager != null;
        spleefBoard = spleefBoardManager.getNewScoreboard();
    }

    public Objective spleefMatch = spleefBoard.registerNewObjective(ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

    public SpleefBlueprint(int locationx, int locationy, int locationz, int sizex, int sizez, String name, ItemStack icon){

        this.winPoints = 7;

        this.locationx = locationx;
        this.locationy = locationy;
        this.locationz = locationz;

        loc = new Location(Bukkit.getWorld("world"), locationx, locationy, locationz);
        s1 = new Location(Bukkit.getWorld("world"), locationx + 11.5, locationy + 1, locationz + 1);
        s2 = new Location(Bukkit.getWorld("world"), locationx + 11.5, locationy + 1, locationz + 29);
        s2.setYaw(180);

        this.sizex = sizex;
        this.sizez = sizez;

        this.name = name;
        this.icon = icon;

        points1 = 0;
        points2 = 0;

        spleefMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
        spleefMatch.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        decayState = "Off";

    }

}