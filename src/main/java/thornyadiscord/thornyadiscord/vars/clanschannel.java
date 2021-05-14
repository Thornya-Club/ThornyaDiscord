package thornyaplugin.thornyaplugin.vars;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.HashMap;
import java.util.Map;

public class clanschannel {

    public Map<String, String> clanschannel = new HashMap<String, String>();
    private ThornyaPlugin pl;

    public  clanschannel(ThornyaPlugin main){
        this.pl = main;
        clansChannelUpdate();
    }

    public void clansChannelUpdate(){
        if(!pl.bot.getJDA().getGuildById("594364577618329620").getCategoryById("792965102545535007").getChannels().isEmpty()){
            pl.bot.getJDA().getGuildById("594364577618329620").getCategoryById("792965102545535007").getTextChannels().forEach(cchannels -> {
                if(!clanschannel.containsKey(cchannels.getId())){
                    System.out.println("Adicionado um clan - " + cchannels.getId());
                    clanschannel.put(cchannels.getId(), "");
                }
            });
        }
    }
}
