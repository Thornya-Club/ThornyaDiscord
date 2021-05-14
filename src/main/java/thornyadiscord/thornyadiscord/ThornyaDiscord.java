package thornyadiscord.thornyadiscord;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import org.bukkit.plugin.java.JavaPlugin;

import thornyadiscord.thornyadiscord.clans.Clan;
import thornyadiscord.thornyadiscord.clans.commands.ClanCommand;
import thornyadiscord.thornyadiscord.discord.BotManager;
import thornyadiscord.thornyadiscord.discord.Embeds.Embeds;
import thornyadiscord.thornyadiscord.verificar.SQLite.SQLite;
import thornyadiscord.thornyadiscord.verificar.commands.Verificar;

import java.io.*;

public final class ThornyaDiscord extends JavaPlugin {

    public SQLite sqliv;
    private File file = null;
    private FileConfiguration fileC = null;
    private File translate = null;
    private FileConfiguration fctranslate = null;
    //public SimpleClans sc;
    public BotManager bot;
    //public Essentials ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
    //Plugin clanPL = getServer().getPluginManager().getPlugin("SimpleClans");
    public Clan clan;
    public static String PREFIX;
    public Embeds emb;

    @Override
    public void onEnable() {
        carregarconfigs();
        PREFIX = getFile("configuration.yml").getString("prefix");
        //this.sqli = new SQLite(this);
        emb = new Embeds(this);
        bot = new BotManager(this);
        clan = new Clan(this);
        sqliv = new SQLite(this);
        registrarComandos();
    }

    @Override
    public void onDisable() {
        bot.getJDA().shutdown();
        bot.getJDA().shutdownNow();
    }
    public void registrarComandos(){
        registrarComando("clans", new ClanCommand(this));
        registrarComando("verificar", new Verificar(this));
    }
    public void registrarComando(String nome, CommandExecutor comando) {
        getCommand(nome).setExecutor(comando);
    }
    public void registrarListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
    public void carregarconfigs(){
        criarConfig("configuration.yml");
        criarConfigTranslate();
        //criarConfig("staffs.yml");
    }
    public void criarConfig(String nomedoarquivo){
        File fileVerifica = new File(getDataFolder(), nomedoarquivo);
        if(!fileVerifica.exists()){ saveResource(nomedoarquivo, false);}
    }
    public FileConfiguration getFile(String nomedoarquivo){
        if(this.fileC == null){
            this.file = new File(getDataFolder(), nomedoarquivo);
            this.fileC = YamlConfiguration.loadConfiguration(this.file);
        }
        return this.fileC;

    }

    ///////////////////\\/\/\/\/\/\/\/\/\/\/Config Translate///////////////////////
    public void criarConfigTranslate(){
        File fileVerifica = new File(getDataFolder(), "translate.yml");
        if(!fileVerifica.exists()){ saveResource("translate.yml", false);}
    }
    public FileConfiguration translate(){
        if(this.fctranslate == null){
            this.translate = new File(getDataFolder(), "translate.yml");
            this.fctranslate = YamlConfiguration.loadConfiguration(this.translate);
        }
        return this.fctranslate;

    }
    public void reloadTranslate(){
        if(this.translate == null){
            this.translate = new File(getDataFolder(), "translate.yml");
        }
        this.fctranslate = YamlConfiguration.loadConfiguration(this.translate);
        if(this.fctranslate != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.translate);
            this.fctranslate.setDefaults(defaults);
        }
    }
    //////////////////////////////////////////////////////////////////////////\\/\/\/\/\//\/\
    public void reloadAll(){
        saveConfig();
        reloadConfig("configuration.yml");
        reloadTranslate();
       //reloadConfig("staffs.yml");
        //reloadConfig("jogadoresV.yml");
    }
    public void saveConfig(){

        try {
            getFile("configuration.yml").save(this.file);
            translate().save(this.translate);
            //getFile("staffs.yml").save(this.file);
            //getFile("jogadoresV.yml").save(this.file);
        }catch (Exception e){

        }
    }

    public void reloadConfig(String nomedoarquivo){
        if(this.file == null){
            this.file = new File(getDataFolder(), nomedoarquivo);
        }
        this.fileC = YamlConfiguration.loadConfiguration(this.file);
        if(this.fileC != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.file);
            this.fileC.setDefaults(defaults);
        }
    }
}
