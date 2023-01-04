package spleef.ManagerMethods;

import org.bukkit.GameMode;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.spluff;

import static spleef.spluff.parkourArenaList;
import static spleef.spluff.shovel;

public class ParkourMethods {

    spluff plugin;

    public ParkourMethods(spluff plugin) {

        this.plugin = plugin;

    }

    public void RenewArena(ParkourBlueprint parkourBlueprint){

        StupidlyLargeMethods.renewArena(parkourBlueprint);


    }

}
