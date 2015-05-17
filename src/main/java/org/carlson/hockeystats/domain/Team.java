package org.carlson.hockeystats.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ryancarlson on 5/4/15.
 */
@Entity
@Table(name="teams")
public class Team
{
	@Id @Type(type="pg-uuid")
	UUID id;

	String name;

	String league;

	@OneToMany(mappedBy="team", fetch = FetchType.LAZY)
	@JsonManagedReference
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
