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
import org.bukkit.ChatColor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import org.bukkit.Bukkit;

public class Vote implements Listener {

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
      
       boolean debugmode = Main.getInstance().getConfig().getBoolean("debug");
       if (debugmode) {
         debug("Player Joined");
       }
       
      String apikey = Main.getInstance().getConfig().getString("apikey");
      
      if (debugmode) {
        debug("Using API KEY : "+apikey);
      }
      
      Player player = event.getPlayer();
      String name = player.getName();
      if (debugmode) {
        debug("Player Joined "+name);
      }

    boolean spasi = name.contains(" ");
if (spasi) {
  String fname = name.replace(" ", "%20");
  
  String api = "https://minecraftpocket-servers.com/api/?object=votes&element=claim&key="+apikey+"&username="+fname;
} else {

      String api = "https://minecraftpocket-servers.com/api/?object=votes&element=claim&key="+apikey+"&username="+name;
      String fname = name;
}
    //idk
try {
URL url = new URL(api);
HttpURLConnection http = (HttpURLConnection)url.openConnection();//start 

int status = http.getResponseCode();
String httpresponse = http.getResponseMessage();//end

if (debugmode) {
  debug("HTTP Response Code:"+status);
  debug("HTTP Response:"+httpresponse);
}

int responseCode = http.getResponseCode();
    InputStream inputStream;
    if (200 <= responseCode && responseCode <= 299) {
        inputStream = http.getInputStream();
    } else {
        inputStream = http.getErrorStream();
    }

    BufferedReader in = new BufferedReader(
        new InputStreamReader(
            inputStream));

    StringBuilder respons = new StringBuilder();
    String currentLine;
    while ((currentLine = in.readLine()) != null) 
        respons.append(currentLine);
    in.close();
    
    String response = respons.toString();

    if (debugmode){
      debug("Response: "+response);
    }

if ( 200 <= responseCode && responseCode <= 299 ) {
  if ( response.contains("1")) {
    
    for (String command : Main.getInstance().getConfig().getStringList("commands")) {
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
}
    player.sendMessage("Terimakasih sudah vote");
    String claimapiurl = "http://minecraftpocket-servers.com/api/?action=post&object=votes&element=claim&key="+apikey+"&username="+fname;
 
  try {
  URL urll = new URL(claimapiurl);
HttpURLConnection httpp = (HttpURLConnection)urll.openConnection();//start

int statuss = httpp.getResponseCode();
String responsee = httpp.getResponseMessage();//end
if (debugmode){
  debug("POST REQUEST CODE:"+statuss);
  debug("POST RESPONSE:"+responsee);
} 
httpp.disconnect();
} catch (IOException p) {
  String logp = p.toString();
  Main.getInstance().getLogger().severe(logp);
}
    //runCommand();
  } 
  if ( response.contains("2")) {
    //voted claimed
    
  }
  if ( response.contains("0")) {
    // not found
    player.sendMessage(ChatColor.YELLOW+"Halo, Kamu Belum Vote silakan vote di vote.renderycrafty.net dan dapatkan hadiah");
  }
} else {
  String logme = "Error"+status+response;
  Main.getInstance().getLogger().severe(logme);
}
http.disconnect();
} catch(IOException q){
  String qstr = q.toString();
  Main.getInstance().getLogger().severe(qstr);
}
      
    }

public void debug(String debugstr) {
  Main.getInstance().getLogger().warning(ChatColor.WHITE+debugstr);
}
  
}
