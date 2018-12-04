package sk.upjs.paz1c.griddlers.biznis;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Obdobie;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;

public class StatistikaVyberManager {

	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();

	public StatistikaVyberManager() {
	}

	public ObservableList<Hra> zmenHry(Obdobie obdobie) {
		List<Hra> hryList = hraDao.getPodlaObdobia(obdobie);
		return FXCollections.observableArrayList(hryList);
	}
}
