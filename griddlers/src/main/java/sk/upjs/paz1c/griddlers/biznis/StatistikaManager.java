package sk.upjs.paz1c.griddlers.biznis;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.paz1c.griddlers.Obdobie;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;

public class StatistikaManager {

	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();

	public StatistikaManager() {
	}

	public ObservableList<Hra> zmenHry(Obdobie obdobie) {
		List<Hra> hryList = hraDao.getPodlaObdobia(obdobie);
		return FXCollections.observableArrayList(hryList);
	}

	public String formatujCas(long cas) {
		long sekundy = cas % 60;
		cas = cas / 60;
		long minuty = cas % 60;
		long hodiny = cas / 60;
		return String.format("%d:%d:%d", hodiny, minuty, sekundy);
	}
}
