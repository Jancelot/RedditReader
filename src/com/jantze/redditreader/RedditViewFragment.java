/**
 * 
 */
package com.jantze.redditreader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author chris
 *
 */
public class RedditViewFragment extends Fragment {

	private String mUrl;
	private WebView mWebView;
	
	
	/**
	 * onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		
		mUrl = getActivity().getIntent().getData().toString();
	}
	
	/**
	 * onCreateView
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_reddit_view, parent, false);
		
		mWebView = (WebView)fragView.findViewById(R.id.web_view);
		
		mWebView.getSettings().setJavaScriptEnabled(true);
		
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});
		
		mWebView.loadUrl(mUrl);
		
		return fragView;
	}
}
