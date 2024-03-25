package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Chapter;
import com.bookstore.entity.Publisher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookStoreService
{
	private Connection connection = null;

	public void persistObjectGraph(Book book)
	{
		try
		{

			connection = getConnection();

			persistPublisher(connection, book);
			persistBook(connection, book);
			persistChapters(connection, book);

		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}

	}
	private void persistPublisher(Connection connection, Book book) throws SQLException
	{
		try (PreparedStatement stmt = connection.prepareStatement(
			"INSERT INTO PUBLISHER (CODE, PUBLISHER_NAME) VALUES (?,?)"))
		{
			stmt.setString(1, book.getPublisher().getCode());
			stmt.setString(2, book.getPublisher().getName());
			stmt.executeUpdate();
		}

	}
	private void persistBook(Connection connection, Book book) throws SQLException
	{
		try (PreparedStatement stmt = connection.prepareStatement(
			"INSERT INTO BOOK (ISBN, BOOK_NAME, PUBLISHER_CODE) VALUES (?,?,?)"))
		{
			stmt.setString(1, book.getIsbn());
			stmt.setString(2, book.getName());
			stmt.setString(3, book.getPublisher().getCode());
			stmt.executeUpdate();
		}
	}
	private void persistChapters(Connection connection, Book book) throws SQLException
	{
		try(PreparedStatement stmt = connection.prepareStatement(
			"INSERT INTO CHAPTER (BOOK_ISBN, CHAPTER_NUM,TITLE) VALUES (?,?,?)"))
		{
			for (Chapter chapter : book.getChapters())
			{
				stmt.setString(1, book.getIsbn());
				stmt.setInt(2, chapter.getChapterNumber());
				stmt.setString(3, chapter.getTitle());
				stmt.executeUpdate();
			}
		}
	}

	public Book retrieveObjectGraph(String isbn)
	{
		Book book = null;
		try
		{
			connection = getConnection();
			book = findBookByISBN(connection, isbn);

		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}
		return book;
	}
	private Book findBookByISBN(Connection connection, String isbn) throws SQLException
	{

		Book book = new Book();
		try(PreparedStatement stmt = connection.prepareStatement(
			"SELECT * FROM BOOK, PUBLISHER WHERE BOOK.PUBLISHER_CODE = PUBLISHER.CODE AND BOOK.ISBN = ?");)
		{
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();

			if (rs.next())
			{
				book.setIsbn(rs.getString("ISBN"));
				book.setName(rs.getString("BOOK_NAME"));

				Publisher publisher = new Publisher();
				publisher.setCode(rs.getString("CODE"));
				publisher.setName(rs.getString("PUBLISHER_NAME"));
				book.setPublisher(publisher);
			}
			rs.close();

			book.setChapters(findBookChapters(connection, isbn));
		}

		return book;
	}
	private List<Chapter> findBookChapters(Connection connection, String isbn){

		List<Chapter> chapters = new ArrayList<>();
		try(PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CHAPTER WHERE BOOK_ISBN = ?");){

		stmt.setString(1, isbn);
		ResultSet rs = stmt.executeQuery();

		while (rs.next())
		{
			Chapter chapter = new Chapter();
			chapter.setTitle(rs.getString("TITLE"));
			chapter.setChapterNumber(rs.getInt("CHAPTER_NUM"));
			chapters.add(chapter);
		}
		rs.close();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		return chapters;
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root",
			"root");
		return connection;
	}
}
