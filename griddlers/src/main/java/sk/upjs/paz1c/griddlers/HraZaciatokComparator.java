package sk.upjs.paz1c.griddlers;

import java.time.LocalDateTime;
import java.util.Comparator;

import sk.upjs.paz1c.griddlers.entity.Hra;

public class HraZaciatokComparator implements Comparator<Hra> {

	@Override
	public int compare(Hra hra1, Hra hra2) {
		LocalDateTime hra1Cas = hra1.getZaciatok();
		LocalDateTime hra2Cas = hra2.getZaciatok();
		return hra2Cas.compareTo(hra1Cas);
	}

}
