/****
 * RedditListing
 * 
 * author: Chris Jantze (cjantze@gmail.com)
 * 
 * Singleton to store the Listing object and manage its array of Link children
 * 
 * TODO this class has been written to the assigned task.  A more flexible solution
 * 		would be to make the ArrayList type generic.
 * 
 */

package com.jantze.redditreader;


//https://github.com/reddit/reddit/wiki/JSON
public class RedditListing {
	
	private String mBefore;
	private String mAfter;
	private String mModhash;
	
	
	@Override
	public String toString() {
		return "after: " + mAfter;
	}

	
	/**
	 * GETTERS AND SETTERS
	 * 
	 */
	public String getBefore() {
		return mBefore;
	}

	public void setBefore(String before) {
		mBefore = before;
	}

	public String getAfter() {
		return mAfter;
	}

	public void setAfter(String after) {
		mAfter = after;
	}

	public String getModhash() {
		return mModhash;
	}

	public void setModhash(String modhash) {
		mModhash = modhash;
	}
}
