package com.analysis.po;

import java.io.Serializable;

public class Label implements Serializable{
	private long id;
	private String text;

	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text = text;
	}

}
