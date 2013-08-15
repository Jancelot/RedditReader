package com.jantze.redditreader;

import android.support.v4.app.Fragment;

public class RedditListActivity extends SingleFragmentActivity {

	@Override
	public Fragment createFragment() {
		return new RedditListFragment();
	}
}
