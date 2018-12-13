package sk.upjs.paz1c.griddlers.entity;

public class Policko {

	private Long id;
	private Long idKrizovky;
	private boolean stav;
	private int surX;
	private int surY;

	public Policko(boolean stav, int surX, int surY) {
		this.stav = stav;
		this.surX = surX;
		this.surY = surY;
	}

	public Policko(Long id, boolean stav, int surX, int surY) {
		this(stav, surX, surY);
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Policko other = (Policko) obj;
		if (surX != other.surX)
			return false;
		if (surY != other.surY)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "x=" + surX + ", y=" + surY + ", " + stav;
	}

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

}
