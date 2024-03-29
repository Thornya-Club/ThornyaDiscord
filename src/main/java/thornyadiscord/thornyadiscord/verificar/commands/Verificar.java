package thornyadiscord.thornyadiscord.verificar.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import thornyadiscord.thornyadiscord.ThornyaDiscord;
import thornyadiscord.thornyadiscord.discord.Embeds.Embeds;


public class Verificar implements CommandExecutor {
    private final ThornyaDiscord pl;

    public Verificar(ThornyaDiscord main) {
        this.pl = main;
    }

    @Override
    public boolean onCommand(CommandSender snd, Command cmd, String s, String[] args) {

        if (cmd.getName().equalsIgnoreCase("verificar")) {
            if (snd instanceof Player) {
                Player p = (Player)snd;
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("aceitar")) {
                        if (pl.sqliv.hasNick(p.getName())) {
                            if (!pl.sqliv.hasVerifyNick(p.getName())) {
                                String idDiscord = pl.sqliv.getIdDiscord(p.getName());
                                pl.sqliv.updatePlayerVerify(p.getName());
                                Embeds.sendEmbedVerificado(p.getName(), pl);
                                Embeds.sendMessageInformando(pl.emb.getUserName(p.getName()), p.getName(), true);
                            } else {
                                p.sendMessage("§cVocê já é verificado!");
                            }
                        } else {
                            p.sendMessage("§cVocê não nenhuma solicitação!");
                        }
                    } else if (args[0].equalsIgnoreCase("negar")) {
                        if (pl.sqliv.hasNick(p.getName())) {
                            if (!pl.sqliv.hasVerifyNick(p.getName())) {
                                Embeds.sendEmbedNegado(p.getName());
                                Embeds.sendMessageInformando(pl.emb.getUserName(p.getName()), p.getName(), false);
                                pl.sqliv.deletePlayerNick(p.getName());
                            } else {
                                p.sendMessage("§cVocê já é verificado!");
                            }
                        } else {
                            p.sendMessage("§cVocê não nenhuma solicitação!");
                        }
                    } else if (args[0].equalsIgnoreCase("conta")) {
                        //fazer
                    }
                } else {
                    p.sendMessage("§cUse /verificar conta");
                }
            }else{
                Bukkit.getConsoleSender().sendMessage("§cComando permitido somente para jogadores");
            }
        }

        return false;
    }
}
