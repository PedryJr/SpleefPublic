package spleef.Miscs.MiniTimers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static spleef.commands.challangeCommand.challange;

public class InventoryDeny extends BukkitRunnable {

    Player player;

    public InventoryDeny(Player player){

        this.player = player;

    }

    @Override
    public void run() {

        player.openInventory(challange);

    }
}
