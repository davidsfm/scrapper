package scrapper;

public class Torrent {
	
	private boolean isCienciaFiccion = false;
	private boolean isFantasia = false;
	private boolean isDrama = false;
	private boolean isAccion = false;
	private boolean isAventura = false;
	private boolean isSuspense = false;
	private boolean isHumor = false;
	private boolean isHechosReales = false;
	private String url = "";
	private String capitulo = "";
	private String description = "";
	private String calidad = "";
	private String titulo = "";
	private String urlUi = "";
	private boolean isInGigas = false;
	private Double tamanio = new Double(0);
	
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
	public boolean isCienciaFiccion() {
		return isCienciaFiccion;
	}
	public void setCienciaFiccion(boolean isCienciaFiccion) {
		this.isCienciaFiccion = isCienciaFiccion;
	}
	public boolean isFantasia() {
		return isFantasia;
	}
	public void setFantasia(boolean isFantasia) {
		this.isFantasia = isFantasia;
	}
	public boolean isDrama() {
		return isDrama;
	}
	public void setDrama(boolean isDrama) {
		this.isDrama = isDrama;
	}
	public boolean isAccion() {
		return isAccion;
	}
	public void setAccion(boolean isAccion) {
		this.isAccion = isAccion;
	}
	public boolean isAventura() {
		return isAventura;
	}
	public void setAventura(boolean isAventura) {
		this.isAventura = isAventura;
	}
	public boolean isHumor() {
		return isHumor;
	}
	public void setHumor(boolean isHumor) {
		this.isHumor = isHumor;
	}
	public boolean isHechosReales() {
		return isHechosReales;
	}
	public void setHechosReales(boolean isHechosReales) {
		this.isHechosReales = isHechosReales;
	}
	public boolean isSuspense() {
		return isSuspense;
	}
	public void setSuspense(boolean isSuspense) {
		this.isSuspense = isSuspense;
	}
	
	
	
}
