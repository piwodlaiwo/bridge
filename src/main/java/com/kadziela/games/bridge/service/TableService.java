package com.kadziela.games.bridge.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kadziela.games.bridge.model.Player;
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
}