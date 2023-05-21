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


        /* These Hero and Monster factory uses are temporary,
         * to see if they work properly.
         */
        myHero = HERO_FACTORY.buildHero("");
        myMonster = MONSTER_FACTORY.buildMonster("");


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
                "MONSTER NAME NOT NULL, " +
                "MONSTER TYPE NOT NULL)";


        try  ( Connection conn = ds.getConnection();
               Statement stmt = conn.createStatement(); ) {


        } catch (SQLException e) {
            e.printStackTrace();
            System.exit( 0 );
        }

        System.out.println("GAME CREATED");
    }

}
