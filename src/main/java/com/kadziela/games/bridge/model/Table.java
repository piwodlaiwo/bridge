package com.kadziela.games.bridge.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.kadziela.games.bridge.model.enumeration.SeatPosition;

public class Table 
{	
	private final Long id = System.currentTimeMillis();
	private final Map<SeatPosition,SeatedPlayer> players = new ConcurrentHashMap<SeatPosition, SeatedPlayer>();
	
	public Long getId() {return id;}
	public void sitDown(SeatPosition sp, Player p) throws IllegalStateException
	{
		SeatedPlayer extantOccupant = players.get(sp); 
		if (extantOccupant != null)
		{
			throw new IllegalStateException("someone ("+extantOccupant+") is already seated in position "+sp+" so player "+p+" cannot sit down there");
		}
		players.put(sp, new SeatedPlayer(sp, p));		
	}
	public void standUp(SeatPosition sp) throws IllegalStateException
	{
		if (players.get(sp) == null)
		{
			throw new IllegalStateException("nobody is sitting at position "+sp);
		}
		SeatedPlayer p = players.remove(sp);
		p = null;
	}
	public SeatedPlayer getPlayerAtPosition(SeatPosition sp) {return players.get(sp);}
	public Collection<SeatedPlayer> getAllSeatedPlayers(){return new HashSet<SeatedPlayer> (players.values());}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Table [id=" + id + ", players=" + players + "]";
	}	
}