package spleef.LargeMethods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import spleef.Blueprints.ArenaBlueprint;
import spleef.Translator.Translator;
import spleef.spluff;

import java.sql.*;
import java.util.Set;

import static spleef.spluff.*;

public class StupidlyLargeMethods {

    AsyncPlayerChatEvent event;
    spluff plugin;

    public StupidlyLargeMethods(spluff plugin){
        this.plugin = plugin;
    }
    public static void renewArena(ArenaBlueprint arena){

        arena.active.clear();
        arena.que.clear();
        arena.playtoQue.clear();
        arena.resetQue.clear();

        switch (arena.name){

            case "Coliseum" :

                arenaList.remove(Coliseum);
                Coliseum = new ArenaBlueprint(0, 29, 3000, 22, 29, "Coliseum", new ItemStack(Material.CLAY_BALL));
                arenaList.add(Coliseum);

                ItemMeta coliseumMeta = Coliseum.icon.getItemMeta();
                assert coliseumMeta != null;
                coliseumMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lColiseum"));
                Coliseum.icon.setItemMeta(coliseumMeta);

                break;

            case "Void" :

                arenaList.remove(Void);
                Void = new ArenaBlueprint(1000, 29, 3000, 22, 33, "Void", new ItemStack(Material.BEDROCK));
                arenaList.add(Void);

                ItemMeta voidMeta = Void.icon.getItemMeta();
                assert voidMeta != null;
                voidMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lVoid"));
                Void.icon.setItemMeta(voidMeta);
                break;

            case "Nations":

                arenaList.remove(Nations);
                Nations = new ArenaBlueprint(2000, 29, 0, 22, 33, "Nations", new ItemStack(Material.BANNER));
                arenaList.add(Nations);

                ItemMeta nationsMeta = Nations.icon.getItemMeta();
                assert nationsMeta != null;
                nationsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&lNa&2&lit&3&lio&4&lns"));
                Nations.icon.setItemMeta(nationsMeta);

                break;

            case "Spooky":

                arenaList.remove(Spooky);
                Spooky = new ArenaBlueprint(0, 29, 2000, 22, 33, "Spooky", new ItemStack(Material.PUMPKIN));
                arenaList.add(Spooky);

                ItemMeta spookyMeta = Spooky.icon.getItemMeta();
                assert spookyMeta != null;
                spookyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSpooky"));
                Spooky.icon.setItemMeta(spookyMeta);

                break;

            case "Disco":

                arenaList.remove(Disco);
                Disco = new ArenaBlueprint(0, 29, 1000, 22, 29, "Disco", new ItemStack(Material.RECORD_10));
                arenaList.add(Disco);

                ItemMeta discoMeta = Disco.icon.getItemMeta();
                assert discoMeta != null;
                discoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lDisco"));
                Disco.icon.setItemMeta(discoMeta);

                break;

            case "Forest":

                arenaList.remove(Forest);
                Forest = new ArenaBlueprint(1000, 29, 1000, 22, 29, "Forest", new ItemStack(Material.SAPLING));
                arenaList.add(Forest);

                ItemMeta forestMeta = Forest.icon.getItemMeta();
                assert forestMeta != null;
                forestMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lForest"));
                Forest.icon.setItemMeta(forestMeta);

                break;

            case "Starwars":

                arenaList.remove(Starwars);
                Starwars = new ArenaBlueprint(2000, 29, 2000, 22, 29, "Starwars", new ItemStack(Material.SEA_LANTERN));
                arenaList.add(Starwars);

                ItemMeta starwarsMeta = Starwars.icon.getItemMeta();
                assert starwarsMeta != null;
                starwarsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lStarwars"));
                Starwars.icon.setItemMeta(starwarsMeta);

                break;

            case "Reef":

                arenaList.remove(Reef);
                Reef = new ArenaBlueprint(1000, 29, 0, 22, 29, "Reef", new ItemStack(Material.RAW_FISH));
                arenaList.add(Reef);

                ItemMeta reefMeta = Reef.icon.getItemMeta();
                assert reefMeta != null;
                reefMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lReef"));
                Reef.icon.setItemMeta(reefMeta);

                break;

            case "Ancient":

                arenaList.remove(Ancient);
                Ancient = new ArenaBlueprint(1000, 29, 2000, 22, 29, "Ancient", new ItemStack(Material.BONE));
                arenaList.add(Ancient);

                ItemMeta ancientMeta = Ancient.icon.getItemMeta();
                assert ancientMeta != null;
                ancientMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lAncient"));
                Ancient.icon.setItemMeta(ancientMeta);

                break;

            case "Timberland":

                arenaList.remove(Timberland);
                Timberland = new ArenaBlueprint(2000, 29, 1000, 22, 29, "Timberland", new ItemStack(Material.LOG));
                arenaList.add(Timberland);

                ItemMeta timberlandMeta = Timberland.icon.getItemMeta();
                assert timberlandMeta != null;
                timberlandMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lTimberland"));
                Timberland.icon.setItemMeta(timberlandMeta);

                break;

            case "Dunetemple":

                arenaList.remove(DuneTemple);
                DuneTemple = new ArenaBlueprint(1000, 29, -1000, 22, 29, "Dunetemple", new ItemStack(Material.LOG));
                arenaList.add(DuneTemple);

                ItemMeta DuneTempleMeta = DuneTemple.icon.getItemMeta();
                assert DuneTempleMeta != null;
                DuneTempleMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lDunetemple"));
                DuneTemple.icon.setItemMeta(DuneTempleMeta);

                break;

            case "Oasis":

                arenaList.remove(Oasis);
                Oasis = new ArenaBlueprint(1000, 29, -1000, 22, 29, "Oasis", new ItemStack(Material.LOG));
                arenaList.add(Oasis);

                ItemMeta OasisMeta = Oasis.icon.getItemMeta();
                assert OasisMeta != null;
                OasisMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lOasis"));
                Oasis.icon.setItemMeta(OasisMeta);

                break;

        }

    }

    // TODO: весь код под этой зеленой меткой предназначен для системы чата
    public void chatLevelDisplay(AsyncPlayerChatEvent event) throws Exception {

        this.event = event;

        Translator translate = new Translator();

        String format = event.getFormat();

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet data = statement.executeQuery("select * from players");

        while(data.next()){

            if(data.getString("UUID").equals(event.getPlayer().getUniqueId().toString())){

                int xp = data.getInt("xp");
                int lvl = 1;

                if(xp > 200){

                    lvl++;
                }
                if(xp > 260){

                    lvl++;
                }
                if(xp > 338){

                    lvl++;
                }
                if(xp > 440){

                    lvl++;
                }
                if(xp > 571){

                    lvl++;
                }
                if(xp > 742){

                    lvl++;
                }
                if(xp > 965){

                    lvl++;
                }
                if(xp > 1254){

                    lvl++;
                }
                if(xp > 1631){

                    lvl++;
                }
                if(xp > 2120){

                    lvl++;
                }
                if(xp > 2757){

                    lvl++;
                }
                if(xp > 3584){

                    lvl++;
                }
                if(xp > 4659){

                    lvl++;
                }
                if(xp > 6057){

                    lvl++;
                }
                if(xp > 7874){

                    lvl++;
                }
                if(xp > 10236){

                    lvl++;
                }
                if(xp > 13307){

                    lvl++;
                }
                if(xp > 17299){

                    lvl++;
                }
                if(xp > 22488){

                    lvl++;
                }
                if(xp > 29235){

                    lvl++;
                }
                if(xp > 38006){

                    lvl++;
                }
                if(xp > 49408){

                    lvl++;
                }
                if(xp > 64230){

                    lvl++;
                }
                if(xp > 83499){

                    lvl++;
                }
                if(xp > 104374){

                    lvl++;
                }
                if(xp > 125249){

                    lvl++;
                }
                if(xp > 150299){

                    lvl++;
                }
                if(xp > 180359){

                    lvl++;
                }
                if(xp > 216431){

                    lvl++;
                }
                if(xp > 259717){

                    lvl++;
                }
                if(xp > 311661){

                    lvl++;
                }
                if(xp > 373993){

                    lvl++;
                }
                if(xp > 448792){

                    lvl++;
                }
                if(xp > 538550){

                    lvl++;
                }
                if(xp > 646261){

                    lvl++;
                }
                if(xp > 775513){

                    lvl++;
                }
                if(xp > 930616){

                    lvl++;
                }
                if(xp > 1116739){

                    lvl++;
                }
                if(xp > 1340087){

                    lvl++;
                }
                if(xp > 1608104){

                    lvl++;
                }
                if(xp > 1929725){

                    lvl++;
                }
                if(xp > 2315670){

                    lvl++;
                }
                if(xp > 2778804){

                    lvl++;
                }
                if(xp > 3334565){

                    lvl++;
                }
                if(xp > 4001478){

                    lvl++;
                }
                if(xp > 4801774){

                    lvl++;
                }
                if(xp > 5762129){

                    lvl++;
                }
                if(xp > 6914555){

                    lvl++;
                }
                if(xp > 8297466){

                    lvl = 50;
                }else{

                    lvl++;

                }

                sendMessage(event.getPlayer(), lvl, event.getRecipients(), data);

                event.setCancelled(true);

            }

        }

    }

    public void sendMessage(Player sender, int lvl, Set<Player> recipients, ResultSet data) throws Exception {

        String fromLang = data.getString("lang");

        Connection connection1 = DriverManager.getConnection(url, username, password);

        Statement statement1 = connection1.createStatement();

        ResultSet data1 = statement1.executeQuery("select * from players");

        String originalMessage = event.getMessage();

        if (sender.hasPermission("group.creator")) {
            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&4: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.creator", originalMessage, fromLang);

        }else if(sender.hasPermission("group.admins")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&c: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.admins", originalMessage, fromLang);

        }else if(sender.hasPermission("group.meneger")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&5: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.meneger", originalMessage, fromLang);

        }else if(sender.hasPermission("group.headmoder")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&d: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.headmoder", originalMessage, fromLang);

        }else if(sender.hasPermission("group.moder")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&2: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.moder", originalMessage, fromLang);

        }else if(sender.hasPermission("group.newmoder")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&a: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.newmoder", originalMessage, fromLang);

        }else if(sender.hasPermission("group.builder")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&e: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.builder", originalMessage, fromLang);

        }else if(sender.hasPermission("group.youtube")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&f: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.youtube", originalMessage, fromLang);

        }else if(sender.hasPermission("group.twitch")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&5: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.twitch", originalMessage, fromLang);

        }else if(sender.hasPermission("group.lord")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&9: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.lord", originalMessage, fromLang);

        }else if(sender.hasPermission("group.frozty")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&b: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.frozty", originalMessage, fromLang);

        }else if(sender.hasPermission("group.chill")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&1: &f" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.chill", originalMessage, fromLang);

        }else if(sender.hasPermission("group.default")){

            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&8: &7" + originalMessage));

            spreadMessage(sender, lvl, recipients, data1, "group.default", originalMessage, fromLang);

        }

    }

    public void spreadMessage(Player sender, int lvl, Set<Player> recipients, ResultSet data1, String group, String originalMessage, String fromLang) throws Exception {

        Translator translator = new Translator();

        while (data1.next()) {

            for (Player player : recipients) {

                if (data1.getString("UUID").equals(player.getUniqueId().toString())) {
                    String newMessage;
                    String toLang = data1.getString("lang");
                    if(fromLang.equals(toLang)){
                        newMessage = originalMessage;
                    }else{
                        newMessage = translator.translate(fromLang, toLang, originalMessage);
                    }

                    if (sender != player) {

                        switch(group){

                            case "group.creator":
                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&4: &f" + newMessage));
                                break;
                            case "group.admins":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&c: &f" + newMessage));
                                break;
                            case "group.meneger":
                            case "group.twitch":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&5: &f" + newMessage));
                                break;
                            case "group.headmoder":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&d: &f" + newMessage));
                                break;
                            case "group.moder":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&2: &f" + newMessage));
                                break;
                            case "group.newmoder":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&a: &f" + newMessage));
                                break;
                            case "group.builder":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&e: &f" + newMessage));
                                break;
                            case "group.youtube":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&f: &f" + newMessage));
                                break;
                            case "group.lord":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&9: &f" + newMessage));
                                break;
                            case "group.frozty":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&b: &f" + newMessage));
                                break;
                            case "group.chill":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&1: &f" + newMessage));
                                break;
                            case "group.default":
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', " &7-&c" + lvl + "&7- " + sender.getDisplayName() + "&8: &7" + newMessage));
                                break;

                        }

                    }

                }

            }

        }

    }

}
