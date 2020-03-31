package com.kadziela.games.bridge.controllers;

import java.util.Collection;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import com.kadziela.games.bridge.model.Player;
import com.kadziela.games.bridge.model.Table;
import com.kadziela.games.bridge.model.enumeration.SeatPosition;
import com.kadziela.games.bridge.service.RoomService;
import com.kadziela.games.bridge.service.TableService;

@Controller
public class TableController 
{
	private static final Logger logger = LogManager.getLogger(TableController.class);

	@Autowired private RoomService roomService;
	@Autowired private TableService tableService;
	@Autowired private SimpMessageSendingOperations messagingTemplate;
	
	/**
	 * Opens a new table
	 * @return a collection of the currently extant tables
	 */
	@MessageMapping("/table/openNew")
	@SendTo("/topic/room")
	public Collection<Table> openNew()
	{
		logger.debug("opening a new table");
		tableService.create();
		return tableService.getAllTables();
	}
	@MessageMapping("/table/getAll")
	@SendTo("/topic/room")
	public Collection<Table> getAll()
	{
		logger.debug("returning all tables");
		return tableService.getAllTables();
	}
	/**
	 * given a name of a Player who is in the room, the id of a table in the room, and a position, this method will attempt to sit the player down at the
	 * given position at the given table, validating that there is no one already sitting in that seat 
	 * @param attributes - the map of attributes, must contain a player, a position and a table (at least). 
	 * The keys are as follows: playerName, tableId, position
	 */
	@MessageMapping("/table/sitDown")
	public void sitDown(Map<String,String> attributes)
	{
    	String playerName = null;
    	String tableId = null;
    	String position = null;
		logger.debug(String.format("Table Controller received a request to sit a player down at a table with the following attributes %s", attributes.toString()));
	    try
	    {
			Assert.notNull(attributes,"the input attributes canot be null");
	    	playerName = attributes.get("playerName");
	    	tableId = attributes.get("tableId");
	    	position = attributes.get("playerName");
	    	Assert.notNull(playerName, "the playerName must be passed in the attribute map");
	    	Assert.notNull(tableId, "the tableId must be passed in the attribute map");
	    	Assert.notNull(position, "the position must be passed in the attribute map");
	    	
	    	Player player = roomService.findByName(playerName);
	    	Assert.notNull(player,String.format("Player named %s is not in the room",playerName));
	    	tableService.sitDown(player, Long.valueOf(tableId), SeatPosition.valueOf(position));
	    	
	    	messagingTemplate.convertAndSend("/queue/private/"+playerName, String.format("you have successfully sat down at table %s in position %s",tableId,position));
	    	messagingTemplate.convertAndSend("/topic/table/"+tableId, String.format("player %s has successfully sat down at table %s in position %s",playerName,tableId,position));
	    }
	    catch (IllegalArgumentException iae)
	    {
	    	logger.error(String.format("An IllegalArgumentException occurred while trying to sit player %s at table %s and position %s ",playerName,tableId,position),iae);
		    messagingTemplate.convertAndSend("/topic/errors",
		    		String.format("An IllegalArgumentException occurred while trying to sit player %s at table %s and position %s. The error message is: %s",playerName,tableId,position,iae.getMessage()));	    	
	    }	    
	    catch (IllegalStateException ise)
	    {
	    	logger.error(String.format("An IllegalStateException occurred while trying to sit player %s at table %s and position %s ",playerName,tableId,position),ise);
		    messagingTemplate.convertAndSend("/topic/errors",
	    		String.format("An IllegalStateException occurred while trying to sit player %s at table %s and position %s. The error message is: %s",playerName,tableId,position,ise.getMessage()));	    	
	    }	    
	}
	/**
	 * given a name of a Player who is in the room, the id of a table in the room, and a position, this method will attempt to stand the player up from the
	 * given position at the given table, validating that there is in fact that player in that position 
	 * @param attributes - the map of attributes, must contain a player, a position and a table (at least). 
	 * The keys are as follows: playerName, tableId, position
	 */
	@MessageMapping("/table/standUp")
	public void standUp(Map<String,String> attributes)
	{
    	String playerName = null;
    	String tableId = null;
    	String position = null;
		logger.debug(String.format("Table Controller received a request to stand a player up with the following attributes %s", attributes.toString()));
	    try
	    {
			Assert.notNull(attributes,"the input attributes canot be null");
	    	playerName = attributes.get("playerName");
	    	tableId = attributes.get("tableId");
	    	position = attributes.get("playerName");
	    	Assert.notNull(playerName, "the playerName must be passed in the attribute map");
	    	Assert.notNull(tableId, "the tableId must be passed in the attribute map");
	    	Assert.notNull(position, "the position must be passed in the attribute map");
	    	
	    	Player player = roomService.findByName(playerName);
	    	Assert.notNull(player,String.format("Player named %s is not in the room",playerName));
	    	tableService.standUp(player, Long.valueOf(tableId), SeatPosition.valueOf(position));
	    	
	    	messagingTemplate.convertAndSend("/queue/private/"+playerName, String.format("you have successfully stood up from table %s and position %s",tableId,position));
	    	messagingTemplate.convertAndSend("/topic/table/"+tableId, String.format("player %s has successfully stood up from table %s in position %s",playerName,tableId,position));
	    }
	    catch (IllegalArgumentException iae)
	    {
	    	logger.error(String.format("An IllegalArgumentException occurred while trying to stand up a player %s at table %s and position %s ",playerName,tableId,position),iae);
		    messagingTemplate.convertAndSend("/topic/errors",
		    		String.format("An IllegalArgumentException occurred while trying to stand up a player %s at table %s and position %s. The error message is: %s",playerName,tableId,position,iae.getMessage()));	    	
	    }	    
	    catch (IllegalStateException ise)
	    {
	    	logger.error(String.format("An IllegalStateException occurred while trying to stand up a player %s at table %s and position %s ",playerName,tableId,position),ise);
		    messagingTemplate.convertAndSend("/topic/errors",
	    		String.format("An IllegalStateException occurred while trying to stand up a player %s at table %s and position %s. The error message is: %s",playerName,tableId,position,ise.getMessage()));	    	
	    }	    
	}
}