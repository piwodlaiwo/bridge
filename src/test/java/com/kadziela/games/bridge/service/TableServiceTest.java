package com.kadziela.games.bridge.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kadziela.games.bridge.model.Player;
import com.kadziela.games.bridge.model.SeatedPlayer;
import com.kadziela.games.bridge.model.Table;
import com.kadziela.games.bridge.model.enumeration.SeatPosition;

@SpringBootTest
public class TableServiceTest 
{
	@Autowired TableService tableService;
	@Autowired RoomService roomService;
	
	@Test
	void testChooseFirstDealer()
	{
		Table table = tableService.create();
		Player north = roomService.addPlayer("north");
		Player east = roomService.addPlayer("east");
		Player south = roomService.addPlayer("south");
		Player west = roomService.addPlayer("west");
		tableService.sitDown(north, table.getId(), SeatPosition.NORTH);
		tableService.sitDown(east, table.getId(), SeatPosition.EAST);
		tableService.sitDown(south, table.getId(), SeatPosition.SOUTH);
		tableService.sitDown(west, table.getId(), SeatPosition.WEST);
		SeatedPlayer firstDealer = tableService.chooseFirstDealer(table);
		assertNotNull(firstDealer);
	}
}