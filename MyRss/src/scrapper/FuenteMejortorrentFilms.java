package scrapper;

import java.io.IOException;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FuenteMejortorrentFilms extends FuenteWeb{
	
	private String category="";
	private String definition="";
	private String langVideo="";
	private Double maxSizeGB = new Double(1024) ;

	public FuenteMejortorrentFilms(String fuente) throws IOException {
		super(fuente);
	}
	
	
	@Override
	public Vector<String> getListTorrents() throws Exception {
		return null;
	}
	public Vector<Torrent> getListTorrentsFilms() throws Exception {
		Vector<Torrent> torrents = new Vector<>();
		
		Document doc = Jsoup.connect(super.getUrlFuenteWeb()).get();
		Elements chapters = doc.select("a[href*=peli-descargar-torrent]");
		
		for(Element f: chapters){
			String urlFilm =  f.attr("abs:href");
			//System.out.println("PELI:"+urlFilm);
			
			//<b>(HDRip)</b>
			
			Element element =  f.nextElementSibling();
			
			String calidad = ( ""+element).replace("(", "").replace(")", "").replace("<b>", "").replace("</b>", "").toLowerCase();
			
			//boolean matchOk = this.getDefinition().indexOf( calidad )!=-1;
			boolean matchOk = false;
			int i = 0;
			String filter[] = this.getDefinition().split("\\|");
			
			while (  !matchOk && i < ( filter.length ) ) {
				String mFilter = filter[i];
				matchOk = calidad.indexOf( mFilter )!=-1;
//				System.out.println(mFilter +"   " +matchOk);
				i++;
			}
			
//			System.out.println(matchOk + " - Filtro: "+this.getDefinition()+" - Scrapeado:"+calidad+"");
//			System.out.println();
//			System.out.println();
			
			//En este punto machamos la calidad
			if( matchOk ){
				Torrent torrent = this.scrap(urlFilm, calidad);
				torrents.add( torrent );
			}
		}
		return torrents;
	}

	private Torrent scrap(String urlFilm, String calidad) throws IOException {
		
		Document doc = Jsoup.connect(urlFilm).get();
		
		Element paginaDescarga = doc.select("a[href*='secciones.php?sec=descargas']").first();

		//TOMAMOS INFO DEL TORRENT
		//table width='450'
		Element tablaDesc = doc.select("table[width=450]").first();
		
//		System.out.println(tablaDesc.html());
//		
//		System.out.println();
//		System.out.println("ESTE?:"+tablaDesc.select("span>b").html());
		
		String tabla = tablaDesc.html().toLowerCase();
		
		//PARSEAMOS EL TITULO
		String titulo = tablaDesc.select("span>b").html();
		titulo = titulo.substring(0, titulo.indexOf("\n"));
	
		//System.out.println(titulo);
		
		//PARSEAMOS EL TAMAÑO
		//<b>Tama�o:</b>&nbsp; 1,71 GB<img width="1
		String cadenaTamanio = "<b>Tama�o:</b>&nbsp; ".toLowerCase();
		int pos1 = tabla.indexOf( cadenaTamanio ) + cadenaTamanio.length();
		int pos2 = (tabla.indexOf("gb<")!=-1?tabla.indexOf("gb<"):tabla.indexOf("mb<"));
		
		boolean isInGigas = (tabla.indexOf("gb<")!=-1?true:false);
		
		String sTamanio = tabla.substring(pos1, pos2);
		
		Double tamanio = new Double(sTamanio.replace(',', '.'));
		//System.out.println("TAMAÑO: "+tamanio);

		
		//DESCARGAMOS EL TORRENT DE LA PAGINA, accediendo a la página de descarga
		Document doc2 = Jsoup.connect( paginaDescarga.attr("abs:href") ).get();
		
		Element link2 = doc2.select("a[href*='.torrent']").first();
		
		//System.out.println("TORRENT: "+link2.attr("abs:href"));
		
		//COJEMOS LA DESCRIPCION DE LA PELI
		//System.out.println("DESCRIPCION:::::::."+tablaDesc.select("div[align]").last().text());
		String descripcion = tablaDesc.select("div[align]").last().text();
		
		
		
		//.--------------------------------
		Torrent torrent = new Torrent();
		torrent.setUrl(link2.attr("abs:href"));
		torrent.setTitulo( titulo );
		torrent.setCalidad(calidad);
		torrent.setTamanio(tamanio);
		torrent.setInGigas(isInGigas);
		torrent.setUrlUi(urlFilm);
		if(!isInGigas){
			torrent.setInGigas(true);
			torrent.setTamanio(tamanio/1024);
		}
		
		torrent.setDescription(descripcion);
		torrent.setCienciaFiccion( tabla.indexOf("ficci�n")!=-1 );
		torrent.setAccion(tabla.indexOf("acci�n")!=-1);
		torrent.setAventura(tabla.indexOf("aventura")!=-1);
		torrent.setDrama(tabla.indexOf("drama")!=-1);
		torrent.setFantasia(tabla.indexOf("fantas�a")!=-1);
		torrent.setHechosReales(false);
		torrent.setHumor(false);
		torrent.setSuspense(false);
		//TODO: Ver que literal filtrar
		
		return torrent;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getDefinition() {
		return definition;
	}


	public void setDefinition(String definition) {
		this.definition = definition;
	}


	public String getLangVideo() {
		return langVideo;
	}


	public void setLangVideo(String langVideo) {
		this.langVideo = langVideo;
	}


	public Double getMaxSizeGB() {
		return maxSizeGB;
	}


	public void setMaxSizeGB(Double maxSizeGB) {
		this.maxSizeGB = maxSizeGB;
	}
	
	

}
