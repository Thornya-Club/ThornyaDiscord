package thornyadiscord.thornyadiscord.verificar.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import thornyadiscord.thornyadiscord.ThornyaDiscord;


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
                                pl.emb.sendEmbedVerificado(p.getName());
                                pl.emb.sendMessageInformando(pl.emb.getUserName(), p.getName(), true);
                            } else {
                                p.sendMessage("§cVocê já é verificado!");
                            }
                        } else {
                            p.sendMessage("§cVocê não nenhuma solicitação!");
                        }
                    } else if (args[0].equalsIgnoreCase("negar")) {
                        if (pl.sqliv.hasNick(p.getName())) {
                            if (!pl.sqliv.hasVerifyNick(p.getName())) {

                                pl.emb.sendEmbedNegado(p.getName());
                                pl.emb.sendMessageInformando(pl.emb.getUserName(), p.getName(), false);
                                pl.sqliv.deletePlayerNick(p.getName());
                            } else {
                                p.sendMessage("§cVocê já é verificado!");
                            }
                        } else {
                            p.sendMessage("§cVocê não nenhuma solicitação!");
                        }
                    } else if (args[0].equalsIgnoreCase("conta")) {

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
