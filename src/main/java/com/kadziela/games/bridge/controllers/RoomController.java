package com.kadziela.games.bridge.controllers;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.kadziela.games.bridge.model.Player;
import com.kadziela.games.bridge.service.RoomService;

@Controller
public class RoomController 
{
	private static final Logger logger = LogManager.getLogger(RoomController.class);

	@Autowired RoomService roomService;
	
	@MessageMapping("/room")
	@SendTo("/topic/room")
	public Collection<Player> enter(Player player)
	{
		logger.debug(String.format("Room Controller received the request for %s to enter the room", player.toString()));
	    roomService.addPlayer(player);
	    logger.debug("Players in the room: ");
	    for (Player p:roomService.getPlayers()) {logger.debug(String.format(", %s",p.getName()));}
		return roomService.getPlayers();
	}
}