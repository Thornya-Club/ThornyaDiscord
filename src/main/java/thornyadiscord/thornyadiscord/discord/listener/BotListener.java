package thornyaplugin.thornyaplugin.discord.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.awt.*;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BotListener extends ListenerAdapter {

    private ThornyaPlugin pl;

    public BotListener(ThornyaPlugin main){
        this.pl = main;
    }

    @Override
    public void onUserUpdateName(@NotNull UserUpdateNameEvent e) {

        Member m = pl.bot.guild.getMember(e.getUser());
        m.modifyNickname(e.getOldName()).queue();
        e.getUser().getJDA().getGuilds().forEach(guild -> {
            if(guild.equals(pl.bot.guild)) {
                guild.getTextChannelById("736398288936370177").sendMessage("O jogador verificado " + e.getOldName() + " trocou seu nick para " + e.getNewName()).queue();
                guild.getMember(e.getUser()).modifyNickname(e.getOldName()).queue();
            }
        });
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        String idMsg = e.getChannel().getLatestMessageId();
        String cmd = args[0];
        if(cmd.equalsIgnoreCase(ThornyaPlugin.PREFIX + "perfil")){
            if(args.length == 1){
                if (pl.sqliv.hasVerifyID(e.getAuthor().getId())) {
                    String nick = pl.sqliv.getNick(e.getAuthor().getId());
                    String cargo = pl.ess.getUser(pl.sqliv.getNick(e.getAuthor().getId())).getGroup();
                    try {
                        pl.createPerfilPlayer(nick, cargo);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.getChannel().sendMessage(e.getAuthor().getAsMention()).addFile(new File(pl.getDataFolder().getPath() + "\\data\\images\\perfil\\perfil.png")).queue();
                }else{
                    e.getChannel().sendMessage("**Você não é verificado!**").complete();
                }
            }else if(args.length == 2){
                if(pl.sqliv.hasVerifyNick(args[1]) && pl.ess.getUser(args[1]) != null){
                    try {
                        pl.createPerfilPlayer(args[1], pl.ess.getUser(args[1]).getGroup());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.getChannel().sendMessage(e.getAuthor().getAsMention()).addFile(new File(pl.getDataFolder().getPath() + "\\data\\images\\perfil\\perfil.png")).queue();
                }else{
                    e.getMessage().getChannel().sendMessage("Jogador não é verificado!").complete();
                }
            }else{
                e.getMessage().getChannel().sendMessage("Use **$perfil** - Mostra o seu perfil\nUse **$perfil {nick}** - Mostra o perfil de outro player").complete();
            }
        }
        //Comando de ListarClan
        if (cmd.equalsIgnoreCase(ThornyaPlugin.PREFIX + "clans")) {
            EmbedBuilder eb = new EmbedBuilder();
            if (!pl.sc.getClanManager().getClans().isEmpty()) {
                eb.setColor(new Color(7946786)).setTitle("Clans do Servidor");
                AtomicInteger i = new AtomicInteger(1);
                StringBuilder descriptionInput = new StringBuilder();
                pl.sc.getClanManager().getClans().forEach(clan -> {
                    //descriptionInput.append("\n**[" + i.toString() + "]** " + clan.getName() + " - **KDR: " + String.format("%.02f", clan.getTotalKDR()) + "**");
                    descriptionInput.append("\n**[" + i.toString() + "]** " + clan.getName());
                    i.getAndIncrement();
                });
                String description = descriptionInput.toString();
                eb.setDescription(description);
                e.getMessage().getChannel().sendMessage(eb.build()).complete();

            } else {
                e.getMessage().getChannel().sendMessage("Não tem clans no servidor!").complete();
            }

        }
        if (cmd.equalsIgnoreCase(ThornyaPlugin.PREFIX + "staff") || cmd.equalsIgnoreCase(ThornyaPlugin.PREFIX + "staffs")) {
            StringBuilder stafflist = new StringBuilder();
            pl.getFile("configuration.yml").getStringList("staffs").forEach(staff -> {
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(staff))) {
                    if (pl.ess.getUser(staff).isAfk() && !(pl.ess.getUser(staff).isVanished())) {
                        stafflist.append("\n**" + staff + "** - <:btn_yellow:798296415604178964>");
                    } else if (pl.ess.getUser(staff).isVanished()) {
                        stafflist.append("\n**" + staff + "** - <:btn_red:798296423908769893>");
                    } else if (pl.ess.getUser(staff).isHidden()) {
                        stafflist.append("\n**" + staff + "** - <:btn_red:798296423908769893>");
                    }else if (pl.ess.getUser(staff).isAfk() && pl.ess.getUser(staff).isVanished()) {
                        stafflist.append("\n**" + staff + "** - <:btn_red:798296423908769893>");
                    } else {
                        stafflist.append("\n**" + staff + "** - <:btn_green:798296406665330688>");
                    }
                } else {
                    stafflist.append("\n**" + staff + "** - <:btn_red:798296423908769893>");
                }
            });
            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription(stafflist.toString())
                    .setColor(new Color(0xFF0000))
                    .setAuthor("Staffs onlines", null, null);
            e.getMessage().getChannel().sendMessage(eb.build()).queue();
        }
        if (cmd.equalsIgnoreCase(ThornyaPlugin.PREFIX + "players") || cmd.equalsIgnoreCase(ThornyaPlugin.PREFIX + "onlines")) {
            if (Bukkit.getOnlinePlayers().size() > 0) {
                StringBuilder playerlist = new StringBuilder();
                Bukkit.getOnlinePlayers().forEach(player -> {
                    playerlist.append("\n**" + player.getName() + "**");
                });

                EmbedBuilder eb = new EmbedBuilder();
                eb.setDescription(playerlist.toString())
                        .setColor(new Color(0x5AF803))
                        .setAuthor("Jogadores onlines", null, null);
                e.getMessage().getChannel().sendMessage(eb.build()).queue();
            }else{
                e.getMessage().getChannel().sendMessage("Nenhum jogador online!").queue();
            }
        }

        //if (cmd.equalsIgnoreCase(ThornyaPlugin.PREFIX + "clan")) {
        //}
    }

}
