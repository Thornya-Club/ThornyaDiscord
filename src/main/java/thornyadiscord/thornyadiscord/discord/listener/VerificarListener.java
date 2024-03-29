package thornyadiscord.thornyadiscord.discord.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import thornyadiscord.thornyadiscord.ThornyaDiscord;
import thornyadiscord.thornyadiscord.discord.Embeds.Embeds;

import java.awt.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("ALL")
public class VerificarListener extends ListenerAdapter {

    private final ThornyaDiscord pl;


    public VerificarListener(ThornyaDiscord main){
        this.pl = main;
    }
    public void commandVerificar(String cmd, GuildMessageReceivedEvent e, String[] args){

        if(cmd.equalsIgnoreCase(ThornyaDiscord.PREFIX + "verificar") ){
            if(e.getMessage().getChannel().getId().equalsIgnoreCase("794042606061223946")) {
                if(args.length == 1){
                    /*
                    if (!pl.sqliv.hasVerifyID(e.getAuthor().getId())) {
                        if (pl.sqliv.getTimestamp(e.getAuthor().getId()) == 0) {
                            if (!pl.sqliv.hasVerifyNick(args[1])) {
                                if (!pl.getServer().getOnlinePlayers().isEmpty()) {
                                    AtomicBoolean playerOn = new AtomicBoolean(false);
                                    pl.getServer().getOnlinePlayers().forEach(player -> {
                                        if (player.getName().equalsIgnoreCase(args[1])) {
                                            playerOn.set(true);
                                        }
                                    });
                                    if (playerOn.get()) {
                                        pl.sqliv.criarPlayer(e.getAuthor().getId(), c.getTimeInMillis(), args[1]);
                                        sendEmbedSearch(args[1]);
                                        //Embeds.sendEmbedVerificado(args[1], e.getAuthor().getName() , e.getAuthor().getId());

                                    } else {
                                        e.getMessage().getChannel().sendMessage("Você não está online no servidor!").complete();
                                    }
                                } else {
                                    e.getMessage().getChannel().sendMessage("Servidor vazio! Entre no servidor para se regisrar.").complete();
                                }
                            } else {
                                e.getMessage().getChannel().sendMessage("Esse jogador já é verificado!").complete();
                            }

                        } else {
                            if (pl.sqliv.getTimestamp(e.getAuthor().getId()) > System.currentTimeMillis()) {
                                e.getMessage().getChannel().sendMessage("Você já tem uma verificação em processo!").complete();
                            } else {
                                pl.sqliv.deletePlayerID(e.getAuthor().getId());
                                e.getMessage().getChannel().sendMessage("Digite o comando novamente para atualizar seus dados!").complete();
                            }
                        }
                    }*/
                }else{
                    e.getMessage().getChannel().sendMessage("Use **$verificar**").complete();
                }
            }else{
                e.getMessage().getChannel().sendMessage("Use o canal " + e.getGuild().getTextChannelById("794042606061223946").getAsMention() + " para o uso desse comando!" ).complete();
            }
        }

    }
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        String cmd = args[0];
        /*
        if(cmd.equalsIgnoreCase(pl.getFile("configuration.yml").getString("prefix") + "embedVerificar") ) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Sistema de verificação do Servidor")
                    .setDescription("\n**Digite nesse canal**\n```$verificar (seunick)```\n*Exemplo: $verificar Gusttavo13*\nUse seu nick dentro do servidor!")
                    .setColor(new Color(13836580))
                    .addField("Informações úteis", "\nDentro do servidor clique em aceitar para verificar sua conta!\nVocê precisa estar logado no servidor!\nUtilização desse canal ou do comando de forma errada, sujeito a punição!\nSua verificação tem um tempo de **1 Minuto** somente!", false);
            e.getMessage().getChannel().sendMessage(eb.build()).queue(message -> {
                message.pin().queue();
            });
        }
        */
        /*
        if(cmd.equalsIgnoreCase( ThornyaDiscord.PREFIX + "pedircargo") ){
            if(e.getMessage().getChannel().getId().equalsIgnoreCase("794141675693146163")) {
                if(args.length == 1){
                    if(pl.sqliv.hasVerifyID(e.getAuthor().getId())){
                        String nick = pl.sqliv.getNick(e.getAuthor().getId());
                        //String cargo = pl.ess.getUser(nick).getGroup().toLowerCase(Locale.ROOT);
                        //String roleID = pl.cD.cargosID.get(cargo);
                        Map<String, String> roles = new HashMap<String, String>();
                        for (Role role : e.getMember().getRoles()) {
                            roles.put(role.getName().toLowerCase(Locale.ROOT), role.getId());
                            /*if (pl.cD.cargosID.containsKey(cargo)) {
                                if (role.getId().equalsIgnoreCase(pl.cD.cargosID.get(cargo))) {
                                    e.getMessage().getChannel().sendMessage("Você já tem esse cargo").complete();
                                    break;
                                } else {
                                    e.getMessage().getChannel().sendMessage(e.getAuthor().getAsMention() + " Seu cargo " + cargo + " foi adicionado com sucesso!").complete();
                                    e.getGuild().addRoleToMember(e.getMember(), e.getGuild().getRoleById(roleID)).queue();
                                    break;
                                }
                            } else {
                                e.getMessage().getChannel().sendMessage("Cargo superior devem ser adicionados manualmente!").complete();
                                break;
                            }
                        }
                        //arrumar essa merda
                        /*
                        if(!roles.containsKey(cargo)){
                            e.getMessage().getChannel().sendMessage(e.getAuthor().getAsMention() + " Seu cargo " + cargo + " foi adicionado com sucesso!").complete();
                            e.getGuild().addRoleToMember(e.getMember(), e.getGuild().getRoleById(roleID)).queue();
                        }else{
                            e.getMessage().getChannel().sendMessage("Você já tem esse cargo!").complete();
                        }
                    }else{
                        e.getMessage().getChannel().sendMessage("❌ Você não é verificado! ❌").complete();
                    }
                }else{
                    e.getMessage().getChannel().sendMessage("Use **$pedircargo**").complete();
                }
           }else{
                e.getMessage().getChannel().sendMessage("Use o canal " + e.getGuild().getTextChannelById("794141675693146163").getAsMention() + " para o uso desse comando!").complete();
            }

        }
        */

    }

}
