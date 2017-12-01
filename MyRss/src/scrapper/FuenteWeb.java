package scrapper;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class FuenteWeb {

	private String urlFuenteWeb = "";
	private Document urlDocument = null;
	private String titulo = "";
	
	public FuenteWeb(String fuente) throws IOException {
		this.urlFuenteWeb = fuente;
		this.urlDocument = Jsoup.connect(this.getUrlFuenteWeb()).get();
	}
	
	public abstract Vector<String> getListTorrents() throws Exception;	

	public String getUrlFuenteWeb() {
		return urlFuenteWeb;
	}

	public void setUrlFuenteWeb(String urlFuenteWeb) {
		this.urlFuenteWeb = urlFuenteWeb;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public Document getUrlDocument() {
		return urlDocument;
	}



	public void setUrlDocument(Document urlDocument) {
		this.urlDocument = urlDocument;
	}
	
	
	
	
}
