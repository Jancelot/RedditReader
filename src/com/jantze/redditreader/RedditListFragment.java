/**
 * 
 */
package com.jantze.redditreader;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author chris
 * 
 * Pull To Refresh Functionality: https://github.com/chrisbanes/Android-PullToRefresh
 *
 */
public class RedditListFragment extends ListFragment implements OnRefreshListener<ListView> {
	
	private static final String LOG_TAG = "RedditListFragment";
	
	private ArrayList<RedditLink> mLinks;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private static final String EXTRA_AFTER = "com.jantze.redditreader.after";
	private String mAfter;
		
	
	/**
	 * onCreate
`	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mAfter = savedInstanceState.getString(EXTRA_AFTER);
		}
		
		setRetainInstance(true);
		
		new FetchItemsTask().execute();
		
		setupAdapter();		
	}
	
	/**
	 * onCreateView
	 * 
	 * (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View layout =  super.onCreateView(inflater, container, savedInstanceState);
		
		ListView lv = (ListView) layout.findViewById(android.R.id.list);
        ViewGroup parent = (ViewGroup) lv.getParent();

        // Remove ListView and add PullToRefreshListView in its place
		int lvIndex = parent.indexOfChild(lv);
		parent.removeViewAt(lvIndex);

		mPullToRefreshListView = new PullToRefreshListView(layout.getContext());
        mPullToRefreshListView.setMode(Mode.PULL_FROM_END);
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>(){
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            	// do nothing
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {            	
            	// save index and top position
            	ListView lv = (ListView)mPullToRefreshListView.getRefreshableView();
            	int listIndex = lv.getFirstVisiblePosition();
            	View v = lv.getChildAt(0);
            	int listTop = (v == null) ? 0 : v.getTop();
            	Log.i(LOG_TAG, "onPullUpToRefresh()  index: " + listIndex + "   top: " + listTop);
            	
            	// bottom, fetch older items
            	new FetchItemsTask().execute();
            	//mPullToRefreshListView.onRefreshComplete();
            	
            	// restore last position
            	//lv.setSelectionFromTop(listIndex, listTop);
            	//lv.setSelection(index);
            	
            	// TODO: check for "no more items"
            	
            }
        });

        parent.addView(mPullToRefreshListView, lvIndex, lv.getLayoutParams());
        
		return layout;
	}

	/**
	 * onRefresh
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Log.d(LOG_TAG, "*********   onRefresh() called   ***********");
		// Do work to refresh the list here.
		//new FetchItemsTask().execute();
	}
	
	/**
	 * onSaveInstanceState
	 * 
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(EXTRA_AFTER, mAfter);
	}

	/**
	 * setupAdapter
	 * 
	 */
	void setupAdapter() {
		if (getActivity() == null) {
			return;
		}
		if (mLinks != null) {
			RedditListAdapter adapter = new RedditListAdapter(mLinks);
			setListAdapter(adapter);
		} else {
			setListAdapter(null);
		}
	}
	
	/**
	 * onResume
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (getActivity() == null || mLinks == null) {
			return;
		}
		((RedditListAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	/**
	 * onListItemClick
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		// NOTE: position is base 1 while array is base 0, compensate		
		RedditLink link = ((RedditListAdapter)getListAdapter()).getItem(position-1);
		Uri linkUri = Uri.parse(link.getUrl());
		
		// Start RedditLinkActivity
		Intent intentView = new Intent(getActivity(), RedditViewActivity.class);
		intentView.setData(linkUri);
		
		//i.putExtra(RedditListFragment.EXTRA_LINK_ID, link.getId());
		startActivity(intentView);
	}
	
	
	/**
	 * class CrimeAdapter
	 * @author chris
	 *
	 */
	private class RedditListAdapter extends ArrayAdapter<RedditLink> {

		public RedditListAdapter(ArrayList<RedditLink> links) {
			super(getActivity(), 0, links);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// if we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_redditlink, null);
			}
			
			RedditLink link = getItem(position);

			// is_self indicator
			if (link.isIs_self()) {
				
			}
			
			// more button + self text
			final Button moreButton = (Button)convertView.findViewById(R.id.reddit_list_item_more_button);
			final TextView selfTextView = (TextView)convertView.findViewById(R.id.reddit_list_item_selftext);
			selfTextView.setVisibility(View.GONE);
			if (link.getSelftext().isEmpty()) {
				moreButton.setVisibility(View.GONE);			
			} else {
				moreButton.setVisibility(View.VISIBLE);
				moreButton.setOnClickListener(new View.OnClickListener() {					
					@Override
					public void onClick(View v) {
						if (selfTextView.isShown()) {
							selfTextView.setVisibility(View.GONE);
							moreButton.setText(R.string.list_item_more_button);							
						} else {
							selfTextView.setVisibility(View.VISIBLE);
							moreButton.setText(R.string.list_item_less_button);
						}
					}
				});				
				selfTextView.setText(link.getSelftext());
			}
			
			// score
			TextView scoreTextView = (TextView)convertView.findViewById(R.id.reddit_list_item_score);
			scoreTextView.setText(Integer.toString(link.getScore()));
			
			// title
			TextView titleTextView = (TextView)convertView.findViewById(R.id.reddit_list_item_title);
			titleTextView.setText(link.getTitle());
			
			//comments
			int count = link.getNum_comments();
			Resources res = getResources();
			String commentsMade = res.getQuantityString(R.plurals.number_of_comments, count, count);			
			TextView commentsTextView = (TextView)convertView.findViewById(R.id.reddit_list_item_comments);
			commentsTextView.setText(commentsMade);
		
			// thumbnail - network access must be done on a thread
			String thumbnailUrl = link.getThumbnail();			
			if (URLUtil.isValidUrl(thumbnailUrl)) {				
				ImageView thumbnailImageView = (ImageView)convertView.findViewById(R.id.reddit_list_item_thumbnail);
				new DrawableManager().fetchDrawableOnThread(thumbnailUrl, thumbnailImageView);
			}
			
			return convertView;
		}
	}
	
	
	/**
	 * class FetchItemsTask
	 * 
	 * Utilize RedditFetcher class to fetch all items from reddit
	 * @author chris
	 *
	 */
	private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<RedditLink>> {

		/**
		 * onPostExecute
		 * 
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(ArrayList<RedditLink> links) {			
			// append new links to existing
			if (mLinks == null) {
				mLinks = links;
			} else {
				mLinks.addAll(links);
			}
			setupAdapter();
			
			mPullToRefreshListView.onRefreshComplete();
			
			Log.d(LOG_TAG, "onPostExecute()  refresh complete -------");
		}

		/**
		 * doInBackground
		 * 
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected ArrayList<RedditLink> doInBackground(Void... params) {

			// instantiate RedditFetcher and set the "after" value
			RedditFetcher fetcher = RedditFetcher.get(getActivity());
			fetcher.setAfter(mAfter);

			// fetch items, returns only additional links
			ArrayList<RedditLink> links = fetcher.fetchLinks();			
			
			// retrieve new "after" value and update fragment arguments
			mAfter = fetcher.getAfter();
			
			return links;
		}
		
	}
	
}
