package sk.upjs.paz1c.griddlers.entity;

public class PolickoHry{
		
	private Long id;
	private Long idHry;
	private boolean stav;
	private int surX;
	private int surY;
	private boolean pozadovanyStav;
	
	public PolickoHry(boolean stav, int surX, int surY) {
		this.stav = stav;
		this.surX = surX;
		this.surY = surY;
	}
	public PolickoHry(boolean stav, int surX, int surY, boolean pozadovanyStav) {
		this(stav, surX, surY);
		this.pozadovanyStav = pozadovanyStav;
	}
	public PolickoHry() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdKrizovky() {
		return idHry;
	}
	public void setIdKrizovky(Long idKrizovky) {
		this.idHry = idKrizovky;
	}
	public boolean getStav() {
		return stav;
	}
	public void setStav(boolean stav) {
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
