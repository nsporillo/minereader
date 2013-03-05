package net.minewriter.commands;

import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import net.minewriter.Book;
import net.minewriter.MineReader;
import static net.minewriter.MineReader.getLibrary;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DownloadCommand extends BaseCommand {

	public DownloadCommand(MineReader plugin) {
		super(plugin);
		super.setName("download");
		super.addUsage("[author]", "[title]",
				"Downloads book from author + title");
		super.addUsage("[id]", null, "Downloads book from id");
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
				sender.sendMessage(RED + "You must provide a book id atleast");
			} else if (args.size() == 1) {
				download(p, args.get(0));
			} else if (args.size() == 2) {
				download(p, args);
			}
		}
	}

	public void download(final Player p, final String id) {
		Book b = get(Integer.parseInt(id));
		if (b != null) {
			if (b.isBanned(plugin)) {
				p.sendMessage(RED + "Sorry that book was 'banned'");
				p.sendMessage(RED + "It was blacklisted in the config");
				return;
			}
			p.sendMessage(GREEN + "Fetched book from cache, there you go!");
			p.getInventory().addItem(b);
			return;
		}
		if (plugin.getConf().cache) {
			p.sendMessage(GREEN + "Book not yet cached, downloading now...");
		} else {
			p.sendMessage(GREEN + "Downloading book from minewriter");
		}

		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

			@Override
			public void run() {
				Book book = null;
				URL url;
				try {
					url = new URL("http://minewriter.net/query.php?id="
							+ URLEncoder.encode(id, "UTF-8") + "&type=JSON");
					plugin.debug(url.toExternalForm());
					book = new Book(url.toString());
				} catch (Exception e) {
					p.sendMessage(RED + "Error: Book was not found");
					plugin.getLogger().warning(
							"Book " + id + " could not be found!");
					plugin.getLogger().warning(e.getLocalizedMessage());
					return;
				}
				give(p, book);
			}
		});
	}

	public void download(final Player p, List<String> args) {
		final String author = args.get(0).replace("~", " ");
		final String title = args.get(1).replace("~", " ");
		final String a = author.replace(" ", "%20");
		final String t = title.replace(" ", "%20");
		Book b = get(author, title);
		if (b != null) {
			if (b.isBanned(plugin)) {
				p.sendMessage(RED + "Sorry that book was 'banned'");
				p.sendMessage(RED + "It was blacklisted in the config");
				return;
			}
			p.sendMessage(GREEN + "Fetched book from cache, there you go!");
			p.getInventory().addItem(b);
			return;
		}
		if (plugin.getConf().cache) {
			p.sendMessage(GREEN + "Book not yet cached, downloading now...");
		} else {
			p.sendMessage(GREEN + "Downloading book from minewriter");
		}
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

			@Override
			public void run() {
				Book book = null;
				URL url;
				try {
					url = new URL("http://minewriter.net/query.php?author=" + a
							+ "&title=" + t + "&type=JSON");
					plugin.debug(url.toExternalForm());
					book = new Book(url.toString());
				} catch (Exception e) {
					p.sendMessage(RED + "Error: Book was not found");
					plugin.getLogger().warning(
							"Book could not be found! - Author: " + author
									+ " Title: " + title);
					plugin.getLogger().warning(e.getLocalizedMessage());
					return;
				}
				give(p, book);
			}
		});
	}

	public Book get(int id) {
		return getLibrary().get(id);
	}

	public Book get(String author, String title) {
		return getLibrary().get(author, title);
	}

	public void add(Book b) {
		if (!plugin.getConf().cache) {
			return;
		}
		if (getLibrary().add(b)) {
			if (b.isBanned(plugin)) {
				plugin.info("Cached banned book " + b.getTitle() + " by "
						+ b.getAuthor());
			} else {
				plugin.info("Cached book '" + b.getTitle() + "' by "
						+ b.getAuthor());
			}
		}
	}

	public void give(Player p, Book b) {
		if (!b.isBanned(plugin)) {
			p.getInventory().addItem(b);
		} else {
			p.sendMessage(RED + "Sorry that book was 'banned'");
			p.sendMessage(RED + "It was blacklisted in the config");
		}
		add(b);
	}
}
