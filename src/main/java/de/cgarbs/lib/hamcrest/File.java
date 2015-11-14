/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.hamcrest;

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
	 * Matches two instances of {@link java.io.File} by comparing their
	 * absolute paths.  Null values are also considered equal.
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
				description.appendText("absolute path is ").appendValue(getAbsolutePathSafe(file));
			}

			@Override
			public void describeMismatch(final Object item, Description mismatchDescription)
			{
				mismatchDescription.appendText(" was ").appendValue(getAbsolutePathSafe((java.io.File) item));
			}

			@Override
			public boolean matches(Object item)
			{
				if (item == null)
				{
					return item == file;
				}
				return ((java.io.File) item).getAbsolutePath().equals(getAbsolutePathSafe(file));
			}

			private String getAbsolutePathSafe(java.io.File file)
			{
				if (file == null)
				{
					return null;
				}
				return file.getAbsolutePath();
			}
		};
	}
}
