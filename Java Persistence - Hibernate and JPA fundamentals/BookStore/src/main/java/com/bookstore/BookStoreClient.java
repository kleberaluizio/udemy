package com.bookstore;

import com.bookstore.entity.Book;
import com.bookstore.entity.Chapter;
import com.bookstore.entity.Publisher;
import com.bookstore.service.BookStoreService;
import java.util.ArrayList;
import java.util.List;

public class BookStoreClient
{
	public static void main(String[] args){
		BookStoreService service = new BookStoreService();


		Publisher publisher = new Publisher("MANN", "Manning Publications CO.");

		Book book = new Book("9781617290459", "Java Persistence with Hibernate, Second Edition", publisher);

		List<Chapter> chapters = new ArrayList<>();
		Chapter chapter1 = new Chapter("Introducion JPA and Hibernate",1);
		chapters.add(chapter1);
		Chapter chapter2 = new Chapter("Introducion JPA and Hibernate",2);
		chapters.add(chapter2);

		book.setChapters(chapters);

		service.persistObjectGraph(book);

	}
}
