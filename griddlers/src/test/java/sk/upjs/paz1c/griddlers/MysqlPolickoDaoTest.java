package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.PolickoDao;

class MysqlPolickoDaoTest {

	
	private PolickoDao polickoDao = DaoFactory.INSTANCE.getPolickoDao();
	
	private Long idKrizovky = 35L;

	@Test
	void getPodlaIdTest() {
		List<Policko> zoznam = polickoDao.getPodlaId(idKrizovky);
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
	}

	@Test
	void ulozitTest() {
		Policko policko1 = new Policko(false, 4, 890);
		Policko policko2 = new Policko(true, 4, 891);
		List<Policko> zoznamPred = polickoDao.getPodlaId(idKrizovky);
		int velkostPred = zoznamPred.size();
		List<Policko> policka = new ArrayList<>();
		policka.add(policko1);
		policka.add(policko2);
		polickoDao.ulozit(policka, idKrizovky);
		int velkostPo = polickoDao.getPodlaId(idKrizovky).size();
		assertEquals(velkostPred + 2, velkostPo);
	}

	@Test
	void vymazatTest() {
		int velkostPred = polickoDao.getPodlaId(idKrizovky).size();
		polickoDao.vymazat(idKrizovky);
		int velkostPo = polickoDao.getPodlaId(idKrizovky).size();
		assertTrue(velkostPred != 0 && velkostPo == 0);
	}

}
