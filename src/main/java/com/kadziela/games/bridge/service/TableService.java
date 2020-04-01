package com.kadziela.games.bridge.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.kadziela.games.bridge.model.Card;
import com.kadziela.games.bridge.model.Player;
import com.kadziela.games.bridge.model.SeatedPlayer;
import com.kadziela.games.bridge.model.Table;
import com.kadziela.games.bridge.model.enumeration.SeatPosition;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TableService 
{
	private static final Logger logger = LogManager.getLogger(TableService.class);

	private final Map<Long,Table> tables = new ConcurrentHashMap<Long,Table>();

	/**
	 * Creates a new, empty table and adds it to the internal collection
	 * @return the newly created table
	 */

	public Table create()
	{
		Table table = new Table();
		tables.put(table.getId(), table);
		return table;
	}
	public Collection<Table> getAllTables() {return new HashSet<Table>(tables.values());}
	public Table sitDown(Player player,Long tableId, SeatPosition position) throws IllegalArgumentException, IllegalStateException
	{
		Table table = tables.get(tableId);
		if (table == null)
		{
			throw new IllegalArgumentException(String.format("%s does not match any extant table ids ",tableId));
		}
		table.sitDown(position, player);
		return table;
	}
	public Table standUp(Player player,Long tableId, SeatPosition position) throws IllegalArgumentException, IllegalStateException
	{
		Table table = tables.get(tableId);
		if (table == null)
		{
			throw new IllegalArgumentException(String.format("%s does not match any extant table ids ",tableId));
		}
		table.standUp(position);
		return table;
	}
	public Table findById(Long tableId) {return tables.get(tableId);}
	public void deal(Table table)
	{
		Assert.notNull(table, "Cannot deal on a null table");
		logger.debug(String.format("dealing on table %s",table));
		table.deal();		
	}
	public Map<Card,SeatPosition> chooseFirstDealer(Table table) throws IllegalArgumentException
	{
		Assert.notNull(table, "Cannot choose a dealer on a null table");
		List<Card> shuffledDeck = table.getDeck().getShuffled();
		Card north = shuffledDeck.get(0);
		logger.debug(String.format("NORTH card is %s, ", north));
		Card east = shuffledDeck.get(1);
		logger.debug(String.format("EAST card is %s", east));
		Card south = shuffledDeck.get(2);
		logger.debug(String.format("SOUTH card is %s", south));
		Card west = shuffledDeck.get(3);
		logger.debug(String.format("WEST card is %s", west));
		logger.debug("sorting ... ");
		NavigableMap<Card,SeatPosition> map = new TreeMap<Card,SeatPosition>();
		map.put(north,SeatPosition.NORTH);
		map.put(east,SeatPosition.EAST);
		map.put(south,SeatPosition.SOUTH);
		map.put(west,SeatPosition.WEST);
		Map.Entry<Card, SeatPosition> lastEntry = map.lastEntry();
		logger.debug(String.format("the best card is %s, so the dealer is %s",lastEntry.getKey(),lastEntry.getValue()));
		table.setCurrentDealer(table.getPlayerAtPosition(lastEntry.getValue()));
		return map;
	}
}