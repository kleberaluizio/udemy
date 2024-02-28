package com.bookstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Publisher
{
	private String code;
	private String name;

	public Publisher(String code, String name)
	{
		this.code = code;
		this.name = name;
	}
}
