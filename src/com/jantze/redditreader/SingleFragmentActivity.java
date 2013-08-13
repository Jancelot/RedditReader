package com.jantze.redditreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	
	protected abstract Fragment createFragment();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		
		FragmentManager fragMgr = getSupportFragmentManager();
		Fragment fragment = fragMgr.findFragmentById(R.id.fragmentContainer);
		
		// if the fragment was already created then it will be retrieved, otherwise we need to create it
		// the fragment would be in the list if the activity was destroyed and is being recreated due to rotation, etc
		if (fragment == null) {
			fragment = createFragment();
			fragMgr.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
		}		
	}
}
