package FallenChampions.controller;

import FallenChampions.model.characters.monsters.Monster;
import FallenChampions.model.characters.monsters.MonsterFactory;
import FallenChampions.model.characters.monsters.MonsterTypes;
import FallenChampions.model.dungeon.Difficulty;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseInitializer {
    private static DatabaseInitializer instance = null;

    // Private constructors to prevent external instantiation
    private DatabaseInitializer() {
    }

    // Singleton pattern to ensure only one instance exists
    public static synchronized DatabaseInitializer getInstance() {
        if (instance == null) {
            instance = new DatabaseInitializer();
        }
        return instance;
    }

    //!!! NAMES DATABASE SHOULD CONTAIN BOTH REGULAR NAMES AND FUNNY NAMES
    //!!! DON'T USE DATABASE FOR MAKING MONSTERS, USE PERCENTAGES INSTEAD WITHIN OTHER CLASSES



    synchronized Map<Character, List<String>> readNames() {
        Map<Character, List<String>> heroNames = new HashMap<>();

        try (InputStream inputStream = Game.class.getResourceAsStream("/Hero_Names.db")) {
            if (inputStream != null) {
                SQLiteDataSource ds = new SQLiteDataSource();
                ds.setUrl("jdbc:sqlite::memory:");

                // Read the database file from the input stream and create an in-memory database
                try (Connection conn = ds.getConnection()) {
                    conn.createStatement().execute("ATTACH DATABASE ':memory:' AS 'filedb'");
                    conn.createStatement().execute("CREATE TABLE filedb.Names AS SELECT * FROM main.Names");

                    // iterate through all the columns in the names table and add each name to the list of names
                    List<String> names = new ArrayList<>();
                    for (char letter = 'A'; letter <= 'Z'; letter++) { //TODO should be able to iterate through the columns in a better way than iterating through the alphabet
                        ResultSet rs = conn.createStatement().executeQuery("SELECT " + letter + " FROM Names");

                        while (rs.next()) {
                            names.add(rs.getString(letter));
                        }
                        heroNames.put(letter, names);
                    }
                }
            } else {
                System.err.println("Could not access the hero names database file.");
                System.exit(1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return heroNames;
    }


    public Map<Character, List<String>> tempReadNames() { //!!! REPLACE WITH METHOD THAT READS FROM DATABASE

        Map<Character, List<String>> names = new HashMap<>();

        names.put('A', Arrays.asList("Awesome", "Awkward", "Absurd"));
        names.put('B', Arrays.asList("Bumbling", "Bizarre", "Bemused"));
        names.put('C', Arrays.asList("Caffeinated", "Chump", "Crackpot"));
        names.put('D', Arrays.asList("Daft", "Dramatic", "Doofenshmirtz"));
        names.put('E', Arrays.asList("Exuberant", "Egghead", "Embarrassing"));
        names.put('F', Arrays.asList("Fungus", "Flapjack", "Flabbergasted"));
        names.put('G', Arrays.asList("Goofball", "Goober", "Gobsmacked"));
        names.put('H', Arrays.asList("Hooman", "Hilarious", "Hairy"));
        names.put('I', Arrays.asList("Itchy", "Impulsive", "Inventor"));
        names.put('J', Arrays.asList("Joke", "Jiggly", "John Cena"));
        names.put('K', Arrays.asList("King", "Kooky", "Kazoo"));
        names.put('L', Arrays.asList("Lollygagger", "Loony", "Lovable"));
        names.put('M', Arrays.asList("Muffin", "Magnificent", "Masterful"));
        names.put('N', Arrays.asList("Nerd", "Noob", "NULL"));
        names.put('O', Arrays.asList("Ominous", "Obnoxious", "Optimistic"));
        names.put('P', Arrays.asList("Punk", "Passionate", "Pale"));
        names.put('Q', Arrays.asList("Quick-witted", "Questionable", "Quarantined"));
        names.put('R', Arrays.asList("Resourceful", "Radioactive", "Rancid"));
        names.put('S', Arrays.asList("Strong", "Stinky", "Sassy"));
        names.put('T', Arrays.asList("Taco", "Terrific", "Terrible"));
        names.put('U', Arrays.asList("Undercooked", "Unbeatable", "Unicycle"));
        names.put('V', Arrays.asList("Vaccinated", "Vegetarian", "Vile"));
        names.put('W', Arrays.asList("Weakling", "Wanted", "Witty"));
        names.put('X', Arrays.asList("Xylophone", "Crummy", "Bashful"));
        names.put('Y', Arrays.asList("Yellow-bellied", "Yucky", "Yummy"));
        names.put('Z', Arrays.asList("Zesty", "Zealous", "Zero"));

        return names;
    }

}

