package thornyadiscord.thornyadiscord.discord.MagikImage;

import org.bukkit.Bukkit;
import thornyadiscord.thornyadiscord.ThornyaDiscord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Perfil {
    private ThornyaDiscord pl;

    public Perfil(ThornyaDiscord main) {
        this.pl = main;
    }

    public boolean createPerfilPlayer(String nick, String cargo) throws IOException {
        //staff 730x160 red
        //https://minotar.net/avatar/user/100.png

        Files.deleteIfExists(Paths.get(pl.getDataFolder().getPath() + "\\data\\images\\perfil\\avatar.png"));
        InputStream inputStream = new URL("https://minotar.net/avatar/" + nick + "/200.png").openStream();
        Files.copy(inputStream, Paths.get(pl.getDataFolder().getPath() + "\\data\\images\\perfil\\avatar.png"), StandardCopyOption.REPLACE_EXISTING);
        String avatar = pl.getDataFolder().getPath() + "\\data\\images\\perfil\\avatar.png";
        String bg = pl.getDataFolder().getPath() + "\\data\\images\\perfil\\Background.png";
        String perfil = pl.getDataFolder().getPath() + "\\data\\images\\perfil\\perfil.png";
        String outputNick = pl.getDataFolder().getPath() + "\\data\\images\\perfil\\perfilName.png";
        String outputCargo = pl.getDataFolder().getPath() + "\\data\\images\\perfil\\cargoName.png";
        String font = "\"" + pl.getDataFolder().getPath().replace("\\", "/") + "/data/images/perfil/FiraCode-SemiBold.ttf\"";
        String isStaff = pl.getDataFolder().getPath() + "\\data\\images\\perfil\\isSTAFF.png";
        /////////////Creation////////////////
        ProcessBuilder pbNick = new ProcessBuilder("magick", "-size", "900x200", "canvas:none", "-font", font, "-pointsize", "60", "-draw", "\"text 205,85 '$nick$'\"".replace("$nick$", nick), "\"$path_output$\"".replace("$path_output$", outputNick));

        pbNick.redirectErrorStream(true);

        try {
            Process p = pbNick.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            return false;
        }
        ProcessBuilder pbCargo = new ProcessBuilder("magick", "-size", "900x200", "canvas:none", "-font", font, "-pointsize", "50", "-fill", "blue", "-draw", "\"text 205,160 '$cargo$'\"".replace("$cargo$", cargo), "\"$path_output$\"".replace("$path_output$", outputCargo));

        pbCargo.redirectErrorStream(true);

        try {
            Process p = pbCargo.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            return false;
        }
        ////////Composition/////////
        ProcessBuilder pbAvatarC = new ProcessBuilder("magick", "composite", avatar, bg, perfil);

        pbAvatarC.redirectErrorStream(true);

        try {
            Process p = pbAvatarC.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            return false;
        }
        ProcessBuilder pbNickC = new ProcessBuilder("magick", "composite", outputNick, perfil, perfil);

        pbNickC.redirectErrorStream(true);

        try {
            Process p = pbNickC.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            return false;
        }
        ProcessBuilder pbCargoC = new ProcessBuilder("magick", "composite", outputCargo, perfil, perfil);

        pbCargoC.redirectErrorStream(true);

        try {
            Process p = pbCargoC.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            return false;
        }
        /*
        if (staff.staffs.containsKey(nick)) {
            ProcessBuilder pbIsStaffC = new ProcessBuilder("magick", "composite", isStaff, perfil, perfil);

            pbIsStaffC.redirectErrorStream(true);

            try {
                Process p = pbIsStaffC.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                return false;
            }
        }*/
        return true;
    }
}