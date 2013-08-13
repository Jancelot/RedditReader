package com.jantze.redditreader;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Menu;

public class RedditListActivity extends SingleFragmentActivity {

	@Override
	public Fragment createFragment() {
		return new RedditListFragment();
	}
}
