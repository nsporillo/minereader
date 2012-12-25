package net.milkycraft.skullwall.commands;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import static org.bukkit.ChatColor.*;

import net.milkycraft.Book;
import net.milkycraft.MineReader;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;

public class DownloadCommand extends BaseCommand {

	public DownloadCommand(MineReader plugin) {
		super(plugin);
		super.setName("download");
		super.addUsage("[author]", "[title]",
				"Downloads book from minewriter.net");
		super.setPermission("minereader.download");
	}

	@Override
	public void runCommand(final CommandSender sender, final List<String> args) {
		if (!this.checkPermission(sender)) {
			this.noPermission(sender);
			return;
		}
		if (sender instanceof Player) {
			final Player p = ((Player) sender);
			if (args.size() == 0) {			
			}else if(args.size() == 1) {
				download(p, args.get(0));
			} else if (args.size() == 2) {
				download(p, args);
			}
		}
	}
	public void download(final Player p, final String id) {
		p.sendMessage(GREEN + "Downloading book ");
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

			@Override
			public void run() {
				Book book = null;
				URL url;
				try {
					url = new URL("http://minewriter.net/query.php?id="
							+ URLEncoder.encode(id, "UTF-8") + "&type=JSON");
					book = new Book(url.toString());
				} catch (MalformedURLException e) {
					p.sendMessage(RED
							+ "Error: Minewriter cant be reached");
					return;
				} catch (UnsupportedEncodingException e) {
					p.sendMessage(RED
							+ "Error: Encoding unsupported!");
					return;
				} catch (Exception e) {
					p.sendMessage(RED
							+ "Error: That book could not be found(ExE)");
					return;
				}
				synchronized (this) {
					p.getInventory().addItem(book);
				}
			}
		});

	}
	
	public void download(final Player p, List<String> args) {
		final String author = args.get(0);
		final String title = args.get(1);
		p.sendMessage(GREEN + "Downloading book ");
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

			@Override
			public void run() {
				Book book = null;
				URL url;
				try {
					url = new URL("http://minewriter.net/query.php?author="
							+ URLEncoder.encode(author, "UTF-8") + "&title="
							+ URLEncoder.encode(title, "UTF-8") + "&type=JSON");
					book = new Book(url.toString());
				} catch (MalformedURLException e) {
					p.sendMessage(RED
							+ "Error: That book could not be found (MUE)");
					return;
				} catch (UnsupportedEncodingException e) {
					p.sendMessage(RED
							+ "Error: That book could not be found (UEE)");
					return;
				} catch (JSONException e) {
					p.sendMessage(RED
							+ "Error: That book could not be found (JSONE)");
					return;
				} catch (Exception e) {
					p.sendMessage(RED
							+ "Error: That book could not be found(ExE)");
					return;
				}
				synchronized (this) {
					p.getInventory().addItem(book);
				}
			}
		});

	}
}
