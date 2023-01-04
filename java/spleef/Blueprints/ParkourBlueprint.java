package spleef.Blueprints;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;

public class ParkourBlueprint {

    public int locationx;
    public int locationy;
    public int locationz;
    public Location loc;
    public Location finish;
    public String name;
    public ItemStack icon;
    public ArrayList<Player> que = new ArrayList<>();
    public ArrayList<Player> active = new ArrayList<>();
    public ArrayList<Player> spectators = new ArrayList<>();
    public ScoreboardManager parkourBoardManager = Bukkit.getScoreboardManager();
    public Scoreboard parkouorBoard;
    public String decayState;
    public SpleefBlueprint ms;

    {
        assert parkourBoardManager != null;
        parkouorBoard = parkourBoardManager.getNewScoreboard();
    }

    public Objective parkouorMatch = parkouorBoard.registerNewObjective(ChatColor.translateAlternateColorCodes('&', "SnowCentral"), "dummy");

    public ParkourBlueprint(int locationx, int locationy, int locationz,int finishx, int finishy, int finishz, String name, ItemStack icon){

        this.locationx = locationx;
        this.locationy = locationy;
        this.locationz = locationz;

        loc = new Location(Bukkit.getWorld("world"), locationx, locationy, locationz);
        finish = new Location(Bukkit.getWorld("world"), finishx, finishy, finishz);

        this.name = name;
        this.icon = icon;

        parkouorMatch.setDisplaySlot(DisplaySlot.SIDEBAR);
        parkouorMatch.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', "      &f&lSnow&b&lCentral     "));

        decayState = "Off";

    }

}