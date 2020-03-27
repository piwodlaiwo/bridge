package com.kadziela.games.bridge.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kadziela.games.bridge.model.Player;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoomService 
{
	public RoomService() {players = new HashSet<Player>();}
	private Collection<Player> players;
	public void addPlayer(Player player) {players.add(player);}
	public boolean removePlayer(Player player) {return players.remove(player);}
	public Collection<Player> getPlayers(){return new HashSet(players);}
}