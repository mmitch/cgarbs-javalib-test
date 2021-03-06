/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.hamcrest;

import static de.cgarbs.lib.hamcrest.File.sameFileAs;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class FileTest
{
	final static Character sep = File.separatorChar;

	@Test
	public void checkSameFileAs()
	{
		{
			File a = new File("some file.txt");
			File b = new File("some file.txt");

			assertThat(a, is(sameFileAs(b)));
		}
		{
			File a = new File("some file.txt");
			File b = new File("some other file.txt");

			assertThat(a, is(not(sameFileAs(b))));
		}
	}

	@Test
	public void checkSameFileAsNull()
	{
		{
			File a = null;
			File b = null;

			assertThat(a, is(sameFileAs(b)));
		}
		{
			File a = null;
			File b = new File("some file.txt");

			assertThat(a, is(not(sameFileAs(b))));
		}
		{
			File a = new File("some other file.txt");
			File b = null;

			assertThat(a, is(not(sameFileAs(b))));
		}
	}

	@Test
	public void checkSameFileAsRelativeDirectories()
	{
		{
			File a = new File("somedir"+sep+"some file.txt");
			File b = new File("somedir"+sep+"some file.txt");

			assertThat(a, is(sameFileAs(b)));
		}
		{
			File a = new File("somedir"+sep+".."+sep+"somedir"+sep+"some file.txt");
			File b = new File("somedir"+sep+"some file.txt");

			assertThat(a, is(sameFileAs(b)));
		}
		{
			File a = new File("somedir"+sep+"some file.txt");
			File b = new File("some other dir"+sep+"some file.txt");

			assertThat(a, is(not(sameFileAs(b))));
		}
	}

	@Test
	public void checkSameFileAsAbsoluteDirectories()
	{
		String currentPath = new File(".").getAbsolutePath();
		{
			File a = new File(currentPath+sep+"somedir"+sep+"some file.txt");
			File b = new File("somedir"+sep+"some file.txt");

			assertThat(a, is(sameFileAs(b)));
		}
		{
			File a = new File(currentPath+sep+"somedir"+sep+".."+sep+"somedir"+sep+"some file.txt");
			File b = new File("somedir"+sep+"some file.txt");

			assertThat(a, is(sameFileAs(b)));
		}
		{
			File a = new File(currentPath+sep+"somedir"+sep+"some file.txt");
			File b = new File("some other dir"+sep+"some file.txt");

			assertThat(a, is(not(sameFileAs(b))));
		}
	}

	@Test
	public void checkSameFileAsDescriptions()
	{
		final File FILE_1 = new File("some file.txt");
		final File FILE_2 = new File("some other file.txt");

		Description description;

		description = new StringDescription();
		sameFileAs(FILE_1).describeTo(description);
		assertThat(
				description.toString(),
				is(not(isEmptyString()))
				);
		assertThat(
				description.toString(),
				endsWith(FILE_1.toString()+"\"")
				);
		assertThat(
				description.toString(),
				containsString("canonical")
				);

		description = new StringDescription();
		sameFileAs(FILE_1).describeMismatch(FILE_2, description);
		assertThat(
				description.toString(),
				is(not(isEmptyString()))
				);
		assertThat(
				description.toString(),
				endsWith(FILE_2.toString()+"\"")
				);
		assertThat(
				description.toString(),
				containsString("canonical")
				);
	}

	@Test
	public void checkSameFileAsDescriptionsIOException()
	{
		final File FILE_1 = new File("\u0000"+sep+"\u0000");
		final File FILE_2 = new File("some other file.txt");

		Description description;

		description = new StringDescription();
		sameFileAs(FILE_1).describeTo(description);
		assertThat(
				description.toString(),
				is(not(isEmptyString()))
				);
		assertThat(
				description.toString(),
				endsWith(FILE_1.toString()+"\"")
				);
		assertThat(
				description.toString(),
				containsString("absolute")
				);

		description = new StringDescription();
		sameFileAs(FILE_2).describeMismatch(FILE_1, description);
		assertThat(
				description.toString(),
				is(not(isEmptyString()))
				);
		assertThat(
				description.toString(),
				endsWith(FILE_1.toString()+"\"")
				);
		assertThat(
				description.toString(),
				containsString("absolute")
				);

		description = new StringDescription();
		sameFileAs(FILE_1).describeMismatch(FILE_2, description);
		assertThat(
				description.toString(),
				is(not(isEmptyString()))
				);
		assertThat(
				description.toString(),
				endsWith(FILE_2.toString()+"\"")
				);
		assertThat(
				description.toString(),
				containsString("canonical")
				);
	}

	@Test
	public void checkSameFileAsIOException()
	{
		final File FILE_1    = new File("\u0000"+sep+"\u0000");
		final File FILE_2    = new File("some other file.txt");
		final File FILE_NULL = null;

		// IOException never matches
		assertThat(FILE_1, is(not(sameFileAs(FILE_1))));
		assertThat(FILE_2, is(not(sameFileAs(FILE_1))));
		assertThat(FILE_1, is(not(sameFileAs(FILE_2))));

		assertThat(FILE_1, is(not(sameFileAs(FILE_NULL))));
		assertThat(FILE_NULL, is(not(sameFileAs(FILE_1))));
	}
}
