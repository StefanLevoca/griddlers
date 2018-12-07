package sk.upjs.paz1c.griddlers.entity;

public class Legenda {
	private Long krizovkaId;
	private boolean horna;
	private int riadokStlpec;
	private int poradie;
	private int hodnota;
	
	public Legenda(boolean horna, int riadokStlpec, int poradie, int hodnota) {
		this.horna = horna;
		this.riadokStlpec = riadokStlpec;
		this.poradie = poradie;
		this.hodnota = hodnota;
	}
	
	public Legenda(long krizovkaId, boolean horna, int riadokStlpec, int poradie, int hodnota) {
		this(horna, riadokStlpec, poradie, hodnota);
		this.krizovkaId = krizovkaId;		
	}
	
	

	public Legenda() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hodnota;
		result = prime * result + (horna ? 1231 : 1237);
		result = prime * result + ((krizovkaId == null) ? 0 : krizovkaId.hashCode());
		result = prime * result + poradie;
		result = prime * result + riadokStlpec;
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
		Legenda other = (Legenda) obj;
		if (hodnota != other.hodnota)
			return false;
		if (horna != other.horna)
			return false;
		if (krizovkaId == null) {
			if (other.krizovkaId != null)
				return false;
		} else if (!krizovkaId.equals(other.krizovkaId))
			return false;
		if (poradie != other.poradie)
			return false;
		if (riadokStlpec != other.riadokStlpec)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		
		return "[horna="+ horna + ", riadok="+ riadokStlpec + ", poradie=" + poradie + ", hodnota=" + hodnota + "]";
	}

	public Long getKrizovkaId() {
		return krizovkaId;
	}
	public void setKrizovkaId(Long krizovka_id) {
		this.krizovkaId = krizovka_id;
	}
	public boolean isHorna() {
		return horna;
	}
	public void setHorna(boolean horna) {
		this.horna = horna;
	}
	public int getRiadokStlpec() {
		return riadokStlpec;
	}
	public void setRiadokStlpec(int riadokStlpec) {
		this.riadokStlpec = riadokStlpec;
	}
	public int getPoradie() {
		return poradie;
	}
	public void setPoradie(int poradie) {
		this.poradie = poradie;
	}
	public int getHodnota() {
		return hodnota;
	}
	public void setHodnota(int hodnota) {
		this.hodnota = hodnota;
	}
	
}
