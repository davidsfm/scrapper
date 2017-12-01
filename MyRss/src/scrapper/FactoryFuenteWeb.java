package scrapper;

public class FactoryFuenteWeb {
	
public static final FuenteWeb builder(String url, boolean isFilm ) throws Exception {
		
		String sUrl = url.toLowerCase();
		FuenteWeb fuenteWeb;
		
		if( !isFilm ) {
			if(sUrl.contains("elitetorrent2")){
				fuenteWeb = new FuenteElitetorrent(url);
			}else if(sUrl.contains("mejortorrent")) {
				fuenteWeb = new FuenteMejortorrent(url);
			}else{
				throw new Exception("FUENTE WEB NO RECONOCIDA");
			}
		}else {
			fuenteWeb =  new FuenteMejortorrentFilms(url);
		}
		
		return fuenteWeb;
		
	}

}
