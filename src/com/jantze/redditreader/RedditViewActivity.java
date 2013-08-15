package com.jantze.redditreader;

import android.support.v4.app.Fragment;

public class RedditViewActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new RedditViewFragment();
	}
}
