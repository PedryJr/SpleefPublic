package spleef;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import spleef.spleef.Blueprints.ArenaBlueprint;
import spleef.spleef.Blueprints.FfaBlueprint;
import spleef.spleef.Events.Events;
import spleef.spleef.Events.FFAevents;
import spleef.spleef.ManualUpdate.ManualUpdate;
import spleef.spleef.Miscs.BeginDecay;
import spleef.spleef.Miscs.BeginMinispleef;
import spleef.spleef.Miscs.Countdown;
import spleef.spleef.commands.challangeCommand;
import spleef.spleef.commands.moneyCommand;
import spleef.spleef.commands.playtoCommand;
import spleef.spleef.commands.winsCommand;
import spleef.spleef.LargeMethods.StupidlyLargeMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Objects;

import static spleef.spleef.LargeMethods.StupidlyLargeMethods.renewArena;

public final class Spleef extends JavaPlugin {

    public static String username;
    public static String password;
    public static String url;

    public static ArrayList<ArenaBlueprint> arenaList;

    public static ArenaBlueprint Bliz;
    public static ArenaBlueprint Coliseum;
    public static ArenaBlueprint Void;
    public static ArenaBlueprint Nations;
    public static ArenaBlueprint Spooky;
    public static ArenaBlueprint Disco;
    public static ArenaBlueprint Mirage;
    public static ArenaBlueprint Forest;
    public static ArenaBlueprint Starwars;
    public static ArenaBlueprint Reef;
    public static ArenaBlueprint Ancient;
    public static ArenaBlueprint Timberland;

    public static FfaBlueprint name;

    public static Inventory menu;
    public static Inventory gameMenu;

    public static ItemStack play;
    public static ItemStack padding;
    public static ItemStack shovel;
    public static ItemStack compass;
    public static ItemStack leave;
    public static ItemStack exitQue;
    public static ItemStack guide;
    public static Location spawn;
    public static Location c1;
    public static TextHologramLine head;
    public static TextHologramLine line;
    public static BukkitTask countdown;
    public static BukkitTask manualUpdate;
    public static Chest chest;
    private static Spleef plugin;
    private boolean useHolographicDisplays;
    HolographicDisplaysAPI api;
    public static Hologram lb;

    @Override
    public void onEnable() {


        plugin = this;

        defineShit();

        getServer().getPluginManager().registerEvents(new Events(this), this);
        getServer().getPluginManager().registerEvents(new FFAevents(name, this), this);

        Objects.requireNonNull(getCommand("wins")).setExecutor(new winsCommand(plugin));
        Objects.requireNonNull(getCommand("money")).setExecutor(new moneyCommand(plugin));
        Objects.requireNonNull(getCommand("playto")).setExecutor(new playtoCommand());
        Objects.requireNonNull(getCommand("challange")).setExecutor(new challangeCommand());



    }

    @Override
    public void onDisable() {

        /*
        Sql database is updated live for now, so nonthing is needed in "onDisable()"
         */

    }

    /** Define shit
     *
     */
    public void defineShit(){


        username = "Pedry";
        password = "SuperKiwi123";
        url = "jdbc:mysql://localhost:3306/minecraftdatabase";
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

        defineArenas();
        defineItems();
        defineMenus();
        defineHolograms();

        manualUpdate = new ManualUpdate().runTaskTimer(plugin, 0, 200);

    }

    /** Shit to define
     *
     */
    public void defineArenas(){

        arenaList = new ArrayList<>();

        /*
        Bliz = new ArenaBlueprint(1902, 95, 1836, 22, 29, "Bliz", new ItemStack(Material.SNOWBALL));
        arenaList.add(Bliz);

        Mirage = new ArenaBlueprint(6935, 156, 7087, 22, 29, "Mirage", new ItemStack(Material.BRICK));
        arenaList.add(Mirage);

        name = new FfaBlueprint(-4005, 40, -2381, 65, 65, "name", new ItemStack(Material.PUMPKIN_SEEDS));
         */

        Coliseum = new ArenaBlueprint(0, 29, 3000, 22, 29, "Coliseum", new ItemStack(Material.CLAY_BALL));
        arenaList.add(Coliseum);
        Void = new ArenaBlueprint(1000, 29, 3000, 22, 33, "Void", new ItemStack(Material.BEDROCK));
        arenaList.add(Void);
        Nations = new ArenaBlueprint(2000, 29, 0, 22, 33, "Nations", new ItemStack(Material.WHITE_BANNER));
        arenaList.add(Nations);
        Spooky = new ArenaBlueprint(0, 29, 2000, 22, 33, "Spooky", new ItemStack(Material.PUMPKIN));
        arenaList.add(Spooky);
        Disco = new ArenaBlueprint(0, 29, 1000, 22, 29, "Disco", new ItemStack(Material.MUSIC_DISC_MELLOHI));
        arenaList.add(Disco);
        Forest = new ArenaBlueprint(1000, 29, 1000, 22, 29, "Forest", new ItemStack(Material.OAK_SAPLING));
        arenaList.add(Forest);
        Starwars = new ArenaBlueprint(2000, 29, 2000, 22, 29, "Starwars", new ItemStack(Material.SEA_LANTERN));
        arenaList.add(Starwars);
        Reef = new ArenaBlueprint(1000, 29, 0, 22, 29, "Reef", new ItemStack(Material.TROPICAL_FISH));
        arenaList.add(Reef);
        Ancient = new ArenaBlueprint(1000, 29, 2000, 22, 29, "Ancient", new ItemStack(Material.BONE));
        arenaList.add(Ancient);
        Timberland = new ArenaBlueprint(2000, 29, 1000, 22, 29, "Timberland", new ItemStack(Material.FERN));
        arenaList.add(Timberland);


    }

    public void defineMenus(){

        /* Define Main Menu

         */

        menu.setItem (13, play);

        for(int i = 0; i < 27; i++){

            if(i < 9 || i > 17){

                menu.setItem(i, padding);

            }

        }

        /* Define Game Menu

         */

        /*
       gameMenu.setItem(31, name.icon);
       gameMenu.setItem(13, Mirage.icon);
       gameMenu.setItem(10, Bliz.icon);
         */

        gameMenu.setItem(10, Coliseum.icon);
        gameMenu.setItem(19, Nations.icon);
        gameMenu.setItem(11, Void.icon);
        gameMenu.setItem(20, Spooky.icon);
        gameMenu.setItem(12, Disco.icon);
        gameMenu.setItem(21, Forest.icon);
        gameMenu.setItem(13, Starwars.icon);
        gameMenu.setItem(22, Reef.icon);
        gameMenu.setItem(14, Ancient.icon);
        gameMenu.setItem(23, Timberland.icon);

    }

    /** Define all different items and their Meta Values
     *
     */
    public void defineItems(){

        menu = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "     &f&lSnow&b&lCentral &8&l- &c&lMenu!"));
        gameMenu = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', "&f&lSnow&b&lCental &8&l- &c&lSelect a map!"));
        shovel = new ItemStack(Material.DIAMOND_SHOVEL);
        compass = new ItemStack(Material.COMPASS);
        leave = new ItemStack(Material.RED_DYE);
        exitQue = new ItemStack(Material.REDSTONE_TORCH);
        spawn = new Location(Bukkit.getWorld("world"), 0.5, 125, 0.5, 0, 0);
        c1 = new Location(Bukkit.getWorld("world"), 453536, 4, 2454678);

        chest = (Chest) c1.getBlock().getState();
        guide = chest.getInventory().getItem(0);

        play = new ItemStack(Material.SNOW_BLOCK);
        padding = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        ItemMeta shovelMeta = shovel.getItemMeta();
        assert shovelMeta != null;
        shovelMeta.addEnchant(Enchantment.DIG_SPEED, 5, false);
        shovelMeta.setUnbreakable(true);
        shovelMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lShovel"));
        shovelMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        shovelMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        shovelMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        shovelMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        shovel.setItemMeta(shovelMeta);

        ItemMeta menuMeta = compass.getItemMeta();
        assert menuMeta != null;
        menuMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lMenu"));
        compass.setItemMeta(menuMeta);

        /*
        ItemMeta blizMeta = Bliz.icon.getItemMeta();
        assert blizMeta != null;
        blizMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lBliz"));
        Bliz.icon.setItemMeta(blizMeta);

        ItemMeta mirageMeta = Mirage.icon.getItemMeta();
        assert mirageMeta != null;
        mirageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lMirage"));
        Mirage.icon.setItemMeta(mirageMeta);

        ItemMeta ffaMeta = name.icon.getItemMeta();
        assert ffaMeta != null;
        ffaMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lFFA"));
        name.icon.setItemMeta(ffaMeta);
         */

        ItemMeta coliseumMeta = Coliseum.icon.getItemMeta();
        assert coliseumMeta != null;
        coliseumMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lColiseum"));
        Coliseum.icon.setItemMeta(coliseumMeta);

        ItemMeta playMeta = play.getItemMeta();
        assert playMeta != null;
        playMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8- &fSpleef &8-"));
        play.setItemMeta(playMeta);

        ItemMeta paddingMeta = padding.getItemMeta();
        assert paddingMeta != null;
        paddingMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l-"));
        padding.setItemMeta(paddingMeta);

        ItemMeta leaveMeta = leave.getItemMeta();
        assert leaveMeta != null;
        leaveMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cLeave"));
        leave.setItemMeta(leaveMeta);

        ItemMeta nationsMeta = Nations.icon.getItemMeta();
        assert nationsMeta != null;
        nationsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&lNa&2&lit&3&lio&4&lns"));
        Nations.icon.setItemMeta(nationsMeta);

        ItemMeta spookyMeta = Spooky.icon.getItemMeta();
        assert spookyMeta != null;
        spookyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSpooky"));
        Spooky.icon.setItemMeta(spookyMeta);

        ItemMeta voidMeta = Void.icon.getItemMeta();
        assert voidMeta != null;
        voidMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lVoid"));
        Void.icon.setItemMeta(voidMeta);

        ItemMeta discoMeta = Disco.icon.getItemMeta();
        assert discoMeta != null;
        discoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lDisco"));
        Disco.icon.setItemMeta(discoMeta);

        ItemMeta exitQueItemMeta = exitQue.getItemMeta();
        assert exitQueItemMeta != null;
        exitQueItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cLeave queue"));
        exitQue.setItemMeta(exitQueItemMeta);

        ItemMeta forestMeta = Forest.icon.getItemMeta();
        assert forestMeta != null;
        forestMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lForest"));
        Forest.icon.setItemMeta(forestMeta);

        ItemMeta starwarsMeta = Starwars.icon.getItemMeta();
        assert starwarsMeta != null;
        starwarsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lStarwars"));
        Starwars.icon.setItemMeta(starwarsMeta);

        ItemMeta reefMeta = Reef.icon.getItemMeta();
        assert reefMeta != null;
        reefMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lReef"));
        Reef.icon.setItemMeta(reefMeta);

        ItemMeta ancientMeta = Ancient.icon.getItemMeta();
        assert ancientMeta != null;
        ancientMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lAncient"));
        Ancient.icon.setItemMeta(ancientMeta);

        ItemMeta timberlandMeta = Timberland.icon.getItemMeta();
        assert timberlandMeta != null;
        timberlandMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lTimberland"));
        Timberland.icon.setItemMeta(timberlandMeta);

    }

    public void defineHolograms(){

        if(useHolographicDisplays){

            try{

                Class.forName("com.mysql.cj.jdbc.Driver");

            }catch (ClassNotFoundException e){

                System.out.println(e.getMessage());

            }

            try{

                Connection connection = DriverManager.getConnection(url, username, password);

                Statement statement = connection.createStatement();

                ResultSet result = statement.executeQuery("select * from players order by money DESC");

                api = HolographicDisplaysAPI.get(plugin);

                lb = api.createHologram(new Location(Bukkit.getWorld("world"), 10.5, 128.3, 0.5));

                head = lb.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&f&lLeadeboard!"));

                int i = 1;

                while(result.next()){

                    line = lb.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&b" + i + "&c. &f&l" + result.getString(2) + " &c| &f&l" + result.getString(4) + "&c$"));

                    i++;

                    if(i >= 10){

                        break;

                    }

                }

            }catch (SQLException e) {

                throw new RuntimeException(e);

            }

        }

    }

    public static void regenerateArena(ArenaBlueprint arena){

        int z = 0;
        int x = 0;

        arena.blocks.clear();
        while(arena.sizex >= x){

            while(arena.sizez >= z){

                Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(x + arena.locationx, arena.locationy, z + arena.locationz).setType(Material.SNOW_BLOCK);
                arena.blocks.add(Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(x + arena.locationx, arena.locationy, z + arena.locationz));
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

    public static void respawnArena(ArenaBlueprint arena){

        arena.que.get(0).teleport(arena.s1);
        arena.que.get(0).closeInventory();
        arena.que.get(0).getInventory();
        arena.que.get(0).getInventory().setItem(0, shovel);
        arena.que.get(0).setGameMode(GameMode.SURVIVAL);

        arena.que.get(1).teleport(arena.s2);
        arena.que.get(1).closeInventory();
        arena.que.get(1).getInventory().clear();
        arena.que.get(1).getInventory().setItem(0, shovel);
        arena.que.get(1).setGameMode(GameMode.SURVIVAL);

        regenerateArena(arena);

    }

    public static void matchPoint(ArenaBlueprint arena, Player victor, Player loser, int winnerScore, int loserScore) throws SQLException {

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

        respawnArena(arena);

        if(winnerScore == arena.winPoints){

            if(arena.beginDecay != null){
                arena.beginDecay.cancel();
            }
            if(arena.beginMinispleef != null){
                arena.beginMinispleef.cancel();
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
    public static void startMatch(ArenaBlueprint arena){

        arena.beginDecay = new BeginDecay(plugin, arena).runTaskTimer(plugin, 0, 20);
        arena.beginMinispleef = new BeginMinispleef(plugin, arena).runTaskTimer(plugin, 0, 20);

        arena.que.get(0).teleport(arena.s1);
        arena.que.get(0).closeInventory();
        arena.que.get(0).getInventory().clear();
        arena.que.get(0).getInventory().setItem(0, shovel);
        arena.que.get(0).setGameMode(GameMode.SURVIVAL);
        arena.que.get(0).addPotionEffect( new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5));
        arena.active.add(arena.que.get(0));

        arena.que.get(1).teleport(arena.s2);
        arena.que.get(1).closeInventory();
        arena.que.get(1).getInventory().clear();
        arena.que.get(1).getInventory().setItem(0, shovel);
        arena.que.get(1).setGameMode(GameMode.SURVIVAL);
        arena.que.get(1).addPotionEffect( new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5));
        arena.active.add(arena.que.get(1));

        respawnArena(arena);

    }

    public static void resetArena(ArenaBlueprint arena, Player resetter){

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

    public static void startFfa(FfaBlueprint arena, Spleef plugin){

        regenerateArena(arena);
        arena.allowBreak = false;

        for(Player player : arena.que) {

            player.teleport(arena.s);
            player.getInventory().clear();
            player.getInventory().setItem(0, shovel);
            player.getInventory().setItem(8, leave);
            player.setGameMode(GameMode.SURVIVAL);
            player.setInvisible(false);
            arena.active.add(player);

        }

        countdown = new Countdown(arena.active, arena).runTaskTimer(plugin, 0, 20);

    }

    public static void killFfaPlayer(FfaBlueprint arena, Player player, Spleef plugin){

        if(arena.que.size() > 2){

            player.teleport(arena.s);
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.getInventory().clear();
            player.getInventory().setItem(0, guide);
            player.getInventory().setItem(8, leave);
            player.setInvisible(true);

            arena.active.remove(player);
            arena.spectator.add(player);

            if(arena.active.size() == 1){

                for(Player que : arena.que){

                    que.sendRawMessage( ChatColor.translateAlternateColorCodes('&', "&6&l" + arena.active.get(0).getPlayerListName() + " &bhas won &fspleef!"));
                }

                arena.spectator.clear();
                arena.active.clear();
                startFfa(arena, plugin);
            }

        }else{

            player.teleport(arena.s);
            arena.allowBreak = true;
        }

    }

    public static void startZenMode(FfaBlueprint arena, Player player){

        player.teleport(arena.s);
        player.getInventory().clear();
        player.getInventory().setItem(0, shovel);
        player.getInventory().setItem(8, leave);
        player.setGameMode(GameMode.SURVIVAL);
        player.setInvisible(false);

    }

}