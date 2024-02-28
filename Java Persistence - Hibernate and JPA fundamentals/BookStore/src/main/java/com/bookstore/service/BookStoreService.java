package com.bookstore.service;

import com.bookstore.entity.Book;
import java.sql.Connection;
import java.sql.DriverManager;

public class BookStoreService
{
	private Connection connection = null;
	public void persistObjectGraph(Book book)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","password");

		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

	}

	public Book retrieveObjectGraph(String book)
	{
		return new Book();
	}
}
