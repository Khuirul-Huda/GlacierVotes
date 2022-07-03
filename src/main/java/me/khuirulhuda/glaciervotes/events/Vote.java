package me.khuirulhuda.glaciervotes.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.*;
import java.awt.Color;
import me.khuirulhuda.glaciervotes.Main;

public class Vote implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {

		final boolean shouldNotice = Main.getInstance().getConfig().getBoolean("notice");
		final String nvmsg = Main.getInstance().getConfig().getString("nvmessage");
		final boolean debugmode = Main.getInstance().getConfig().getBoolean("debug");
		if (debugmode) {
			debug("Player Joined");
		}

		final String apikey = Main.getInstance().getConfig().getString("apikey");

		if (debugmode) {
			debug("Using API KEY : " + apikey);
		}

		final Player player = event.getPlayer();
		String name = player.getName();
		if (debugmode) {
			debug("Player Joined " + name);
		}

		boolean spasi = name.contains(" ");
		String fname = name;
		String gname = name;

		if (spasi) {
			int sploc = gname.indexOf(" ");
			gname = gname.substring(0, sploc);
		} else {
		}

		if (spasi) {
			fname = name.replace(" ", "%20");
			// String api =
			// "https://minecraftpocket-servers.com/api/?object=votes&element=claim&key="+apikey+"&username="+fname;
		}
		// final String httperrormsg = "Internal Error has occured! Try Again Later";
		final String api = "https://minecraftpocket-servers.com/api/?object=votes&element=claim&key=" + apikey
				+ "&username=" + fname;
		final List<String> listcmd = Main.getInstance().getConfig().getStringList("commands");
		final String gnamee = gname;
		final String fnamee = fname;
		// Webhook Variables
                // Drop webhook
		//final String whservername = Main.getInstance().getConfig().getString("servername");
		//final String whfooter = Main.getInstance().getConfig().getString("footer");
		//final String whthumbnail = Main.getInstance().getConfig().getString("thumbnail");
		//final String whusername = Main.getInstance().getConfig().getString("username");
		//final String whdescription = Main.getInstance().getConfig().getString("description").replaceAll("%player%",gnamee);
		//final String whavatar = Main.getInstance().getConfig().getString("avatarurl");
		//final String whurl = Main.getInstance().getConfig().getString("webhook");
		//final boolean whenabled = !whurl.equalsIgnoreCase("null");

		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {

			try {
				URL url = new URL(api);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();// start

				int status = http.getResponseCode();
				String httpresponse = http.getResponseMessage();// end

				if (debugmode) {
					debug("HTTP Response Code:" + status);
					debug("HTTP Response:" + httpresponse);
				}

				int responseCode = http.getResponseCode();
				InputStream inputStream;
				if (200 <= responseCode && responseCode <= 299) {
					inputStream = http.getInputStream();
				} else {
					inputStream = http.getErrorStream();
				}

				BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

				StringBuilder respons = new StringBuilder();
				String currentLine;
				while ((currentLine = in.readLine()) != null)
					respons.append(currentLine);
				in.close();

				String response = respons.toString();

				if (debugmode) {
					debug("Response: " + response);
				}

				if (200 <= responseCode && responseCode <= 299) {
					if (response.contains("1")) {

						for (String command : listcmd) {

							if (spasi) {
								Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

									Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
											command.replace("%player%", gnamee));
								});
							} else {
								Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

									Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
											command.replace("%player%", player.getName()));
								});
							}
						}
						String claimapiurl = "http://minecraftpocket-servers.com/api/?action=post&object=votes&element=claim&key="
								+ apikey + "&username=" + fnamee;

						try {
							URL urll = new URL(claimapiurl);
							HttpURLConnection httpp = (HttpURLConnection) urll.openConnection();// start

							int statuss = httpp.getResponseCode();
							String responsee = httpp.getResponseMessage();// end
							if (debugmode) {
								debug("POST REQUEST CODE:" + statuss);
								debug("POST RESPONSE:" + responsee);
							}
							httpp.disconnect();
						} catch (IOException p) {
							Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

								String logp = p.toString();
								Main.getInstance().getLogger().severe(logp);
							});
						}
						// W
					}
					if (response.contains("2")) {
						// voted claimed
					}
					if (response.contains("0")) {
						// not found || no votes
						if (shouldNotice) {
							player.sendMessage(ChatColor.YELLOW + nvmsg);
						}
					}
				} else {
					Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

						String logme = "Error" + status + response;
						Main.getInstance().getLogger().severe(logme);
					});
				}
				http.disconnect();
			} catch (IOException q) {
				Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

					String qstr = q.toString();
					Main.getInstance().getLogger().severe(qstr);
				});
			}
		});// async?
	}

	public void debug(String debugstr) {
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			if (Main.getInstance().getConfig().getBoolean("debug"))
				Main.getInstance().getLogger().warning(ChatColor.WHITE + debugstr);
		});
	}
}
