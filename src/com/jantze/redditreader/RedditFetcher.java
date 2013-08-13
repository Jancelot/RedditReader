package com.jantze.redditreader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * class RedditFetcher
 * 
 * @author chris
 *
 */
public class RedditFetcher {

	private static final String LOG_TAG = "RedditFetcher";
	
	private static final String ENDPOINT = "http://www.reddit.com/.json";	
	
	private static final String PARAM_AFTER = "after";
	private static final String PARAM_BEFORE = "before";
	private static final String PARAM_COUNT = "count";
	private static final String PARAM_LIMIT = "limit";  	//limit	the maximum number of items desired (default: 25, maximum: 100)
	
	
	/**
	 * getUrlBytes
	 * 
	 * @param urlSpec
	 * @return
	 * @throws Exception
	 */
	byte[] getUrlBytes(String urlSpec) throws IOException {
		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();
			
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
			
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) >0 ) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			return out.toByteArray();		
		} finally {
			connection.disconnect();
		}
	}
	
	/**
	 * getUrl
	 * 
	 * @param urlSpec
	 * @return
	 * @throws IOException
	 */
	public String getUrl(String urlSpec) throws IOException {
		return new String(getUrlBytes(urlSpec));
	}
	
	/**
	 * fetchItems
	 * 
	 * fetch the recent photos from the REST endpoint
	 */
	public ArrayList<RedditLink> fetchItems() {
		
		Log.i(LOG_TAG, "fetchItems() called");
		
		ArrayList<RedditLink> links = new ArrayList<RedditLink>();
            		
		try {
			Log.i(LOG_TAG, "fetchItems() called 2");
			
			// TODO: retrieve "after" from fragment arguments
			
			// retrieve XML from endpoint
			String url = Uri.parse(ENDPOINT).buildUpon()
					//.appendQueryParameter(PARAM_AFTER, METHOD_GET_RECENT)
					.build().toString();
			Log.i(LOG_TAG, "url: " + url);
			String jsonString = getUrl(url);

			// TODO: need to store "after" in fragment arguments
			JSONObject data = new JSONObject(jsonString).getJSONObject("data");
			
			RedditListing listing = new RedditListing();
			listing.setAfter(data.optString("after"));
			Log.i(LOG_TAG, "after: " + listing.toString());
			
			// cycle through children
			JSONArray children = data.getJSONArray("children");
			Log.i(LOG_TAG, "children: " + children.length());
			
			for (int i=0; i < children.length(); i++) {
				JSONObject child = children.getJSONObject(i).getJSONObject("data");
				
				RedditLink link = new RedditLink();
				link.setAuthor(child.optString("author"));
				link.setAuthor_flair_css_class(child.optString("author_flair_css_class"));
				link.setAuthor_flair_text(child.optString("author_flair_text"));
				link.setClicked(child.optBoolean("clicked"));
				link.setDomain(child.optString("domain"));
				link.setHidden(child.optBoolean("hidden"));
				link.setIs_self(child.optBoolean("is_self"));
				link.setLink_flair_css_class(child.optString("link_flair_css_class"));
				link.setLink_flair_text(child.optString("link_flair_text"));
				link.setOver_18(child.optBoolean("over_18"));
				link.setPermalink(child.optString("permalink"));
				link.setSaved(child.optBoolean("saved"));
				link.setScore(child.optInt("score"));
				link.setSelftext(child.optString("selftext"));
				link.setSelftext_html(child.optString("selftext_html"));
				link.setSubreddit(child.optString("subreddit"));
				link.setSubreddit_id(child.optString("subreddit_id"));
				link.setThumbnail(child.optString("thumbnail"));
				link.setTitle(child.optString("title"));
				link.setUrl(child.optString("url"));
				link.setEdited(child.optLong("edited"));
				link.setDistinguished(child.optString("distinguished"));	
				
				links.add(link);
			}
			
		} catch (IOException ioe) {
			Log.e(LOG_TAG, "Failed to fetch items", ioe);
		} catch (JSONException jsone) {
			 Log.e(LOG_TAG, "JSON Parser - error parsing data: " + jsone.toString());
		} catch(Exception e) {
			Log.e(LOG_TAG, "failed to connect: " + e);		   	
		}
	    
		return links;
	}
	
}
