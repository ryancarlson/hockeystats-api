package org.carlson.hockeystats.api.utils;

import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by ryancarlson on 5/16/15.
 */
public class QuickResponse
{
	public static Response notFound()
	{
		return Response
				.status(Response.Status.NOT_FOUND)
				.build();
	}

	public static Response noContent()
	{
		return Response
				.noContent()
				.build();
	}

	public static Response ok(Object entity)
	{
		return Response
				.ok(entity)
				.build();
	}

	public static Response badRequest(String reason)
	{
		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity(reason)
				.build();
	}

	public static Response created(String pathToResource)
	{
		return Response
				.created(URI.create(pathToResource))
				.build();
	}

}
