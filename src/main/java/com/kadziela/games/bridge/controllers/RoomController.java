package com.kadziela.games.bridge.controllers;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import com.kadziela.games.bridge.model.Player;
import com.kadziela.games.bridge.service.RoomService;

@Controller
public class RoomController 
{
	private static final Logger logger = LogManager.getLogger(RoomController.class);

	@Autowired private RoomService roomService;
	@Autowired private SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/room/enter")
	@SendTo("/topic/room")
	public Collection<Player> enter(String name)
	{
		logger.debug(String.format("Room Controller received the request for %s to enter the room", name));
	    try
	    {
			Player player = roomService.addPlayer(name);
		    logger.debug("Players in the room: ");
		    for (Player p:roomService.getPlayers()) {logger.debug(String.format(", %s",p.getName()));}
			
//		    messagingTemplate.convertAndSendToUser(name, "/queue/private", "Hello new player "+player.toString()); - I can't get this to work
		    messagingTemplate.convertAndSend("/queue/private/"+name, "Hello new player "+player.toString()); // so this is a workaround for now
	    }
	    catch (IllegalArgumentException iae)
	    {
	    	logger.error("Unable to create a new player with username: "+ name+", because that name is already in use ", iae);
		    messagingTemplate.convertAndSend("/topic/errors","Unable to create a new player with username: "+ name+", because that name is already in use ");	    	
	    }	    
	    return roomService.getPlayers();
	}
}