package com.kadziela.games.bridge.model;

import com.kadziela.games.bridge.model.enumeration.SeatPosition;

public class SeatedPlayer
{
	private SeatPosition position;
	private Player player;
	
	protected SeatedPlayer() {}
	public SeatedPlayer(SeatPosition pos, Player p) 
	{
		position = pos;
		player=p;
	}
	public SeatPosition getPosition() {
		return position;
	}
	public void setPosition(SeatPosition position) {
		this.position = position;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		SeatedPlayer other = (SeatedPlayer) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (position != other.position)
			return false;
		return true;
	}
	@Override public String toString() {return "SeatedPlayer [position=" + position + ", player=" + player + "]";}	
}