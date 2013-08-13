/**
 * 
 */
package com.jantze.redditreader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author chris
 *
 */
public class RedditListFragment extends ListFragment {

	private static final String LOG_TAG = "RedditListFragment";
	
	private FetchItemsTask fetchTask = new FetchItemsTask();
	private ArrayList<RedditLink> mLinks;
	
	
	/**
	 * onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "onCreate() called");
		
		setRetainInstance(true);
		
		fetchTask.execute();
		
		setupAdapter();
	}

	/**
	 * setupAdapter
	 * 
	 */
	void setupAdapter() {
		Log.i(LOG_TAG, "setupAdapter() called");
		if (getActivity() == null) {
			return;
		}
		if (mLinks != null) {
			Log.i(LOG_TAG, "setupAdapter() called.  links count: " + mLinks.size());
			RedditListAdapter adapter = new RedditListAdapter(mLinks);
			setListAdapter(adapter);
		} else {
			Log.i(LOG_TAG, "setupAdapter() called.  links null");
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
		//RedditLink link = ((RedditListAdapter)getListAdapter()).getItem(position);
		//Log.d(LOG_TAG, link.getTitle() + " was clicked");
		
		// Start RedditLinkActivity
		//Intent i = new Intent(getActivity(), RedditViewActivity.class);
		//i.putExtra(RedditListFragment.EXTRA_LINK_ID, link.getId());
		//startActivity(i);
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
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.reddit_list_item_title);
			titleTextView.setText(link.getTitle());
			
			// TODO: network task needs to be on a thread
			/*
			try {
				if (link.getThumbnail() != null) {
					Log.i(LOG_TAG, "Thumbnail FOUND: " + link.getThumbnail());
					ImageView image = (ImageView)convertView.findViewById(R.id.reddit_list_item_thumbnail);
					Log.i(LOG_TAG, "Thumbnail FOUND 2");
					URL url = new URL(link.getThumbnail());
					Log.i(LOG_TAG, "Thumbnail FOUND 3");
					image.setImageBitmap(getRemoteImage(url));
					Log.i(LOG_TAG, "Thumbnail FOUND 4");
				}
			} catch (Exception e) {
				Log.i(LOG_TAG, "Thumbnail not found: " + e.getStackTrace());
			}
			*/
			
			return convertView;
		}
		
		Bitmap getRemoteImage(final URL aURL) {
		    try {
		        final URLConnection conn = aURL.openConnection();
		        conn.connect();
		        final BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		        final Bitmap bm = BitmapFactory.decodeStream(bis);
		        bis.close();
		        return bm;
		    } catch (IOException e) {}
		    return null;
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
			mLinks = links;
			setupAdapter();
		}

		/**
		 * doInBackground
		 * 
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected ArrayList<RedditLink> doInBackground(Void... params) {
			// fetch the links from Reddit
			return new RedditFetcher().fetchItems();
		}
		
	}
	
}
