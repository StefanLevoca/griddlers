package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.PolickoHry;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.PolickoHryDao;

class MysqlPolickoHryDaoTest {

	private PolickoHryDao polickoHryDao = DaoFactory.INSTANCE.getPolickoHryDao();

	@Test
	void ulozitTest1() {
		PolickoHry polickoHry = new PolickoHry();
		polickoHry.setIdHry(8L);
		polickoHry.setStav(false);
		polickoHry.setSurX(10);
		polickoHry.setSurY(15);
		polickoHry.setPozadovanyStav(false);
		// vytvorenie
		polickoHry = polickoHryDao.ulozit(polickoHry);
		assertNotNull(polickoHry.getId());
	}
	
	@Test
	void ulozitTest2() {
		PolickoHry pol1 = new PolickoHry(null, 10, 4, true);
		pol1.setIdHry(8L);
		PolickoHry pol2 = new PolickoHry(true, 5, 6, false);
		pol2.setIdHry(8L);
		List<PolickoHry> polickaHry = new ArrayList<>();
		polickaHry.add(pol1);
		polickaHry.add(pol2);
		List<PolickoHry> policka = polickoHryDao.ulozit(polickaHry);
		assertTrue(policka.size() == 2);
	}

	@Test
	void getPodlaHraIdTest() {
		List<PolickoHry> polickaHry = polickoHryDao.getPodlaHraId(8L);
		assertNotNull(polickaHry);
	}
	
	@Test
	void vymazatTest() {
		PolickoHry polickoHry = new PolickoHry(null, 10, 5, true);
		polickoHry.setIdHry(8L);
		polickoHryDao.ulozit(polickoHry);
		int velkostPred = polickoHryDao.getPodlaHraId(8L).size();
		polickoHryDao.vymazat(8L);
		int velkostPo = polickoHryDao.getPodlaHraId(8L).size();
		assertTrue(velkostPred > velkostPo);
	}
}
