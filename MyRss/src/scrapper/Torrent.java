package scrapper;

public class Torrent {
	
	
	public static final String all = "all";
	public static final String cienciaFiccion =  "cienciaFiccion";
	public static final String drama =  "drama";
	public static final String aventura =  "aventura";
	public static final String thriller =  "thriller";
	public static final String comedia =  "comedia";
	
	String url = "";
	String capitulo = "";
	String description = "";
	String calidad = "";
	String titulo = "";
	String urlUi = "";
	boolean isInGigas = false;
	Double tamanio = new Double(0);
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCapitulo() {
		return capitulo;
	}
	public void setCapitulo(String capitulo) {
		this.capitulo = capitulo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCalidad() {
		return calidad;
	}
	public String getUrlUi() {
		return urlUi;
	}
	public void setUrlUi(String urlUi) {
		this.urlUi = urlUi;
	}
	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public boolean isInGigas() {
		return isInGigas;
	}
	public void setInGigas(boolean isInGigas) {
		this.isInGigas = isInGigas;
	}
	public Double getTamanio() {
		return tamanio;
	}
	public void setTamanio(Double tamanio) {
		this.tamanio = tamanio;
	}
	
	
	
}
