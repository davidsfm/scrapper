package scrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import scrapper.rsswriter.Feed;
import scrapper.rsswriter.FeedMessage;
import scrapper.rsswriter.RSSFeedWriter;

public class Scrapper {
	
	
	private static Properties properties;
	
	public Scrapper(String rutaPropiedades) throws IOException{
		
		Properties prop = new Properties();
		InputStream is = null;
		
		is = new FileInputStream(rutaPropiedades+"configuracion.properties");
		prop.load(is);
		this.properties = prop;
		
	}
	
	public  String  getProperty(String string) {
		return Scrapper.properties.getProperty( string );
	}
	
	
	private void start() throws Exception{
		
		
		File fXmlFile = new File(this.getProperty("ruta.series.in"));
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
		
	    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	    NodeList series = doc.getDocumentElement().getElementsByTagName("serie");
	    
	   // System.out.println("--> "+series.getLength());
	    
	    if( series.getLength()>0 ) {
	    	 this.generateRSS_Series(series);
	    }
	    
	    NodeList films= doc.getDocumentElement().getElementsByTagName("film");

	    if(films.getLength()>0) {
	    	this.generateRSS_Filsm(films);
	    }
	}

	private void generateRSS_Series(NodeList series) throws Exception{
		
		Vector<FuenteWeb> fuenteWebs = new Vector<FuenteWeb>();
	    for (int temp = 0; temp < series.getLength(); temp++) {
	        Node serie = series.item(temp);

	        String url = serie.getAttributes().getNamedItem("url").getTextContent();
	        String titulo = serie.getAttributes().getNamedItem("titulo").getTextContent();
	        
	        FuenteWeb fuenteWeb = FactoryFuenteWeb.builder( url, false );
	        fuenteWeb.setTitulo(titulo);
	        //System.out.println("[Scrapper]"+serie.getTextContent());
	        
	        fuenteWebs.add( fuenteWeb );
	    }
	    
		this.generaRSS( fuenteWebs );
	}
	private void generateRSS_Filsm(NodeList films) throws Exception{
		
	    for (int temp = 0; temp < films.getLength(); temp++) {
	        Node film = films.item(temp);
	        
	        Vector<FuenteWeb> fuenteWebs = new Vector<FuenteWeb>();

	        String titulo = film.getAttributes().getNamedItem("titulo").getTextContent();
	        String url = film.getAttributes().getNamedItem("urlSource").getTextContent();
	        String category = film.getAttributes().getNamedItem("category").getTextContent();
	        String definition = film.getAttributes().getNamedItem("definition").getTextContent();
	        String langVideo = film.getAttributes().getNamedItem("langVideo").getTextContent();
	        Double maxDownloadSizeGB =  new Double( film.getAttributes().getNamedItem("maxDownloadSizeGB").getTextContent() );
	        
	        FuenteMejortorrentFilms fuenteWeb = (FuenteMejortorrentFilms) FactoryFuenteWeb.builder( url , true);
	        fuenteWeb.setTitulo(titulo);
	        fuenteWeb.setUrlFuenteWeb(url);
	        fuenteWeb.setCategory(category);
	        fuenteWeb.setDefinition(definition);
	        fuenteWeb.setLangVideo(langVideo);
	        fuenteWeb.setMaxSizeGB( maxDownloadSizeGB );
	        
	        fuenteWebs.add( fuenteWeb );
	        
	        this.generaRSS_Films( fuenteWebs );
	    }
	    
		
	}
	
	
	
	
	
	private void generaRSS_Films(Vector<FuenteWeb> fuenteWebs) throws Exception {
		
        for (Iterator iterator = fuenteWebs.iterator(); iterator.hasNext();) {
        	
			FuenteMejortorrentFilms fuenteWeb = (FuenteMejortorrentFilms) iterator.next();
	        String copyright = "Copyright hold by The Fox";
	        String title = "My RSS of " + fuenteWeb.getTitulo();
	        String description = "Pelis de ["+fuenteWeb.getDefinition()+ "] de [" + fuenteWeb.getCategory() + "] en " + fuenteWeb.getLangVideo() + " maximo " + fuenteWeb.getMaxSizeGB()+" GB";
	        String language = "en";
	        String link = fuenteWeb.getUrlFuenteWeb();
	        Calendar cal = new GregorianCalendar();
	        Date creationDate = cal.getTime();
	        SimpleDateFormat date_format = new SimpleDateFormat(
	                "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
	        String pubdate = date_format.format(creationDate);
	        Feed rssFeeder = new Feed(title, link, description, language,
	                copyright, pubdate, this.getProperty("rss.ttl"));
	
	        
	        Vector<Torrent> torrents = fuenteWeb.getListTorrentsFilms();
	        
	        int i = 1;
	        for (Iterator iterator2 = torrents.iterator(); iterator2.hasNext();) {
				Torrent torrent = (Torrent) iterator2.next();
			
				if(torrent.getTamanio().longValue() < fuenteWeb.getMaxSizeGB().longValue() ) {
					
					boolean aniaDirTorrent = (
							( fuenteWeb.getCategory().contains( "cienciaFiccion" ) && torrent.isCienciaFiccion() ) ||
							( fuenteWeb.getCategory().contains( "drama" ) && torrent.isDrama() ) ||
							( fuenteWeb.getCategory().contains( "aventura" ) && torrent.isAventura() ) ||
							( fuenteWeb.getCategory().contains( "thriller" ) && torrent.isSuspense() ) ||
							( fuenteWeb.getCategory().contains( "comedia" ) && torrent.isHumor() ) ||
							( fuenteWeb.getCategory().contains( "reales" ) && torrent.isHechosReales()) ||
							( fuenteWeb.getCategory().contains( "fantasia" ) && torrent.isFantasia() ) ||
							( fuenteWeb.getCategory().contains( "all" ) )
							);
					
					if( aniaDirTorrent ) {
						FeedMessage feed = new FeedMessage();
				        feed.setTitle( torrent.getTitulo() );
				        feed.setDescription( torrent.getDescription() );
				        feed.setAuthor("");
				        feed.setGuid( "" );
				        feed.setLink( torrent.getUrl() );
				        rssFeeder.getMessages().add(feed);
				        System.out.println("[Scrapper][Generating RSS Films] Torren A�ADIDO a la seleccion! ( "+torrent.getTitulo()+")" );
					}else{
						System.out.println("[Scrapper][Generating RSS Films] Torrent ( "+torrent.getTitulo()+" ) , diferente al seleccionado: " + fuenteWeb.getCategory() );
					}
				}else {
					System.out.println("[Scrapper][Generating RSS Films] Torrent ( "+torrent.getTitulo()+", "+torrent.getTamanio()+" GB ) , mayor de " + fuenteWeb.getMaxSizeGB() + " GB");
				}
		        i++;
			}
	        
	        // now write the file
	        RSSFeedWriter writer = new RSSFeedWriter(rssFeeder, this.getProperty("ruta.series.rss")+fuenteWeb.getTitulo().replace(" ", "")+"_films.rss");
	        try {
	            writer.write();
	            System.out.println("RSS Genernado: "+this.getProperty("ruta.series.rss")+fuenteWeb.getTitulo().replace(" ", "")+"_films.rss");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}

	private void generaRSS(Vector<FuenteWeb> fuenteWebs) throws Exception {
		//rssSeries.rss
        for (Iterator iterator = fuenteWebs.iterator(); iterator.hasNext();) {
        	
			FuenteWeb fuenteWeb = (FuenteWeb) iterator.next();
	        String copyright = "Copyright hold by The Fox";
	        String title = "My RSS of " + fuenteWeb.getTitulo();
	        String description = "RSS de tus series preferidas!";
	        String language = "en";
	        String link = "http://www.desconocido.com";
	        Calendar cal = new GregorianCalendar();
	        Date creationDate = cal.getTime();
	        SimpleDateFormat date_format = new SimpleDateFormat(
	                "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
	        String pubdate = date_format.format(creationDate);
	        Feed rssFeeder = new Feed(title, link, description, language,
	                copyright, pubdate, this.getProperty("rss.ttl"));
	
	        
	        Vector<String> torrents = fuenteWeb.getListTorrents();
	        
	        int i = 1;
	        for (Iterator iterator2 = torrents.iterator(); iterator2.hasNext();) {
				String torrent = (String) iterator2.next();
			
				FeedMessage feed = new FeedMessage();
		        feed.setTitle("Chapter " + i);
		        feed.setDescription("This is a description");
		        feed.setAuthor("Fox...");
		        //feed.setGuid("http://www.vogella.com/tutorials/RSSFeed/article.html");
		        feed.setLink( torrent );
		        rssFeeder.getMessages().add(feed);
				
		        i++;
			}
	        // now write the file
	        RSSFeedWriter writer = new RSSFeedWriter(rssFeeder, this.getProperty("ruta.series.rss")+fuenteWeb.getTitulo().replace(" ", "")+"_RssSeries.rss");
	        try {
	            writer.write();
	            System.out.println("RSS Genernado: "+this.getProperty("ruta.series.rss")+fuenteWeb.getTitulo().replace(" ", "")+"_RssSeries.rss");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
		
	public static void main(String[] args) {
		//System.out.println("[Lanzado] Fichero " + args[0]);
		//Scrapper scrapper = new Scrapper(args[0]);
		try {
			String rutaPropiedades = "";
			if(args.length != 0){
				rutaPropiedades = args[0];
			}
			System.out.println("[Scrapper][INI] program ");
			Scrapper scrapper = new Scrapper( rutaPropiedades );
			scrapper.start();
			System.out.println("[Scrapper][FIN] OK ");
		} catch (Exception e) {
			System.out.println("[Scrapper][ERROR]:"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		}


}
