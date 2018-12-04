package sk.upjs.paz1c.griddlers.entity;

public class PolickoHry {
		
	private Long id;
	private Long idKrizovky;
	private Boolean stav;
	private int surX;
	private int surY;
	private boolean pozadovanyStav;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdKrizovky() {
		return idKrizovky;
	}
	public void setIdKrizovky(Long idKrizovky) {
		this.idKrizovky = idKrizovky;
	}
	public Boolean getStav() {
		return stav;
	}
	public void setStav(Boolean stav) {
		this.stav = stav;
	}
	public int getSurX() {
		return surX;
	}
	public void setSurX(int surX) {
		this.surX = surX;
	}
	public int getSurY() {
		return surY;
	}
	public void setSurY(int surY) {
		this.surY = surY;
	}
	public boolean isPozadovanyStav() {
		return pozadovanyStav;
	}
	public void setPozadovanyStav(boolean pozadovanyStav) {
		this.pozadovanyStav = pozadovanyStav;
	}
	
	
}
