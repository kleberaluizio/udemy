package com.bookstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Chapter
{
	private String title;
	private Integer chapterNumber;

	public Chapter(String title, Integer chapterNumber)
	{
		this.title = title;
		this.chapterNumber = chapterNumber;
	}

}
