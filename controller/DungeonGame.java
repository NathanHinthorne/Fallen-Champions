package controller;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.*;

public class DungeonGame {

    private static HeroFactory HERO_FACTORY = new HeroFactory();
    private static MonsterFactory MONSTER_FACTORY = new MonsterFactory();

    public Hero myHero;
    public Monster myMonster;

    private DungeonGame() {

        // this is my test of the dungeon builder -Nathan
        // small dungeon = easy difficulty
        // medium dungeon = medium difficulty
        // large dungeon = hard difficulty
        Dungeon theSmallDungeon = new Dungeon(new Dungeon.SmallDungeonBuilder());
        Dungeon theMediumDungeon = new Dungeon(new Dungeon.SmallDungeonBuilder());
        Dungeon theLargeDungeon = new Dungeon(new Dungeon.SmallDungeonBuilder());



        /* These Hero and Monster factory uses are temporary,
         * to see if they work properly.
         */
        myHero = HERO_FACTORY.buildHero(HeroTypes.WARRIOR);
        myMonster = MONSTER_FACTORY.buildMonster(MonsterTypes.SKELETON);


    }



    public static void main(String[] theArgs) {
        SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:monsters.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        String query = "CREATE TABLE IF NOT EXISTS monsters ( " +
                "TYPE NOT NULL )";


        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {
            int rv = stmt.executeUpdate(query);
            System.out.println("database executed successfully: " + rv);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Temporary to test DB functionality
        String query1 = "INSERT INTO monsters ( TYPE ) VALUES ( myMonster )";
        String query2 = "INSERT INTO questions ( TYPE ) VALUES ( myMonster )";

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {
            int rv = stmt.executeUpdate(query1);
            System.out.println("database executed successfully: " + rv);

            rv = stmt.executeUpdate(query2);
            System.out.println("database executed successfully: " + rv);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        query = "SELECT * FROM monsters";

        try ( Connection conn = ds.getConnection();
              Statement stmt = conn.createStatement(); ) {

            ResultSet rs = stmt.executeQuery(query);

            while ( rs.next() ) {
                String monster = rs.getString( "TYPE" );

                System.out.println( "Result: Monster = " + monster );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }

        System.out.println("GAME CREATED");
    }

}
