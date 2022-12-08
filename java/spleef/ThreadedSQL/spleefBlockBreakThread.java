package spleef.ThreadedSQL;

import jdk.internal.foreign.ArenaAllocator;
import org.bukkit.event.block.BlockBreakEvent;
import spleef.spleef.Blueprints.ArenaBlueprint;

import java.sql.*;

import static spleef.spleef.Spleef.*;
import static spleef.spleef.Spleef.password;

public class spleefBlockBreakThread extends Thread{

    BlockBreakEvent event;

    public spleefBlockBreakThread(BlockBreakEvent event){

        this.event = event;

    }

    @Override
    public void run() {

        if(name == null){

            for(ArenaBlueprint arena: arenaList){

                if(arena.active.contains(event.getPlayer())){

                    arena.blocks.remove(event.getBlock());

                }

            }

            Connection connection = null;
            ResultSet data = null;

            try {

                connection = DriverManager.getConnection(url, username, password);

                Statement statement = connection.createStatement();

                data = statement.executeQuery("select * from players order by money DESC");

                while(data.next()){

                    if(data.getString("UUID").equals(event.getPlayer().getUniqueId().toString())){

                        int xp = data.getInt("xp");

                        xp++;

                        statement.executeUpdate("update players set xp = '" + xp + "' where UUID = '" + event.getPlayer().getUniqueId() + "'");

                        break;


                    }

                }

            } catch (SQLException e) {

                throw new RuntimeException(e);

            }finally {

                if(connection!=null) {

                    try {

                        assert data != null;
                        data.close();
                        connection.close();

                    } catch (SQLException e) {

                        throw new RuntimeException(e);

                    }

                }

            }

        }else{

            if(name.que == null){

                for(ArenaBlueprint arena: arenaList){

                    if(arena.active.contains(event.getPlayer())){

                        arena.blocks.remove(event.getBlock());

                    }

                }

                Connection connection = null;
                ResultSet data = null;

                try {

                    connection = DriverManager.getConnection(url, username, password);

                    Statement statement = connection.createStatement();

                    data = statement.executeQuery("select * from players order by money DESC");

                    while(data.next()){

                        if(data.getString("UUID").equals(event.getPlayer().getUniqueId().toString())){

                            int xp = data.getInt("xp");

                            xp++;

                            statement.executeUpdate("update players set xp = '" + xp + "' where UUID = '" + event.getPlayer().getUniqueId() + "'");

                            break;


                        }

                    }

                } catch (SQLException e) {

                    throw new RuntimeException(e);

                }finally {

                    if(connection!=null) {

                        try {

                            assert data != null;
                            data.close();
                            connection.close();

                        } catch (SQLException e) {

                            throw new RuntimeException(e);

                        }

                    }

                }

            }else{

                if(!name.que.contains(event.getPlayer())){

                    for(ArenaBlueprint arena: arenaList){

                        if(arena.active.contains(event.getPlayer())){

                            arena.blocks.remove(event.getBlock());

                        }

                    }

                    Connection connection = null;
                    ResultSet data = null;

                    try {

                        connection = DriverManager.getConnection(url, username, password);

                        Statement statement = connection.createStatement();

                        data = statement.executeQuery("select * from players order by money DESC");

                        while(data.next()){

                            if(data.getString("UUID").equals(event.getPlayer().getUniqueId().toString())){

                                int xp = data.getInt("xp");

                                xp++;

                                statement.executeUpdate("update players set xp = '" + xp + "' where UUID = '" + event.getPlayer().getUniqueId() + "'");

                                break;


                            }

                        }

                    } catch (SQLException e) {

                        throw new RuntimeException(e);

                    }finally {

                        if(connection!=null) {

                            try {

                                assert data != null;
                                data.close();
                                connection.close();

                            } catch (SQLException e) {

                                throw new RuntimeException(e);

                            }

                        }

                    }

                }

            }

        }

    }

}
