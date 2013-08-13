package com.jantze.redditreader;


// https://github.com/reddit/reddit/wiki/JSON
public class RedditLink {
	
	private String author;
	private String author_flair_css_class;
	private String author_flair_text;
	private boolean clicked;
	private String domain;
	private boolean hidden;
	private boolean is_self;
	private String link_flair_css_class;
	private String link_flair_text;
	//private RedditMedia media;
	//private RedditMediaEmbed media_embed;
	private boolean over_18;
	private String permalink;
	private boolean saved;
	private int score;
	private String selftext;
	private String selftext_html;
	private String subreddit;
	private String subreddit_id;
	private String thumbnail;
	private String title;
	private String url;
	private long edited;
	private String distinguished;
	
	
	@Override
	public String toString() {
		return "title: " + title;
	}

	//TODO: implement media and media_embed
	
	/**
	 * GETTERS AND SETTERS
	 * 
	 */
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor_flair_css_class() {
		return author_flair_css_class;
	}

	public void setAuthor_flair_css_class(String author_flair_css_class) {
		this.author_flair_css_class = author_flair_css_class;
	}

	public String getAuthor_flair_text() {
		return author_flair_text;
	}

	public void setAuthor_flair_text(String author_flair_text) {
		this.author_flair_text = author_flair_text;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isIs_self() {
		return is_self;
	}

	public void setIs_self(boolean is_self) {
		this.is_self = is_self;
	}

	public String getLink_flair_css_class() {
		return link_flair_css_class;
	}

	public void setLink_flair_css_class(String link_flair_css_class) {
		this.link_flair_css_class = link_flair_css_class;
	}

	public String getLink_flair_text() {
		return link_flair_text;
	}

	public void setLink_flair_text(String link_flair_text) {
		this.link_flair_text = link_flair_text;
	}

	public boolean isOver_18() {
		return over_18;
	}

	public void setOver_18(boolean over_18) {
		this.over_18 = over_18;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSelftext() {
		return selftext;
	}

	public void setSelftext(String selftext) {
		this.selftext = selftext;
	}

	public String getSelftext_html() {
		return selftext_html;
	}

	public void setSelftext_html(String selftext_html) {
		this.selftext_html = selftext_html;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public String getSubreddit_id() {
		return subreddit_id;
	}

	public void setSubreddit_id(String subreddit_id) {
		this.subreddit_id = subreddit_id;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getEdited() {
		return edited;
	}

	public void setEdited(long edited) {
		this.edited = edited;
	}

	public String getDistinguished() {
		return distinguished;
	}

	public void setDistinguished(String distinguished) {
		this.distinguished = distinguished;
	}
	
	
}
