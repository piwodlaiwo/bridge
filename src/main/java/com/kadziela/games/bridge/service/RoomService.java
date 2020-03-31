package com.kadziela.games.bridge.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kadziela.games.bridge.model.Player;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoomService 
{
	public RoomService() {}
	private final Map<String,Player> players = new ConcurrentHashMap<String,Player>();
	public Player addPlayer(String name) throws IllegalArgumentException 
	{
		if (players.containsKey(name))
		{
			throw new IllegalArgumentException("name "+name+" is already used ny another user");
		}
		Player player = new Player(name);
		player.setName(name);
		players.put(name,player);	
		return player;
	}
	public Player removePlayer(String name) {return players.remove(name);}
	public Collection<Player> getPlayers(){return new HashSet<Player>(players.values());}
	public Player findByName(String name) {return players.get(name);}
}