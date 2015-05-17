package org.carlson.hockeystats.api;

import org.carlson.hockeystats.domain.Team;

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
import java.util.Objects;
import java.util.UUID;

/**
 * Created by ryancarlson on 5/4/15.
 */
@Path("/teams")
public class TeamResource
{
	@PersistenceContext(name = "hockeystatsDS")
	EntityManager entityManager;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeam(@PathParam("id") UUID teamId)
	{
		Team team = entityManager.find(Team.class, teamId);

		if(team == null)
		{
			return Response
					.status(Response.Status.NOT_FOUND)
					.build();
		}
		else
		{
			return Response
					.ok(team)
					.build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTeam(Team team)
	{
		// make sure the team has an ID in case the client did not set it
		if(team.getId() == null) team.setId(UUID.randomUUID());

		entityManager.persist(team);

		return Response
				.created(URI.create(String.format("/teams/%s", team.getId())))
				.build();
	}

	@PUT
	@Path("/{teamId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTeam(@PathParam("teamId") UUID teamId, Team team)
	{
		if(!Objects.equals(teamId, team.getId()))
		{
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(String.format("Team ID %s in path did not match team ID in entity %s", teamId, team.getId()))
					.build();
		}

		if(entityManager.getReference(Team.class, teamId) == null)
		{
			return Response
					.status(Response.Status.NOT_FOUND)
					.build();
		}

		entityManager.merge(team);

		return Response
				.noContent()
				.build();
	}

}
