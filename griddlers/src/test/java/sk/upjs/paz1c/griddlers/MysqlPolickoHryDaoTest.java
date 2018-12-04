package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.PolickoHry;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.PolickoHryDao;

class MysqlPolickoHryDaoTest {

	private PolickoHryDao polickoHryDao = DaoFactory.INSTANCE.getPolickoHryDao();

	@Test
	void getVsetkyTest() {
		List<PolickoHry> zoznam = polickoHryDao.getVsetky();
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
	}

	@Test
	void ulozitTest1() {
		PolickoHry polickoHry = new PolickoHry();
		polickoHry.setIdKrizovky(1L);
		polickoHry.setStav(false);
		polickoHry.setSurX(10);
		polickoHry.setSurY(15);
		polickoHry.setPozadovanyStav(false);
		// vytvorenie
		polickoHryDao.ulozit(polickoHry);
		assertNotNull(polickoHry.getId());
	}

	@Test
	void ulozitTest2() {
		PolickoHry polickoHry = new PolickoHry();
		polickoHry.setIdKrizovky(1L);
		polickoHry.setStav(false);
		polickoHry.setSurX(10);
		polickoHry.setSurY(15);
		polickoHry.setPozadovanyStav(false);
		polickoHryDao.ulozit(polickoHry);
		// aktualizacia
		polickoHry.setIdKrizovky(2L);
		polickoHryDao.ulozit(polickoHry);
		List<PolickoHry> polickaHry = polickoHryDao.getVsetky();
		for (PolickoHry polickoHry1 : polickaHry) {
			if (polickoHry1.getIdKrizovky() == polickoHry.getIdKrizovky()) {
				assertEquals(polickoHry.getIdKrizovky(), polickoHry1.getIdKrizovky());
				polickoHryDao.vymazat(polickoHry1.getId());
				return;
			}
		}
	}

	@Test
	void vymazatTest() {
		PolickoHry polickoHry = new PolickoHry();
		polickoHry.setIdKrizovky(1L);
		polickoHry.setStav(false);
		polickoHry.setSurX(10);
		polickoHry.setSurY(15);
		polickoHry.setPozadovanyStav(false);
		polickoHryDao.ulozit(polickoHry);
		Long id = polickoHry.getId();
		polickoHryDao.vymazat(id);
		List<PolickoHry> polickaHry = polickoHryDao.getVsetky();
		for (PolickoHry polickoHry1 : polickaHry) {
			assertNotEquals(id, polickoHry1.getId());
		}
	}
}
