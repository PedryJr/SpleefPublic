package spleef.ManualUpdate;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import spleef.Blueprints.SpleefBlueprint;

import java.sql.*;

import static spleef.spluff.*;

public class ManualUpdate extends BukkitRunnable {
    @Override
    public void run() {

        try {

            UpdateLeaderboard();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void UpdateLeaderboard() throws SQLException {


        for(SpleefBlueprint arena : spleefArenaList){

            if(arena.active.isEmpty()){

                if(arena.beginMinispleef != null){

                    arena.beginMinispleef.cancel();

                }
                if(arena.beginDecay != null){

                    arena.beginDecay.cancel();

                }

            }

        }


        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("select * from players order by money DESC");

        lb.getLines().clear();
        lb.getLines().appendText(head.getText());

        int i = 1;
        while(result.next()){

            line = lb.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&b" + i + "&c. &f&l" + result.getString(2) + " &c| &f&l" + result.getString(4) + "&c$"));

            if(i >= 10){

                break;

            }
            i++;

        }


    }

}
