package com.kadziela.games.bridge.model;

import java.util.Collection;
import com.kadziela.games.bridge.model.enumeration.SeatPosition;

public class SeatedPlayer extends Player 
{
	
	public SeatedPlayer(String name) {super(name);}
	private Long id;
	private SeatPosition position;
	private Collection<Card> cards;
}