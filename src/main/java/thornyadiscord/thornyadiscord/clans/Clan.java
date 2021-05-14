package thornyadiscord.thornyadiscord.clans;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import thornyadiscord.thornyadiscord.ThornyaDiscord;

import java.util.EnumSet;

public class Clan {
    private thornyadiscord.thornyadiscord.ThornyaDiscord pl;

    public Clan(ThornyaDiscord main){
        this.pl = main;
    }

    public void buyChannel(String tag, String userID, String nickname){
        long userIDNew = Long.parseLong(userID);

        Guild guild = pl.bot.getJDA().getGuildById("594364577618329620");
        ChannelAction<VoiceChannel> cV = guild.getCategoryById("792965102545535007").createVoiceChannel("\uD83D\uDEE1》 ClanVoz-" + tag);
        cV.addMemberPermissionOverride(userIDNew,  EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.VOICE_CONNECT,
                Permission.VOICE_SPEAK,
                Permission.VOICE_MUTE_OTHERS,
                Permission.VOICE_STREAM), null)
        .addPermissionOverride(guild.getRoleById("654783484979707904"),  null, EnumSet.of(
                Permission.VOICE_CONNECT,
                Permission.VOICE_SPEAK,
                Permission.VIEW_CHANNEL,
                Permission.CREATE_INSTANT_INVITE))
                //.setUserlimit(pl.sc.getSettingsManager().getClanMaxLength())
                .queue();
        //
        ChannelAction<TextChannel> cC = guild.getCategoryById("792965102545535007").createTextChannel("\uD83D\uDEE1》ClanChat-" + tag);
        cC.addMemberPermissionOverride(userIDNew, EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_READ,
                Permission.MESSAGE_ADD_REACTION,
                Permission.MESSAGE_HISTORY,
                Permission.MESSAGE_MENTION_EVERYONE,
                Permission.MESSAGE_ATTACH_FILES),null)

        .addPermissionOverride(guild.getRoleById("654783484979707904"), null, EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_READ,
                Permission.CREATE_INSTANT_INVITE))
                //.setTopic("Chat de conversas do clan [" + pl.sc.getClanManager().getClanPlayer(nickname).getTag() + "] " + pl.sc.getClanManager().getClanPlayer(nickname).getClan().getName())
                .queue();

        //pl.cc.clansChannelUpdate();

    }


}
