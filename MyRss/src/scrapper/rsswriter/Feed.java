package scrapper.rsswriter;

import java.util.ArrayList;
import java.util.List;

public class Feed {
	

    final String title;
    final String link;
    final String description;
    final String language;
    final String copyright;
    final String pubDate;


    final List<FeedMessage> entries = new ArrayList<FeedMessage>();
	private String ttl;

    public Feed(String title, String link, String description, String language,
            String copyright, String pubDate, String ttl) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
        this.setTTL(ttl);
    }

    public List<FeedMessage> getMessages() {
        return entries;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getPubDate() {
        return pubDate;
    }

    @Override
    public String toString() {
        return "Feed [copyright=" + copyright + ", description=" + description
                + ", language=" + language + ", link=" + link + ", pubDate="
                + pubDate + ", title=" + title + "]";
    }

	public String getTTL() {
		return this.ttl;
	}

	public void setTTL(String ttl) {
		this.ttl = ttl;
	}

}
