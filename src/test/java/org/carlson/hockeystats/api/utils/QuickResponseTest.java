package org.carlson.hockeystats.api.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by ryancarlson on 5/16/15.
 */
public class QuickResponseTest
{

	@Test
	public void testOk()
	{
		Assert.assertEquals(QuickResponse.ok(new Object()).getStatus(), 200);
	}

	/**
	 * I really want 100% coverage, and since this class's methods are all static
	 * it shows the line & method where the default constructor would be as untested.
	 *
	 * Lame.
	 */
	@Test
	public void testConstructor()
	{
		new QuickResponse();
	}
}
