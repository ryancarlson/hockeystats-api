package org.carlson.hockeystats.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

	@OneToMany(mappedBy="team", fetch = FetchType.EAGER)
	@JsonManagedReference
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
