package thornyadiscord.thornyadiscord.discord.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import thornyadiscord.thornyadiscord.ThornyaDiscord;

import java.awt.*;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BotListener extends ListenerAdapter {

    private final ThornyaDiscord pl;

    public BotListener(ThornyaDiscord main){
        this.pl = main;
        registrarComandosDiscord();
    }

    public void registrarComandosDiscord(){
        CommandListUpdateAction commands = pl.bot.getJDA().updateCommands();
        commands.addCommands(
                new CommandData("perfil", "Mostra seu perfil no servidor.")
        );
        commands.addCommands(
                new CommandData("banco", "Mostra o seu saldo no banco.")
        );
        commands.addCommands(
                new CommandData("players", "Lista os jogadores online no servidor.")
        );
        commands.addCommands(
                new CommandData("staffs", "Lista a staff online no servidor.")
        );
        commands.addCommands(
                new CommandData("verificar", "Vincula sua conta do discord no servidor.")
        );
        commands.queue();
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getGuild() == null)
            return;
        switch (event.getName()) {
            case "players":
                listPlayers(event.getChannel());
                break;
            case "staffs":
                break;
            case "verificar":
                break;
            case "perfil":
                break;
            case "banco":
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        String idMsg = e.getChannel().getLatestMessageId();
        String cmd = args[0];

        if (cmd.equalsIgnoreCase(ThornyaDiscord.PREFIX + "players") || cmd.equalsIgnoreCase(ThornyaDiscord.PREFIX + "onlines")) {
            listPlayers(e.getMessage().getChannel());
        }
        if(cmd.equalsIgnoreCase(ThornyaDiscord.PREFIX + "perfil")){
            showPerfil(e, args);
        }
        /*
        //Comando de ListarClan
        if (cmd.equalsIgnoreCase(pl.PREFIX + "clans")) {
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
            */

    }

    public void showPerfil(GuildMessageReceivedEvent e, String[] args) {
        if(args.length == 1){
            if (pl.sqliv.hasVerifyID(e.getAuthor().getId())) {
                String nick = pl.sqliv.getNick(e.getAuthor().getId());
                //String cargo = pl.ess.getUser(pl.sqliv.getNick(e.getAuthor().getId())).getGroup();
                //try {
                //pl.createPerfilPlayer(nick, cargo);
                //} catch (IOException ioException) {
                //    ioException.printStackTrace();
                //}
                e.getChannel().sendMessage(e.getAuthor().getAsMention()).addFile(new File(pl.getDataFolder().getPath() + "\\data\\images\\perfil\\perfil.png")).queue();
            }else{
                e.getChannel().sendMessage("**Você não é verificado!**").complete();
            }
        }else if(args.length == 2){
                /*
                if(pl.sqliv.hasVerifyNick(args[1]) && pl.ess.getUser(args[1]) != null){
                    try {
                        //pl.createPerfilPlayer(args[1], pl.ess.getUser(args[1]).getGroup());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.getChannel().sendMessage(e.getAuthor().getAsMention()).addFile(new File(pl.getDataFolder().getPath() + "\\data\\images\\perfil\\perfil.png")).queue();
                }else{
                    e.getMessage().getChannel().sendMessage("Jogador não é verificado!").complete();
                }
                */
        }else{
            e.getMessage().getChannel().sendMessage("Use **$perfil** - Mostra o seu perfil\nUse **$perfil {nick}** - Mostra o perfil de outro player").complete();
        }

    }

    public void listPlayers(MessageChannel e) {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            StringBuilder playerlist = new StringBuilder();
            Bukkit.getOnlinePlayers().forEach(player -> {
                playerlist.append("\n**").append(player.getName()).append("**");
            });

            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription(playerlist.toString())
                    .setColor(new Color(0x5AF803))
                    .setAuthor("Jogadores onlines", null, null);
            e.sendMessage(eb.build()).queue();
        }else{
            e.sendMessage("Nenhum jogador online!").queue();
        }
    }

}
