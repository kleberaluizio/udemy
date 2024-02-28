package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Chapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookStoreService
{
	private Connection connection = null;
	public void persistObjectGraph(Book book)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root",
				"root");

			PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO PUBLISHER (CODE, PUBLISHER_NAME) VALUES (?,?)");
			stmt.setString(1, book.getPublisher().getCode());
			stmt.setString(2, book.getPublisher().getName());

			stmt.close();

			stmt = connection.prepareStatement(
				"INSERT INTO BOOK (ISBN, BOOK_NAME, PUBLISHER_CODE) VALUES (?,?,?)");
			stmt.setString(1, book.getIsbn());
			stmt.setString(2, book.getName());
			stmt.setString(3, book.getPublisher().getCode());

			stmt.close();

			stmt = connection.prepareStatement(
				"INSERT INTO CHAPTER (BOOK_ISBN, CHAPTER_NUM,TITLE) VALUES (?,?,?)");
			for(Chapter chapter : book.getChapters()){
				stmt.setString(1, book.getIsbn());
				stmt.setInt(2, chapter.getChapterNumber());
				stmt.setString(3, chapter.getTitle());
				stmt.executeUpdate();
			}

			stmt.close();

		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}

	}

	public Book retrieveObjectGraph(String book)
	{
		return new Book();
	}
}
