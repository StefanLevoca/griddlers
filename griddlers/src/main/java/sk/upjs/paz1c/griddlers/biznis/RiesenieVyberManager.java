package sk.upjs.paz1c.griddlers.biznis;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.paz1c.griddlers.Narocnost;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;

public class RiesenieVyberManager {

	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();

	public RiesenieVyberManager() {

	}
	
	
	public ObservableList<Krizovka> zmenKrizovky(Narocnost narocnost) {
		List<Krizovka> krizovkyList = krizovkaDao.getPodlaNarocnosti(narocnost);
		return FXCollections.observableArrayList(krizovkyList);
	}
}
