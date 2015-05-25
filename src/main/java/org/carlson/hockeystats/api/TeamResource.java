package org.carlson.hockeystats.api;

import org.carlson.hockeystats.api.utils.QuickResponse;
import org.carlson.hockeystats.domain.Player;
import org.carlson.hockeystats.domain.Team;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by ryancarlson on 5/4/15.
 */
@Stateless
@Path("/teams")
public class TeamResource
{
	@PersistenceContext(name = "hockeystatsDS")
	EntityManager entityManager;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeams()
	{
		List<Team> allTeams = entityManager.createNamedQuery("Teams.selectAll", Team.class)
				.getResultList();

		return QuickResponse.ok(allTeams);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeam(@PathParam("id") UUID teamId)
	{
		Team team = entityManager.find(Team.class, teamId);

		if(team == null)
		{
			return QuickResponse.notFound();
		}
		else
		{
			return QuickResponse.ok(team);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTeam(Team team)
	{
		entityManager.persist(team);

		return QuickResponse.created(String.format("/teams/%s", team.getId()));
	}

	@PUT
	@Path("/{teamId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTeam(@PathParam("teamId") UUID teamId, Team team)
	{
		if(!Objects.equals(teamId, team.getId()))
		{
			return QuickResponse
					.badRequest(String.format("Team ID %s in path did not match team ID in entity %s", teamId, team.getId()));
		}

		if(entityManager.getReference(Team.class, teamId) == null)
		{
			return QuickResponse.notFound();
		}

		entityManager.merge(team);

		return QuickResponse.noContent();
	}

	@GET
	@Path("/{teamId}/players")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlayersForTeam(@PathParam("teamId") UUID teamId)
	{
		Team team = entityManager.find(Team.class, teamId);

		if(team == null)
		{
			return QuickResponse.notFound();
		}

		return QuickResponse.ok(team.getPlayers());
	}

	@POST
	@Path("/{teamId}/players")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPlayerToTeam(@PathParam("teamId") UUID teamId, Player player)
	{
		Team team = entityManager.find(Team.class, teamId);

		if(team == null)
		{
			return QuickResponse
					.badRequest(String.format("Team with ID %s does not exist", teamId));
		}

		team.getPlayers().add(player);

		return QuickResponse.created(String.format("/teams/%s/players/%s", teamId, player.getId()));
	}

	@PUT
	@Path("/{teamId}/players/{playerId}")
	public Response updatePlayerInformation(@PathParam("teamId") UUID teamId,
											@PathParam("playerId") UUID playerId,
											Player updatedPlayer)
	{
		if(!Objects.equals(playerId, updatedPlayer.getId()))
		{
			return QuickResponse
					.badRequest(String.format("Player ID %s in path did not match player ID in entity %s", playerId, updatedPlayer.getId()));
		}

		if(entityManager.getReference(Player.class, playerId) == null)
		{
			return QuickResponse.notFound();
		}

		entityManager.merge(updatedPlayer);

		return QuickResponse.noContent();
	}
}
