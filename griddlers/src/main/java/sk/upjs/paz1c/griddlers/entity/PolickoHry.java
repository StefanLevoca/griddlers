package sk.upjs.paz1c.griddlers.entity;

public class PolickoHry {

	private Long id;
	private Long idHry;
	private Boolean stav;
	private int surX;
	private int surY;
	private boolean pozadovanyStav;

	public PolickoHry(Boolean stav, int surX, int surY) {
		this.stav = stav;
		this.surX = surX;
		this.surY = surY;
	}

	public PolickoHry(Boolean stav, int surX, int surY, boolean pozadovanyStav) {
		this(stav, surX, surY);
		this.pozadovanyStav = pozadovanyStav;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idHry == null) ? 0 : idHry.hashCode());
		result = prime * result + surX;
		result = prime * result + surY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolickoHry other = (PolickoHry) obj;
		if (idHry == null) {
			if (other.idHry != null)
				return false;
		} else if (!idHry.equals(other.idHry))
			return false;
		if (surX != other.surX)
			return false;
		if (surY != other.surY)
			return false;
		return true;
	}

	public PolickoHry() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdHry() {
		return idHry;
	}

	public void setIdHry(Long idHry) {
		this.idHry = idHry;
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

	public boolean getPozadovanyStav() {
		return pozadovanyStav;
	}

	public void setPozadovanyStav(boolean pozadovanyStav) {
		this.pozadovanyStav = pozadovanyStav;
	}

}
