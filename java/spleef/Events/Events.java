package spleef.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import spleef.Events.Global.GlobalEvents;
import spleef.Events.Parkour.ParkourEvents;
import spleef.Events.Spleef.SpleefEvents;
import spleef.spluff;

import java.sql.*;

public class Events implements Listener {



    spluff plugin;
    SpleefEvents spleefEvents;
    GlobalEvents globalEvents;
    ParkourEvents parkourEvents;

    public Events(spluff plugin){

        this.plugin = plugin;

        globalEvents = new GlobalEvents(plugin);

        spleefEvents = new SpleefEvents(plugin);
        parkourEvents = new ParkourEvents(plugin);

    }

    @EventHandler
    public void onItemHotbarClick(PlayerInteractEvent event){

        spleefEvents.HotbarInteraction(event);

        parkourEvents.LeaveQue(event);

    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){

        spleefEvents.Cancel(event);

        spleefEvents.InitiateResetRequest(event);

    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event){

        spleefEvents.CancellChallange(event);

    }

    @EventHandler
    public void menuInteraction(InventoryClickEvent event){

        spleefEvents.Cancel(event);
        spleefEvents.ChallangeResult(event);
        spleefEvents.InitiateGame(event);

        parkourEvents.Cancel(event);
        /** Add command to challange later **/
        parkourEvents.InitiateGame(event);

        globalEvents.Cancel(event);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) throws SQLException {

        spleefEvents.KillPlayer(event);
        parkourEvents.finish(event);

    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    private void onSandFall(EntityChangeBlockEvent event){

        spleefEvents.FixDecay(event);

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {

        spleefEvents.AenablockBreak(event);

    }

    @EventHandler
    public void antiDualWield(PlayerSwapHandItemsEvent event){

        spleefEvents.Cancel(event);

    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {

        globalEvents.InitializePlayer(event);

    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event){

        spleefEvents.SuspendFromGame(event);
        parkourEvents.SuspendFromGame(event);

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){

        globalEvents.PreventPVP(event);
        globalEvents.preventFallDamage(event);
        globalEvents.PreventVoidDeath(event);

        spleefEvents.PreventInGamePVP(event);
        parkourEvents.PreventInGamePVP(event);

    }

    @EventHandler
    void onPlayerMesage(AsyncPlayerChatEvent event){

        globalEvents.Chat(event);

    }

}
