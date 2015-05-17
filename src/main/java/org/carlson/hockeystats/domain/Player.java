package org.carlson.hockeystats.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by ryancarlson on 5/4/15.
 */
@Entity
@Table(name="players")
public class Player
{

	@Id
	@Type(type="pg-uuid")
	UUID id;

	String name;

	String number;

	String position;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="team_id")
	@JsonBackReference
	Team team;

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

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public Team getTeam()
	{
		return team;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}
}
