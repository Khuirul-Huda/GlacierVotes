package me.khuirulhuda.glaciervotes.events;

import me.khuirulhuda.glaciervotes.Main;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import java.net.HttpURLConnection;
import org.bukkit.entity.Player;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import java.net.MalformedURLException;
import org.bukkit.configuration.InvalidConfigurationException;



public class Vote implements Listener {
  

private String apikey = Main.getInstance().getConfig().getString("apikey");
@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

  String api = "https://minecraftpocket-servers.com/api/?object=votes&element=claim&key="+apikey+"&username="+name;
try {
URL url = new URL(api);
HttpURLConnection http = (HttpURLConnection)url.openConnection();
} catch (MalformedURLException ex) {
  String logm = ex.toString();
  Main.getInstance().getLogger().severe(logm);
}
int status = http.getResponseCode();
String response = http.getResponseMessage();
if ( status == 200 ) {
  if ( response == "1") {
    //vote not claimed
    player.sendMessage("Terimakasih sudah vote");
    claimvote(name);
    //runCommand();
  } 
  if ( response == "2") {
    //voted claimed
    
  }
  if ( response == "0") {
    // not found
    player.sendMessage("Halo, Kamu Belum Vote silakan vote di vote renderycrafty.net dan dapatkan hadiah");
  }
} else {
  String logme = "Error"+status+response;
  Main.getInstance().getLogger().severe(logme);
}
http.disconnect();
}

public void claimvote(String name){
  String claimapiurl = "http://minecraftpocket-servers.com/api/?action=post&object=votes&element=claim&key="+apikey+"&username="+name;
  try {
  URL url = new URL(claimapiurl);
HttpURLConnection http = (HttpURLConnection)url.openConnection();

int status = http.getResponseCode();
String response = http.getResponseMessage();
http.disconnect();
} catch (MalformedURLException p) {
  String logp = p.toString();
  Main.getInstance().getLogger().severe(logp);
}

}


}