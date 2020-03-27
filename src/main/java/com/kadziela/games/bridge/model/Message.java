package com.kadziela.games.bridge.model;

public class Message 
{
	private String content;
	public Message() {}
	public Message(String sContent) {content = sContent;}
	public String getContent() {return content;}
	public void setContent(String sContent) {content = sContent;}
	@Override public String toString() {return "Message [content=" + content + "]";}	
}