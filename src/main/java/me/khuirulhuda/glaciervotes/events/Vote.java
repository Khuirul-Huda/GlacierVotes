package me.khuirulhuda.glaciervotes.events;

import me.khuirulhuda.glaciervotes.Main;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import java.net.*;



public class Vote implements Listener {
  

private String apikey = Main.getInstance().config.getString("apikey");
@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        
}
public void checkvote(String name) {
  String api = "https://minecraftpocket-servers.com/api/?object=votes&element=claim&key="+apikey+"&username="+name;
URL url = new URL(api);
HttpURLConnection http = (HttpURLConnection)url.openConnection();
(http.getResponseCode() + " " + http.getResponseMessage());
int status = http.getResponseCode();
int response = http.getResponseMessage();

if ( status == 200 ) {
  if ( response.toString() == "1") {
    //voted not claimed
    player.sendMessage("Terimakasih sudah vote");
  } 
  if ( response.toString() == "2") {
    //voted claimed
    
  }
  if ( response.toString() == "0") {
    // not found
    player.sendMessage("Halo, Kamu Belum Vote silakan vote di vote renderycrafty.net dan dapatkan hadiah")
  }
} else {
  Main.getInstance().getLoggger().high("Error"+status+response);
}
http.disconnect();
}
}