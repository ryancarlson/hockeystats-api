package org.carlson.hockeystats.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

/**
 * Created by ryancarlson on 5/4/15.
 */
@Entity
@Table(name="games")
public class Game
{

	@Id
	@Type(type="pg-uuid")
	UUID id;

	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="home_team_id")
	Team homeTeam;

	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="away_team_id")
	Team awayTeam;

	int homeTeamScore;

	int awayTeamScore;

	boolean homeTeamShootoutWin;

	boolean awayTeamShootoutWin;

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	DateTime gameTimestamp;


	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public Team getHomeTeam()
	{
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam)
	{
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam()
	{
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam)
	{
		this.awayTeam = awayTeam;
	}

	public int getHomeTeamScore()
	{
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore)
	{
		this.homeTeamScore = homeTeamScore;
	}

	public int getAwayTeamScore()
	{
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore)
	{
		this.awayTeamScore = awayTeamScore;
	}

	public boolean isHomeTeamShootoutWin()
	{
		return homeTeamShootoutWin;
	}

	public void setHomeTeamShootoutWin(boolean homeTeamShootoutWin)
	{
		this.homeTeamShootoutWin = homeTeamShootoutWin;
	}

	public boolean isAwayTeamShootoutWin()
	{
		return awayTeamShootoutWin;
	}

	public void setAwayTeamShootoutWin(boolean awayTeamShootoutWin)
	{
		this.awayTeamShootoutWin = awayTeamShootoutWin;
	}

	public DateTime getGameTimestamp()
	{
		return gameTimestamp;
	}

	public void setGameTimestamp(DateTime gameTimestamp)
	{
		this.gameTimestamp = gameTimestamp;
	}
}
