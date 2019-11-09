package Gameplay.Util;

import Gameplay.Model.Artifact.Armor;
import Gameplay.Model.Artifact.Helm;
import Gameplay.Model.Artifact.Weapon;
import Gameplay.Model.Charactor.Hero;
import Gameplay.Model.Charactor.HeroBuilder;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private static final String DATA_BASE_URL = "jdbc:sqlite::resource:heroes.db";
    private static Connection connection;

    public static void connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DATA_BASE_URL);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        connection = conn;
    }

    public static void close() {
        try {
            if (connection != null)
                connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection getConnection() {
        if (connection == null)
            connect();
        return connection;
    }

    public static int insert(String name, String className, int level, int xp, int attack, int defense, int hp) {
        String sqlQuery = "INSERT INTO heroes(name, class, level, xp, attack, defense, hp) VALUES(?, ?, ?, ?, ?, ?, ?)";
        int id = 0;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, className);
            preparedStatement.setInt(3, level);
            preparedStatement.setInt(4, xp);
            preparedStatement.setInt(5, attack);
            preparedStatement.setInt(6, defense);
            preparedStatement.setInt(7, hp);
            preparedStatement.executeUpdate();

            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name=\"heroes\"");
            if (rs.next())
                id = rs.getInt("seq");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public static ArrayList<String> selectAll() {
        String sqlQuery = "SELECT * FROM heroes";
        ArrayList<String> arrayList = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            for (int i = 1; rs.next(); i++) {
                arrayList.add(String.format("%d. %s (%s)", i, rs.getString("name"), rs.getString("class")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrayList;
    }

    public static Hero selectHeroById(int id) {
        String sqlQuery = "SELECT * FROM heroes WHERE id = ?";
        Hero hero = null;

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                HeroBuilder builder = new HeroBuilder();
                builder.setId(rs.getInt("id"));
                builder.setName(rs.getString("name"));
                builder.setHeroClass(rs.getString("class"));
                builder.setLevel(rs.getInt("level"));
                builder.setExperience(rs.getInt("xp"));
                builder.setAttack(rs.getInt("attack"));
                builder.setDefense(rs.getInt("defense"));
                builder.setHitPoint(rs.getInt("hp"));

                if (rs.getString("weapon_name") != null)
                    builder.setWeapon(new Weapon(rs.getString("weapon_name"), rs.getInt("weapon_value")));
                if (rs.getString("helm_name") != null)
                    builder.setHelm(new Helm(rs.getString("helm_name"), rs.getInt("helm_value")));
                if (rs.getString("armor_name") != null)
                    builder.setArmor(new Armor(rs.getString("armor_name"), rs.getInt("armor_value")));

                hero = builder.getHero();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hero;
    }

    public static void updateHero(Hero hero) {
        String sqlQuery = "UPDATE heroes SET level = ?, xp = ?, attack = ?, defense = ?, hp = ? , " +
                "weapon_name = ?, weapon_value = ?, helm_name = ?, helm_value = ?, armor_name = ?, armor_value = ? " +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, hero.getLevel());
            preparedStatement.setInt(2, hero.getExperience());
            preparedStatement.setInt(3, hero.getAttack());
            preparedStatement.setInt(4, hero.getDefense());
            preparedStatement.setInt(5, hero.getHitPoints());

            if (hero.getWeapon() != null) {
                preparedStatement.setString(6, hero.getWeapon().getName());
                preparedStatement.setInt(7, hero.getWeapon().getPoints());
            }
            if (hero.getHelm() != null) {
                preparedStatement.setString(8, hero.getHelm().getName());
                preparedStatement.setInt(9, hero.getHelm().getPoints());
            }
            if (hero.getArmor() != null) {
                preparedStatement.setString(10, hero.getArmor().getName());
                preparedStatement.setInt(11, hero.getArmor().getPoints());
            }

            preparedStatement.setInt(12, hero.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//    private static final String DB_URL = "jdbc:sqlite::resource.db";
//    private static Connection conn;
//
//    public static void connect() {
//        Connection c = null;
//        try {
//         Class.forName("org.sqlite.JDBC");
//         c = DriverManager.getConnection(DB_URL);
//           // System.out.println("Database Connected");
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        conn = c;
//    }
//
//    public static void close() {
//        try {
//            if (conn != null);
//            conn.close();
//            conn = null;
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//    public static Connection getConn() {
//        if (conn == null);
//        connect();
//        return conn;
//    }
//
//    public static int insert(String name, String ClassName, int level, int xp, int attack, int defense, int hp) {
//        String sqlQuery = "INSET INTO heroes(name, class, level, xp, attack, defense, hp) VAULES(?, ?, ?, ?, ?, ?, ?)";
//        int id = 0;
//        try (PreparedStatement pstate = getConn().prepareStatement(sqlQuery)){
//            pstate.setString(1, name);
//            pstate.setString(2, ClassName);
//            pstate.setInt(3, level);
//            pstate.setInt(4, xp);
//            pstate.setInt(5, attack);
//            pstate.setInt(6, defense);
//            pstate.setInt(7, hp);
//            pstate.executeUpdate();
//
//            Statement stmt = getConn().createStatement();
//            ResultSet rs = stmt.executeQuery("section seq FROM sqlite_sequence WHERE name=\"heroes\"");
//            if (rs.next())
//            id = rs.getInt("seq");
//
//        }catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return id;
//    }
//
//    public static ArrayList<String> selectAll() {
//       String sqlQuery = "SELECT * FROM heroes";
//       ArrayList<String> arrayList = new ArrayList<>();
//       try (Statement pstate = getConn().createStatement()){
//           ResultSet rs = pstate.executeQuery(sqlQuery);
//           for (int i = 1; rs.next(); i++) {
//               arrayList.add(String.format("%d. %s(%s)", i, rs.getString("name"), rs.getString("class")));
//
//           }
//       } catch (SQLException e) {
//           System.out.println(e.getMessage());
//       }
//       return arrayList;
//    }
//
//    public static Hero selectHeroById(int id) {
//        String sqlQuery = "SELECT * FROM heroes WHERE id = ?";
//        Hero hero = null;
//        try (PreparedStatement pstate = getConn().prepareStatement(sqlQuery)){
//            pstate.setInt(1, id);
//            ResultSet rs = pstate.executeQuery();
//            if (rs.next()) {
//                HeroBuilder builder = new HeroBuilder();
//                builder.setId(rs.getInt("id"));
//                builder.setName(rs.getString("name"));
//                builder.setHeroClass(rs.getString("class"));
//                builder.setLevel(rs.getInt("level"));
//                builder.setExperience(rs.getInt("xp"));
//                builder.setAttack(rs.getInt("attack"));
//                builder.setDefense(rs.getInt("defense"));
//                builder.setHitPoint(rs.getInt("hp"));
//
//                if (rs.getString("weapon_name") != null)
//                    builder.setWeapon(new Weapon(rs.getString("weapon_name"), rs.getInt("weapon_value")));
//                if (rs.getString("helm_name") != null)
//                    builder.setHelm(new Helm(rs.getString("helm_name"), rs.getInt("helm_value")));
//                if (rs.getString("armor_name") != null)
//                    builder.setArmor(new Armor(rs.getString("Armor_name"), rs.getInt("Armor_value")));
//
//                hero = builder.getHero();
//
//            }
//        } catch (SQLException e){
//            System.out.println(e.getMessage());
//
//        }
//        return hero;
//    }
//
//    public static void  updateHero(Hero hero) {
//        String sqlQuery = "UPDATE heroes SET LEVEL = ?, xp = ?, attack = ?, defense = ?, hp = ?, " +
//             "weapon_name = ?, weapon_value = ?, helm_name = ?, helm_value = ?, armor_name = ?, armor_value, " +
//             "WHERE id = ?";
//        try (PreparedStatement pstate = getConn().prepareStatement(sqlQuery)){
//            pstate.setInt(1, hero.getLevel());
//            pstate.setInt(2, hero.getExperience());
//            pstate.setInt(3, hero.getAttack());
//            pstate.setInt(4, hero.getDefense());
//            pstate.setInt(5, hero.getHitPoints());
//
//            if (hero.getWeapon() != null) {
//                pstate.setString(6, hero.getWeapon().getName());
//                pstate.setInt(7, hero.getWeapon().getPoints());
//            }
//            if (hero.getHelm() != null) {
//                pstate.setString(8, hero.getHelm().getName());
//                pstate.setInt(9, hero.getHelm().getPoints());
//            }
//            if (hero.getArmor() != null) {
//                pstate.setString(10, hero.getArmor().getName());
//                pstate.setInt(11, hero.getArmor().getPoints());
//            }
//            pstate.setInt(12, hero.getId());
//            pstate.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
}
