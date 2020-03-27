package com.kadziela.games.bridge.model;

import com.kadziela.games.bridge.model.enumeration.Suit;
import com.kadziela.games.bridge.model.enumeration.ValidBidOption;

public class Bid 
{
	private Long id;
	private SeatedPlayer bidder;
	private ValidBidOption bid;
	private Suit suit;
	private int level;
	
	
}