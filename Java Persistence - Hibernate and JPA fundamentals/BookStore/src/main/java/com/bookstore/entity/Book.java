package com.bookstore.entity;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book
{
	private String isbn;
	private String name;
	private Publisher publisher;
	private List<Chapter> chapters;

	public Book(String isbn, String name, Publisher publisher)
	{
		this.isbn = isbn;
		this.name = name;
		this.publisher = publisher;
	}
}
