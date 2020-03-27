package com.kadziela.games.bridge.model;

import java.util.Objects;

import com.kadziela.games.bridge.model.enumeration.Rank;
import com.kadziela.games.bridge.model.enumeration.Suit;
import com.kadziela.games.bridge.util.ComparisonUtils;

public class Card implements Comparable<Card>
{
	private Long id;
	private Rank rank;
	private Suit suit;

	public Card() {}
	public Card(Rank rank, Suit suit) {this.rank = rank;this.suit = suit;}

	public Long getId() {return id;}
	public Rank getRank() {return rank;}
	public void setRank(Rank rank) {this.rank = rank;}
	public Suit getSuit() {return suit;}
	public void setSuite(Suit suit) {this.suit = suit;}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Card)) return false;
		Card other = (Card) obj;
		if (!Objects.equals(id, other.id)) return false;
		if (rank != other.rank) return false;
		if (suit != other.suit) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("Card [id=%s, rank=%s, suit=%s]",getId(), getRank(), getSuit());
	}
	/**
	 * compares (sorts) by rank first then suit, finally id
	 */
	@Override
	public int compareTo(Card o) 
	{
		if (o == null) return 1;
		
		return ComparisonUtils.nullSafeCompare(rank, o.getRank()) != 0 ? ComparisonUtils.nullSafeCompare(rank, o.getRank()) : 
			ComparisonUtils.nullSafeCompare(suit, o.getSuit()) != 0 ? ComparisonUtils.nullSafeCompare(suit, o.getSuit()) :
				ComparisonUtils.nullSafeCompare(id, o.getId());
	}
}