package thornyadiscord.thornyadiscord.discord.Embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;
import thornyadiscord.thornyadiscord.ThornyaDiscord;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Embeds {
    private Member m;
    private final ThornyaDiscord pl;

    public Embeds(ThornyaDiscord main){
        this.pl = main;
    }

    public void sendEmbedNegado(String nickname) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0xFFFF0000, true))
                .setTitle("Verificação não autorizada!")
                .setDescription("Seu pedido foi negado por *{nick}*".replace("{nick}", nickname))
                .setFooter("Verificação do Servidor", "https://minotar.net/avatar/robot");
        //bot.getJDA().getTextChannelById("794042606061223946").sendMessage(eb.build()).complete();
    }
    public String getUserName(){
        return m.getEffectiveName();
    }
    public void sendMessageInformando(String user, String nickname, boolean situacao){
        if(situacao){
            if(Bukkit.getServer().getPlayer(nickname).isOnline()){
                for(int i = 0; i <= 100; i++){
                    Bukkit.getServer().getPlayer(nickname).sendMessage(" ");
                }
                Bukkit.getServer().getPlayer(nickname).sendMessage(" ");
                Bukkit.getServer().getPlayer(nickname).sendMessage("§aSua conta foi vinculada com sucesso!");
                Bukkit.getServer().getPlayer(nickname).sendMessage(" ");
                Bukkit.getServer().getPlayer(nickname).sendMessage("§2Minecraft: §a" + nickname);
                Bukkit.getServer().getPlayer(nickname).sendMessage("§9Discord: §3" + user);
                Bukkit.getServer().getPlayer(nickname).sendMessage(" ");
            }
        }else{
            if(Bukkit.getServer().getPlayer(nickname).isOnline()){
                for(int i = 0; i <= 100; i++){
                    Bukkit.getPlayer(nickname).sendMessage(" ");
                }
                Bukkit.getServer().getPlayer(nickname).sendMessage("§cVocê negou o pedido de §4" + user);
                Bukkit.getServer().getPlayer(nickname).sendMessage(" ");
            }
        }
    }
    public void sendEmbedVerificado(String username){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("✅ Verificado com Sucesso ✅")
                .setDescription("**-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-**")
                .setColor(new Color(4886754))
                .setFooter("Verificação do Servidor", "https://minotar.net/avatar/robot")
                .addField("Minecraft", username, false)
                .addField("Discord", m.getAsMention(), false);
        m.getGuild().getTextChannelById("794042606061223946").sendMessage(eb.build()).complete();
        m.getGuild().addRoleToMember(m.getId(), m.getGuild().getRoleById("794106928145760266")).complete();
        try{
            m.modifyNickname(username).queue();
        }catch (NullPointerException e){
            System.out.println("Nick não pode ser modificado: " + e.getStackTrace());
            System.out.println("Nick não pode ser modificado: " + e.getCause());
            System.out.println("Nick não pode ser modificado: " + e.toString());
        }

    }

    public void sendServerVerify(String nickname, String nametag, String ID, Member membro){
        this.m = membro;
        Bukkit.getOnlinePlayers().forEach(online -> {
            if(online.getName().equals(nickname)) {
                ArrayList<String> messageVerify = new ArrayList<>();
                if (!pl.getFile("configuration.yml").getStringList("discord.verifymessage").isEmpty()){
                    for (String str : pl.getFile("configuration.yml").getStringList("discord.verifymessage")) {
                        if (str.equalsIgnoreCase("$comando")) {
                            try {
                                String cmd  = "tellraw @p [\"\",{\"text\":\"Clique em \",\"color\":\"dark_green\"},{\"text\":\"[\",\"bold\":true,\"color\":\"gray\"},{\"text\":\"Aceitar\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/thornya aceitar\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Deseja aceitar?\",\"color\":\"light_purple\"}}},{\"text\":\"]\",\"bold\":true,\"color\":\"gray\"},{\"text\":\" ou\",\"color\":\"dark_green\"},{\"text\":\" [\",\"bold\":true,\"color\":\"gray\"},{\"text\":\"Negar\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/thornya negar\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Deseja negar?\",\"color\":\"dark_red\"}}},{\"text\":\"]\",\"bold\":true,\"color\":\"gray\"}]";
                                Bukkit.getScheduler().callSyncMethod(pl, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("@p", online.getName()))).get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else {
                            online.sendMessage(str.replace("&", "§").
                                    replace("$nametag", nametag).
                                    replace("$id", ID).
                                    replace("$nickname", online.getName()));
                        }
                    }
                }
            }
        });
    }

}

