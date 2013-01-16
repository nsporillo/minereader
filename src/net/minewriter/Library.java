package net.minewriter;

import java.util.HashSet;
import java.util.Set;

public class Library {	
	private Set<Book> library = new HashSet<Book>();

	public boolean add(Book b) {
		return library.add(b);
	}
	
	public boolean remove(Book b) {
		return library.remove(b);
	}
	
	public Book get(int id) {
		for(Book b : library) {
			if(b.getID() == id) {
				return b;
			}
		}
		return null;
	}
	
	public Book get(String author, String title) {
		for(Book b : library) {
			if(b.getAuthor().equalsIgnoreCase(author) && b.getTitle().equalsIgnoreCase(title)) {
				return b;
			}
		}
		return null;
	}
	
	
	public Set<Book> getBooks() {
		return library;
	}
}
