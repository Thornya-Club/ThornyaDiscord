package thornyadiscord.thornyadiscord.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import thornyadiscord.thornyadiscord.ThornyaDiscord;
import thornyadiscord.thornyadiscord.discord.listener.BotListener;
import thornyadiscord.thornyadiscord.discord.listener.OnJoin;
import thornyadiscord.thornyadiscord.discord.listener.VerificarListener;


import javax.security.auth.login.LoginException;
import java.util.Objects;

public class BotManager {
    private final ThornyaDiscord pl;
    public JDA jda;
    private Role role;
    public Guild guild;

    public BotManager(ThornyaDiscord main){
        this.pl = main;
        buildJDA();
        this.guild = getJDA().getGuildById("594364577618329620");
    }


    private void buildJDA() {
        if(jda == null) {
            String token = pl.getFile("configuration.yml").getString("token");
            try {
                this.jda = JDABuilder.createDefault(token)
                        .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                        .setActivity(Activity.playing(Objects.requireNonNull(pl.getFile("configuration.yml").getString("description"))))
                        .addEventListeners(new OnJoin(pl))
                        .addEventListeners(new BotListener(pl))
                        .addEventListeners(new VerificarListener(pl))
                        .build().awaitReady();
            } catch (LoginException | InterruptedException e) {
                Bukkit.getConsoleSender().sendMessage("Crashei na Construção do BOT *buildJDA()*");
                Bukkit.getConsoleSender().sendMessage(e.toString());
                e.printStackTrace();

            }
        }
    }

    public JDA getJDA(){ return this.jda; }



}
