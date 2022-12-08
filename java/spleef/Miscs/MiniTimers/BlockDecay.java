package spleef.Miscs.MiniTimers;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import spleef.Blueprints.ArenaBlueprint;
import spleef.spluff;

import java.util.Random;

public class BlockDecay extends BukkitRunnable {

    ArenaBlueprint arena;
    spluff plugin;
    BukkitTask action;
    public BlockDecay(spluff plugin, ArenaBlueprint arena){

        this.plugin = plugin;
        this.arena = arena;

    }

    @Override
    public void run() {

        if(arena.beginDecay.isCancelled()){
            this.cancel();
        }

        Random rand = new Random();
        Block randomElement = arena.blocks.get(rand.nextInt(arena.blocks.size()));
        arena.blocks.remove(randomElement);
        action = new Lime(plugin, randomElement).runTaskLater(plugin, 0);

    }
}
