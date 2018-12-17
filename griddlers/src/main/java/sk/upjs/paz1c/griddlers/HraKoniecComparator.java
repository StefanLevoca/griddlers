package sk.upjs.paz1c.griddlers;

import java.time.LocalDateTime;
import java.util.Comparator;

import sk.upjs.paz1c.griddlers.entity.Hra;

public class HraKoniecComparator implements Comparator<Hra> {

	@Override
	public int compare(Hra hra1, Hra hra2) {
		LocalDateTime hra1Koniec = hra1.getKoniec();
		LocalDateTime hra2Koniec = hra2.getKoniec();
		return hra2Koniec.compareTo(hra1Koniec);
	}

}
