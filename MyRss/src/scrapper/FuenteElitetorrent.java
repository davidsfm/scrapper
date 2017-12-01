package scrapper;

import java.io.IOException;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FuenteElitetorrent extends FuenteWeb{

	public FuenteElitetorrent(String fuente) throws IOException {
		super(fuente);
		// TODO Auto-generated constructor stub
	}

	

	protected Elements getChaptersList() {
		Document doc = super.getUrlDocument();
		
		
		Element episodiosList = doc.select("ul.episodios").first();
		
		Elements links = episodiosList.select("div.episodiotitle>a[href]");
//		for(Element l: links){
//			System.out.println("link: "+l.text()+" " +l.attr("abs:href"));
//		}
		
		//System.out.println(episodiosList.html());
		return links;
	}	
	
	
	private String getTorrent() throws Exception {
		
		Document doc = super.getUrlDocument();
		
		Element tablaLinks = doc.select("div.links_table>div.fix-table>table>tbody").first();
		
		//System.out.println(tablaLinks.html());
		
		Element link = tablaLinks.select("a[href]").first();
		
		//System.out.println("link: "+link.text()+" " +link.attr("abs:href"));
		
		//print all available links on page
		/**
		Elements links = tablaLinks.select("a[href]");
		for(Element l: links){
			System.out.println("link: "+l.text()+" " +l.attr("abs:href"));
		}
		**/
		return link.attr("abs:href");
	}
	
	
	@Override
	public Vector<String> getListTorrents() throws Exception{
		Vector<String> torrents = new Vector<>();
		
		Elements chapters = this.getChaptersList();
		
		for(Element c: chapters){
			
			
			String urlChapter = c.attr("abs:href");

			//System.out.println("CAPITULO: ----------->"+urlChapter);
			
			
			//FuenteWeb fuenteWeb = new FuenteWeb( urlChapter );
			FuenteWeb fuenteWeb = FactoryFuenteWeb.builder( urlChapter, false );
			
			try {
				String torrent = ((FuenteElitetorrent) fuenteWeb).getTorrent() ;
				
				//System.out.println("TORRENT: =============>"+torrent);
				
				torrents.add( torrent );
			} catch (Exception e) {
				System.out.println("PARA EL CAPITULO " + urlChapter + " NO EXISTE TORRENT");
			}
			
		}
		
		return torrents;
	}
	
	
}
