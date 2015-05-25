package org.carlson.hockeystats.api;

import com.google.common.collect.Lists;
import org.carlson.hockeystats.domain.Player;
import org.carlson.hockeystats.domain.Team;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ryancarlson on 5/16/15.
 */
@Test
public class TeamResourceTest
{
	TeamResource teamResource;

	@BeforeTest
	public void setup()
	{
		teamResource = new TeamResource();
		teamResource.entityManager = Mockito.mock(EntityManager.class);
	}

	@Test
	public void testGetTeam()
	{
		UUID teamId = UUID.randomUUID();

		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(teamId);
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Mockito.when(teamResource.entityManager.find(Team.class, teamId))
				.thenReturn(dekesOfHazard);

		Response response = teamResource.getTeam(teamId);

		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertNotNull(response.getEntity());

		Team teamFromResponse = (Team) response.getEntity();

		Assert.assertEquals(teamFromResponse.getId(), dekesOfHazard.getId());
		Assert.assertEquals(teamFromResponse.getLeague(), dekesOfHazard.getLeague());
		Assert.assertEquals(teamFromResponse.getName(), dekesOfHazard.getName());
	}

	@Test
	public void testGetTeamNotFound()
	{
		UUID teamId = UUID.randomUUID();
		Mockito.when(teamResource.entityManager.find(Team.class, teamId))
				.thenReturn(null);

		Response response = teamResource.getTeam(teamId);

		Assert.assertEquals(response.getStatus(), 404);
		Assert.assertNull(response.getEntity());
	}
	
	@Test public void testGetAllTeams()
	{
		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(UUID.randomUUID());
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Team puckaneers = new Team();
		puckaneers.setId(UUID.randomUUID());
		puckaneers.setName("Puckaneers");
		puckaneers.setLeague("B");

		//TODO: Figure out how to mock a named query
	}

	@Test
	public void testCreateTeam()
	{
		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(UUID.randomUUID());
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Response response = teamResource.createTeam(dekesOfHazard);

		Assert.assertEquals(response.getStatus(), 201);
		Assert.assertEquals(response.getHeaderString("Location"), String.format("/teams/%s", dekesOfHazard.getId()));
	}

	@Test
	public void testCreateTeamWithoutId()
	{
		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(null);
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Response response = teamResource.createTeam(dekesOfHazard);

		Assert.assertEquals(response.getStatus(), 201);
		Assert.assertEquals(response.getHeaderString("Location"), String.format("/teams/%s", dekesOfHazard.getId()));
		Assert.assertNotNull(dekesOfHazard.getId());
	}

	@Test
	public void testUpdateTeam()
	{
		UUID teamId = UUID.randomUUID();

		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(teamId);
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Team updatedDekesOfHazard = new Team();
		updatedDekesOfHazard.setId(teamId);
		updatedDekesOfHazard.setName("Updated Dekes of Hazard");
		updatedDekesOfHazard.setLeague("A");

		// makes it that the team "already exists" in the database, so the update will succeed
		Mockito.when(teamResource.entityManager.getReference(Team.class, teamId))
				.thenReturn(dekesOfHazard);

		Response response = teamResource.updateTeam(teamId, updatedDekesOfHazard);

		Assert.assertEquals(response.getStatus(), 204);
	}

	@Test
	public void testUpdateTeamNotFound()
	{
		UUID teamId = UUID.randomUUID();

		Team updatedDekesOfHazard = new Team();
		updatedDekesOfHazard.setId(teamId);
		updatedDekesOfHazard.setName("Updated Dekes of Hazard");
		updatedDekesOfHazard.setLeague("A");

		Response response = teamResource.updateTeam(teamId, updatedDekesOfHazard);

		Assert.assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testUpdateTeamMismatchedIds()
	{
		UUID teamId = UUID.randomUUID();

		Team updatedDekesOfHazard = new Team();
		updatedDekesOfHazard.setId(teamId);
		updatedDekesOfHazard.setName("Updated Dekes of Hazard");
		updatedDekesOfHazard.setLeague("A");

		Response response = teamResource.updateTeam(UUID.randomUUID(), updatedDekesOfHazard);

		Assert.assertEquals(response.getStatus(), 400);
	}

	@Test
	public void testGetPlayersForTeam()
	{
		UUID teamId = UUID.randomUUID();

		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(teamId);
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Player ryanCarlson = new Player();
		ryanCarlson.setId(UUID.randomUUID());
		ryanCarlson.setName("Ryan Carlson");
		ryanCarlson.setNumber("14");
		ryanCarlson.setPosition("D");
		ryanCarlson.setTeam(dekesOfHazard);

		dekesOfHazard.getPlayers().add(ryanCarlson);

		Mockito.when(teamResource.entityManager.find(Team.class, teamId))
				.thenReturn(dekesOfHazard);

		Response response = teamResource.getPlayersForTeam(teamId);

		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertNotNull(response.getEntity());

		Set<Player> playerSet = (Set<Player>)response.getEntity();

		Assert.assertEquals(playerSet.size(), 1);

		Player player = playerSet.iterator().next();
		Assert.assertEquals(player.getId(), ryanCarlson.getId());
		Assert.assertEquals(player.getName(), "Ryan Carlson");
	}

	@Test
	public void testGetPlayersForTeamNotFound()
	{
		Response response = teamResource.getPlayersForTeam(UUID.randomUUID());

		Assert.assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testAddPlayerToTeam()
	{
		UUID teamId = UUID.randomUUID();

		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(teamId);
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Player ryanCarlson = new Player();
		ryanCarlson.setId(UUID.randomUUID());
		ryanCarlson.setName("Ryan Carlson");
		ryanCarlson.setNumber("14");
		ryanCarlson.setPosition("D");

		Mockito.when(teamResource.entityManager.find(Team.class, teamId))
				.thenReturn(dekesOfHazard);

		Response response = teamResource.addPlayerToTeam(teamId, ryanCarlson);

		Assert.assertEquals(response.getStatus(), 201);
		Assert.assertEquals(response.getHeaderString("Location"), String.format("/teams/%s/players/%s", teamId, ryanCarlson.getId()));
		Assert.assertEquals(ryanCarlson.getTeam(), dekesOfHazard);
	}

	@Test
	public void testAddPlayerToTeamNotFound()
	{
		UUID teamId = UUID.randomUUID();

		Team dekesOfHazard = new Team();
		dekesOfHazard.setId(teamId);
		dekesOfHazard.setName("Dekes of Hazard");
		dekesOfHazard.setLeague("B");

		Player ryanCarlson = new Player();
		ryanCarlson.setId(UUID.randomUUID());
		ryanCarlson.setName("Ryan Carlson");
		ryanCarlson.setNumber("14");
		ryanCarlson.setPosition("D");
		ryanCarlson.setTeam(dekesOfHazard);

		Response response = teamResource.addPlayerToTeam(teamId, ryanCarlson);

		Assert.assertEquals(response.getStatus(), 400);
		Assert.assertEquals(response.getEntity(), String.format("Team with ID %s does not exist", teamId));
	}

	@Test
	public void testUpdatePlayer()
	{
		UUID teamId = UUID.randomUUID();
		UUID playerId = UUID.randomUUID();

		Player ryanCarlson = new Player();
		ryanCarlson.setId(playerId);
		ryanCarlson.setName("Ryan Carlson");
		ryanCarlson.setNumber("14");
		ryanCarlson.setPosition("D");

		Mockito.when(teamResource.entityManager.getReference(Player.class, playerId))
				.thenReturn(ryanCarlson);

		Response response = teamResource.updatePlayerInformation(teamId,
				playerId,
				ryanCarlson);

		Assert.assertEquals(response.getStatus(), 204);
		Assert.assertNull(response.getEntity());
	}

	@Test
	public void testUpdatePlayerNotFound()
	{
		UUID teamId = UUID.randomUUID();
		UUID playerId = UUID.randomUUID();

		Player ryanCarlson = new Player();
		ryanCarlson.setId(playerId);
		ryanCarlson.setName("Ryan Carlson");
		ryanCarlson.setNumber("14");
		ryanCarlson.setPosition("D");

		Response response = teamResource.updatePlayerInformation(teamId,
				playerId,
				ryanCarlson);

		Assert.assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testUpdatePlayerMismatchedIds()
	{
		UUID teamId = UUID.randomUUID();
		UUID playerId = UUID.randomUUID();

		Player ryanCarlson = new Player();
		ryanCarlson.setId(playerId);
		ryanCarlson.setName("Ryan Carlson");
		ryanCarlson.setNumber("14");
		ryanCarlson.setPosition("D");

		Response response = teamResource.updatePlayerInformation(teamId,
				UUID.randomUUID(),
				ryanCarlson);

		Assert.assertEquals(response.getStatus(), 400);
	}
}
