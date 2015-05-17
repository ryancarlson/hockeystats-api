package org.carlson.hockeystats.api;

import org.carlson.hockeystats.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
}
