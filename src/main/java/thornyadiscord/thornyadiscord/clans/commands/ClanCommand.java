package thornyadiscord.thornyadiscord.clans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import thornyadiscord.thornyadiscord.ThornyaDiscord;


public class ClanCommand implements CommandExecutor {
    private ThornyaDiscord pl;

    public ClanCommand(ThornyaDiscord main){
        this.pl = main;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender snd, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        /*
        Player p = (Player)snd;
        if (cmd.getName().equalsIgnoreCase("clans")) {
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("discord")){
                    if(pl.sc.getClanManager().getClanPlayer(p) != null){
                        if(pl.sc.getClanManager().getClanPlayer(p).isLeader()){
                            if(pl.sqliv.hasVerifyNick(p.getName())){
                                pl.clan.buyChannel(pl.sc.getClanManager().getClanPlayer(p).getTag(), pl.sqliv.getIdDiscord(p.getName()), p.getName());
                            }else{
                                p.sendMessage("§cVocê não é verificado no Discord");
                            }
                        }else{
                            p.sendMessage("§cVocê não é lider!");
                        }
                    }else{
                        p.sendMessage("§cVocê não tem um clan!");
                    }
                    //id pripri - 402371896248893440
                    //pl.clan.buyChannel(pl.sc.getClanManager().getClanPlayer(p.getUniqueId()).getClan().getTag(), "402371896248893440", p.getName());
                    //p.sendMessage("§cVocê comprou um canal/voz no discord!");
                }
                // if(args[0].equalsIgnoreCase("rank")){
                //  p.sendMessage("getRankDisplay() = " +  pl.sc.getClanManager().getClanPlayer(p.getName()).getRankDisplayName());
                //}
            }
        }

         */


        return false;
    }
}
