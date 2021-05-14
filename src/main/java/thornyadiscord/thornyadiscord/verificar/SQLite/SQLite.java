package thornyaplugin.thornyaplugin.verificar.SQLite;

import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.plugin.Plugin;
import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.awt.*;
import java.sql.*;
import java.util.Date;

public class SQLite {
    private final ThornyaPlugin pl;
    String url = null;
    public SQLite(ThornyaPlugin main){
        this.pl = main;
        url = "jdbc:sqlite:" + pl.getDataFolder() + "/verification.db";
        createNewTable();
    }
    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS verification (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	nick text,\n"
                + "	discord string NOT NULL,\n"
                + "	verify integer NOT NULL,\n"
                + "	rewards integer NOT NULL,\n"
                + "	timestamp real\n"
                + ");";

        try (Connection conn =  connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connect().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void criarPlayer(String discord, double timestamp, String nick) {
        String sql = "INSERT INTO verification (nick, discord, verify, rewards, timestamp) VALUES(?, ?, 0, 0, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nick);
            pstmt.setString(2, discord);
            pstmt.setDouble(3, timestamp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deletePlayerID(String discord) {
        String sql = "DELETE FROM verification WHERE discord = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, discord);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updatePlayerVerify(String nick) {
        String sql = "UPDATE verification SET verify = 1, rewards = 0 WHERE nick = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nick);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deletePlayerNick(String nick) {
        String sql = "DELETE FROM verification WHERE nick = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nick);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Boolean hasVerifyNick(String nick){
        boolean verified = false;
        String sql = "SELECT nick, verify"
                + " FROM verification WHERE nick = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, nick);//
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                if(rs.getInt("verify") == 0){
                    verified = false;
                }else{
                    verified = true;
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verified;
    }
    public Boolean hasVerifyID(String discord){
        boolean verified = false;
        String sql = "SELECT discord, verify"
                + " FROM verification WHERE discord = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, discord);//
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                if(rs.getInt("verify") == 0){
                    verified = false;
                }else{
                    verified = true;
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verified;
    }
    public Boolean hasNick(String nick){
        boolean verified = false;
        String sql = "SELECT COUNT(*) FROM verification WHERE nick = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, nick);//
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) != 0){
                    verified = true;
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verified;
    }
    public Boolean hasID(String discord){
        boolean verified = false;
        String sql = "SELECT COUNT(*) FROM verification WHERE discord = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, discord);//
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) != 0){
                    verified = true;
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verified;
    }

    public String getIdDiscord(String nick){
        String idDiscord = "";
        String sql = "SELECT discord FROM verification WHERE nick = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, nick);//
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                idDiscord = rs.getString("discord");
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idDiscord;
    }
    public String getNick(String idDiscord){
       String nick = null;
        String sql = "SELECT discord, nick"
                + " FROM verification WHERE discord = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, idDiscord);//
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                nick = rs.getString("nick");
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nick;
    }
    public double getTimestamp(String idDiscord){
        double time = 0;
        String sql = "SELECT discord, timestamp"
                + " FROM verification WHERE discord = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, idDiscord);//
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                time = rs.getDouble("timestamp");
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return time;
    }
}

