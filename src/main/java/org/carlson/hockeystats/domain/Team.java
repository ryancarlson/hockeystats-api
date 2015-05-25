package org.carlson.hockeystats.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ryancarlson on 5/4/15.
 */
@Entity
@Table(name="teams")
@NamedQueries(
		@NamedQuery(name="Teams.selectAll", query="SELECT t FROM Team t")
)
public class Team
{
	@Id @Type(type="pg-uuid")
	UUID id = UUID.randomUUID();

	String name;

	String league;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(
			name="roster_entry",
			joinColumns = @JoinColumn(name="team_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="player_id", referencedColumnName = "id")
	)
	Set<Player> players = Sets.newHashSet();

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLeague()
	{
		return league;
	}

	public void setLeague(String league)
	{
		this.league = league;
	}

	public Set<Player> getPlayers()
	{
		return players;
	}

	public void setPlayers(Set<Player> players)
	{
		this.players = players;
	}
}
