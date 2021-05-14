ackage thornyaplugin.thornyaplugin.vars;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.HashMap;
import java.util.Map;

public class cargosDiscord {

    public Map<String, String> cargosID = new HashMap<String, String>();
    private ThornyaPlugin pl;

    public cargosDiscord (ThornyaPlugin main){
        this.pl = main;
        cargosDiscordUpdate();
    }

    public void cargosDiscordUpdate(){
        //aprendiz
        cargosID.put("aprendiz", "766317839933767710");
        //mestre
        cargosID.put("mestre", "795478318623096833");
        //vaprendiz
        cargosID.put("vaprendiz", "734915024404873279");
        //vpaprendiz
        cargosID.put("vpaprendiz", "734913720811257966");
        //vMestre
        cargosID.put("vmestre", "");
        //vpmestre
        cargosID.put("vpmestre", "766408694300540938");
    }

}
