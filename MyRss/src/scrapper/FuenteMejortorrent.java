package scrapper;

import java.io.IOException;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FuenteMejortorrent extends FuenteWeb{

	public FuenteMejortorrent(String fuente) throws IOException {
		super(fuente);
	}

	@Override
	public Vector<String> getListTorrents() throws Exception {
		Vector<String> torrents = new Vector<String>();
		
		Document doc = Jsoup.connect(super.getUrlFuenteWeb()).get();
		
		//System.out.println(doc.html());
		
		//Element tablaLinks = doc.select("form^episodios    div.links_table>div.fix-table>table>tbody").first();
		//Element capitulos = doc.select("a[href]").first();
		
		//System.out.println(formEpisodios);
		
		
		//print all available links on page
		Elements chapters = doc.select("a[href*=serie-episodio-descargar]");
		
		for(Element c: chapters){
			String urlChapter =  c.attr("abs:href");
			
			//System.out.println("CAPITULO: ----------->"+urlChapter);
			
			
			//FuenteWeb fuenteWeb = new FuenteWeb( urlChapter );
			FuenteWeb fuenteWeb = FactoryFuenteWeb.builder( urlChapter, false );
			
			try {
				String torrent = ((FuenteMejortorrent) fuenteWeb).getTorrent() ;
				
				//System.out.println("TORRENT: =============>"+torrent);
				
				torrents.add( torrent );
				
			} catch (Exception e) {
				System.out.println("PARA EL CAPITULO " + urlChapter + " NO EXISTE TORRENT O HA OCURRIDO UN ERROR");
			}
		}
		
		return torrents;
	}

	private String getTorrent() {
		Document doc = super.getUrlDocument();
		
		Element link = doc.select("a[href*='secciones.php?sec=descargas']").first();
		
		//System.out.println("AQUI???'"+super.getUrlFuenteWeb());
		/**
		Elements links = super.getUrlDocument().select("a[href*='secciones.php?sec=descargas']");
		for(Element l: links){
			System.out.println("link: "+l.text()+" " +l.attr("abs:href"));
		}
		**/
		String urlTorrent = "";
		//System.out.println("Enlace final en->: "+link.attr("abs:href"));
		try {
			FuenteMejortorrent fuenteMejortorrent = (FuenteMejortorrent) FactoryFuenteWeb.builder( link.attr("abs:href"), false );
			urlTorrent = fuenteMejortorrent.getFinalTorrent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return urlTorrent;
	}

	private String getFinalTorrent() {
		Document doc = super.getUrlDocument();
		
		Element link = doc.select("a[href*='.torrent']").first();
		
		//System.out.println("Enlace Torrent!!: "+link.attr("abs:href"));
		
		return link.attr("abs:href");
	}
	
	



}
