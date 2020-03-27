package com.kadziela.games.bridge.model;

import java.util.Collection;
import com.kadziela.games.bridge.model.enumeration.SeatPosition;

public class SeatedPlayer extends Player 
{
	private Long id;
	private SeatPosition position;
	private Collection<Card> cards;
}