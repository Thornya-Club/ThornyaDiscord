package thornyaplugin.thornyaplugin.discord.listener;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

public class OnJoin extends ListenerAdapter {
    private ThornyaPlugin pl;
    public OnJoin(ThornyaPlugin main){
        this.pl = main;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Bukkit.getConsoleSender().sendMessage(event.getUser().getName() + " recebeu o cargo " + event.getGuild().getRoleById("654783484979707904").getName());
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("654783484979707904")).complete();
    }
}
