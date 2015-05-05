package org.carlson.hockeystats.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
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
	@Id @Type(type="pg_uuid")
	UUID id;

	String name;

	String league;

	@OneToMany(mappedBy="team")
	Set<Player> players;

	public UUID getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getLeague()
	{
		return league;
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
