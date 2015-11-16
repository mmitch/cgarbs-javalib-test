/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.hamcrest;

import java.io.IOException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Hamcrest Matchers for {@link java.io.File} objects
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.1.0
 *
 */
public abstract class File
{
	/**
	 * Creates a matcher that matches if two instances of {@link java.io.File}
	 * have the same canonical paths.  Null values are also considered equal.<br>
	 * <b>Beware:</b> If there is an {@link IOException} calculating
	 * the canonical path, the absolute path is used instead which might
	 * result in unexpected mismatches.
	 *
	 * @param file the {@link java.io.File} to compare to
	 * @return a new {@link BaseMatcher} matching as described
	 */
	public static Matcher<java.io.File> sameFileAs(final java.io.File file)
	{
		return new BaseMatcher<java.io.File>() {

			@Override
			public void describeTo(Description description)
			{
				try
				{
					description.appendText("canonical path is ").appendValue(getCanonicalPathSafe(file));
				}
				catch (IOException e)
				{
					description.appendText("absolute(!) path is ").appendValue(file.getAbsolutePath());
				}
			}

			@Override
			public void describeMismatch(final Object item, Description mismatchDescription)
			{
				try
				{
					mismatchDescription.appendText(" was canonical ").appendValue(getCanonicalPathSafe((java.io.File) item));
				}
				catch (IOException e)
				{
					mismatchDescription.appendText(" was absolute(!) ").appendValue(((java.io.File) item).getAbsolutePath());
				}
			}

			@Override
			public boolean matches(Object item)
			{
				try
				{
					if (item == null)
					{
						return item == file;
					}
					return ((java.io.File) item).getCanonicalPath().equals(getCanonicalPathSafe(file));
				}
				catch (IOException e)
				{
					return false;
				}
			}

			private String getCanonicalPathSafe(java.io.File file) throws IOException
			{
				if (file == null)
				{
					return null;
				}
				return file.getCanonicalPath();
			}
		};
	}
}
