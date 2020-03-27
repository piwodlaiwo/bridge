package com.kadziela.games.bridge.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.kadziela.games.bridge.model.Message;

@Controller
public class ChatController 
{
	private static final Logger logger = LogManager.getLogger(ChatController.class);

	@MessageMapping("/chat")
	@SendTo("/topic/chat")
	public Message chat(Message message)
	{
		logger.debug(String.format("Chat Controller received the message %s", message));
		return new Message(message.getContent());
	}
}