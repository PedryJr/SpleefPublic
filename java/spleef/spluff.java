package spleef;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
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
import org.bukkit.scoreboard.Scoreboard;
import spleef.Blueprints.ParkourBlueprint;
import spleef.Blueprints.SpleefBlueprint;
import spleef.Blueprints.FfaBlueprint;
import spleef.Blueprints.QueBlueprint;
import spleef.Events.Events;
import spleef.ManualUpdate.ManualUpdate;
import spleef.Miscs.BeginDecay;
import spleef.Miscs.BeginMinispleef;
import spleef.Miscs.Countdown;
import spleef.commands.*;
import spleef.commands.Spleef.challangeCommand;
import spleef.commands.Spleef.duelCommand;
import spleef.commands.Spleef.playtoCommand;

import java.sql.*;
import java.util.*;
import java.util.function.BiConsumer;

import static spleef.ManagerMethods.StupidlyLargeMethods.renewArena;

public final class spluff extends JavaPlugin {

    public static ItemStack accept = new ItemStack(Material.WOOL);
    public static ItemStack deny = new ItemStack(Material.CONCRETE_POWDER);
    public static Inventory challange = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&', "   &f&lSnow&b&lCentral &8- &cChallange!"));
    public static ArrayList<QueBlueprint> challangeList = new ArrayList<>();
    public static Block red;
    public static Block yellow;
    public static Block lime;

    public static String username;
    public static String password;
    public static String url;

    public static ArrayList<SpleefBlueprint> spleefArenaList;
    public static ArrayList<ParkourBlueprint> parkourArenaList;


    /** Spleef blueprints **/
    public static SpleefBlueprint Bliz;
    public static SpleefBlueprint Coliseum;
    public static SpleefBlueprint Void;
    public static SpleefBlueprint Nations;
    public static SpleefBlueprint Spooky;
    public static SpleefBlueprint Disco;
    public static SpleefBlueprint Mirage;
    public static SpleefBlueprint Forest;
    public static SpleefBlueprint Starwars;
    public static SpleefBlueprint Reef;
    public static SpleefBlueprint Ancient;
    public static SpleefBlueprint Timberland;
    public static SpleefBlueprint DuneTemple;
    public static SpleefBlueprint Oasis;

    /** Parkour blueprints **/
    public static ParkourBlueprint test;

    public static Inventory menu;
    public static Inventory spleefMenu;
    public static Inventory parkourMenu;

    public static ItemStack playSpleef;
    public static ItemStack playParkour;
    public static ItemStack padding;
    public static ItemStack shovel;
    public static ItemStack compass;
    public static ItemStack exitQue;
    public static ItemStack guide;
    public static Location spawn;
    public static Location limbo;
    public static Location c1;
    public static TextHologramLine head;
    public static TextHologramLine line;
    public static BukkitTask countdown;
    public static BukkitTask manualUpdate;
    private spluff plugin;
    private boolean useHolographicDisplays;
    HolographicDisplaysAPI api;
    public static Hologram lb;

    @Override
    public void onEnable() {

        plugin = this;

        defineShit();

        getServer().getPluginManager().registerEvents(new Events(this), this);

        Objects.requireNonNull(getCommand("wins")).setExecutor(new winsCommand(plugin));
        Objects.requireNonNull(getCommand("money")).setExecutor(new moneyCommand(plugin));
        Objects.requireNonNull(getCommand("playto")).setExecutor(new playtoCommand());
        Objects.requireNonNull(getCommand("challange")).setExecutor(new challangeCommand());
        Objects.requireNonNull(getCommand("duel")).setExecutor(new duelCommand());
        Objects.requireNonNull(getCommand("plugins")).setExecutor(new pluginsCommand());
        Objects.requireNonNull(getCommand("spectate")).setExecutor(new spectateCommand());
        Objects.requireNonNull(getCommand("spec")).setExecutor(new specCommand());

        yellow = Bukkit.getWorld("world").getBlockAt(-1, 142, 1);
        red = Bukkit.getWorld("world").getBlockAt(1, 142, 1);
        lime = Bukkit.getWorld("world").getBlockAt(-1, 142, -1);



    }

    @Override
    public void onDisable() {

        /*
        Sql database is updated live for now, so nonthing is needed in "onDisable()"
         */

    }

    public void defineShit(){


        username = "Pedry";
        password = "SuperKiwi123";
        url = "jdbc:mysql://localhost:3306/minecraftdatabase?useSSL=false";
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

        defineArenas();
        defineItems();
        defineMenus();
        defineHolograms();

        manualUpdate = new ManualUpdate().runTaskTimer(plugin, 0, 200);

    }

    public void defineArenas(){

        spleefArenaList = new ArrayList<>();
        parkourArenaList = new ArrayList<>();

        /*
        Bliz = new ArenaBlueprint(1902, 95, 1836, 22, 29, "Bliz", new ItemStack(Material.SNOWBALL));
        arenaList.add(Bliz);

        Mirage = new ArenaBlueprint(6935, 156, 7087, 22, 29, "Mirage", new ItemStack(Material.BRICK));
        arenaList.add(Mirage);

        name = new FfaBlueprint(-4005, 40, -2381, 65, 65, "name", new ItemStack(Material.PUMPKIN_SEEDS));
         */

        /** Define spleef arenas **/
        Coliseum = new SpleefBlueprint(0, 29, 3000, 22, 29, "Coliseum", new ItemStack(Material.CLAY_BALL));
        spleefArenaList.add(Coliseum);
        Void = new SpleefBlueprint(1000, 29, 3000, 22, 33, "Void", new ItemStack(Material.BEDROCK));
        spleefArenaList.add(Void);
        Nations = new SpleefBlueprint(2000, 29, 0, 22, 33, "Nations", new ItemStack(Material.BANNER));
        spleefArenaList.add(Nations);
        Spooky = new SpleefBlueprint(0, 29, 2000, 22, 33, "Spooky", new ItemStack(Material.PUMPKIN));
        spleefArenaList.add(Spooky);
        Disco = new SpleefBlueprint(0, 29, 1000, 22, 29, "Disco", new ItemStack(Material.RECORD_10));
        spleefArenaList.add(Disco);
        Forest = new SpleefBlueprint(1000, 29, 1000, 22, 29, "Forest", new ItemStack(Material.SAPLING));
        spleefArenaList.add(Forest);
        Starwars = new SpleefBlueprint(2000, 29, 2000, 22, 29, "Starwars", new ItemStack(Material.SEA_LANTERN));
        spleefArenaList.add(Starwars);
        Reef = new SpleefBlueprint(1000, 29, 0, 22, 29, "Reef", new ItemStack(Material.RAW_FISH));
        spleefArenaList.add(Reef);
        Ancient = new SpleefBlueprint(1000, 29, 2000, 22, 29, "Ancient", new ItemStack(Material.BONE));
        spleefArenaList.add(Ancient);
        Timberland = new SpleefBlueprint(2000, 29, 1000, 22, 29, "Timberland", new ItemStack(Material.LOG));
        spleefArenaList.add(Timberland);
        DuneTemple = new SpleefBlueprint(1000, 29, -1000, 22, 29, "Duntemple", new ItemStack(Material.SANDSTONE));
        spleefArenaList.add(DuneTemple);
        Oasis = new SpleefBlueprint(998, 29, -2019, 22, 29, "Oasis", new ItemStack(Material.getMaterial(38)));
        spleefArenaList.add(Oasis);

        /** Define parkour arenas **/
        test = new ParkourBlueprint(5000, 33, 3000, 5008, 63, 2890, "test", new ItemStack(Material.BRICK));
        parkourArenaList.add(test);


    }

    public void defineMenus(){

        /* Define Main Menu

         */

        menu.setItem (12, playSpleef);
        menu.setItem (14, playParkour);

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

        /** Set spleef menu icons **/
        spleefMenu.setItem(10, Coliseum.icon);
        spleefMenu.setItem(19, Nations.icon);
        spleefMenu.setItem(11, Void.icon);
        spleefMenu.setItem(20, Spooky.icon);
        spleefMenu.setItem(12, Disco.icon);
        spleefMenu.setItem(21, Forest.icon);
        spleefMenu.setItem(13, Starwars.icon);
        spleefMenu.setItem(22, Reef.icon);
        spleefMenu.setItem(14, Ancient.icon);
        spleefMenu.setItem(23, Timberland.icon);
        spleefMenu.setItem(15, DuneTemple.icon);
        spleefMenu.setItem(24, Oasis.icon);

        /** Set parkour menu icons **/
        parkourMenu.setItem(10, test.icon);


    }

    public void defineItems(){

        menu = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "     &f&lSnow&b&lCentral &8&l- &c&lMenu!"));
        spleefMenu = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', "&f&lSnow&b&lCental &8&l- &c&lSelect a map!"));
        parkourMenu = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', "&f&lSnow&b&lCental &8&l- &c&lSelect a map!"));
        shovel = new ItemStack(Material.DIAMOND_SPADE);
        compass = new ItemStack(Material.COMPASS);
        exitQue = new ItemStack(Material.REDSTONE_TORCH_ON);
        exitQue = new ItemStack(Material.REDSTONE_TORCH_ON);
        spawn = new Location(Bukkit.getWorld("world"), 0.5, 125, 0.5, 0, 0);
        c1 = new Location(Bukkit.getWorld("world"), 453536, 4, 2454678);

        playSpleef = new ItemStack(Material.SNOW_BLOCK);
        playParkour = new ItemStack(Material.FEATHER);
        padding = new ItemStack(Material.THIN_GLASS);

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

        /** Design menu and hotbar icons **/
        ItemMeta playSpleefMeta = playSpleef.getItemMeta();
        assert playSpleefMeta != null;
        playSpleefMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8- &fSpleef &8-"));
        playSpleef.setItemMeta(playSpleefMeta);

        ItemMeta playParkourMeta = playParkour.getItemMeta();
        assert playParkourMeta != null;
        playParkourMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8- &6Parkour &8-"));
        playParkour.setItemMeta(playParkourMeta);

        ItemMeta exitQueItemMeta = exitQue.getItemMeta();
        assert exitQueItemMeta != null;
        exitQueItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cLeave queue"));
        exitQue.setItemMeta(exitQueItemMeta);

        ItemMeta paddingMeta = padding.getItemMeta();
        assert paddingMeta != null;
        paddingMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l-"));
        padding.setItemMeta(paddingMeta);

        /** Design spleef icons **/
        ItemMeta coliseumMeta = Coliseum.icon.getItemMeta();
        assert coliseumMeta != null;
        coliseumMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lColiseum"));
        Coliseum.icon.setItemMeta(coliseumMeta);

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

        ItemMeta DuneTempleMeta = DuneTemple.icon.getItemMeta();
        assert DuneTempleMeta != null;
        DuneTempleMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lDunetemple"));
        DuneTemple.icon.setItemMeta(DuneTempleMeta);

        ItemMeta OasisMeta = Oasis.icon.getItemMeta();
        assert OasisMeta != null;
        OasisMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lOasis"));
        Oasis.icon.setItemMeta(OasisMeta);

        /** Design parkour icons **/
        ItemMeta testMeta = test.icon.getItemMeta();
        assert testMeta != null;
        testMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Test"));
        test.icon.setItemMeta(testMeta);

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

}