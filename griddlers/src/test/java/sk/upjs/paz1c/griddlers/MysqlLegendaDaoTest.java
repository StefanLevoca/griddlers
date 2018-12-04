package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.LegendaDao;

class MysqlLegendaDaoTest {

	private LegendaDao legendaDao = DaoFactory.INSTANCE.getLegendaDao();

	private Long idKrizovky = 35L;

	@Test
	void ulozitTest() {
		List<Legenda> legenda = new ArrayList<>();
		int velkostPred = legendaDao.getHornaPodlaId(idKrizovky).size();
		for(int i = 0; i < 10; i++) {
			legenda.add(new Legenda(true, i + 251, 1, (int)Math.pow(i, 2)));
		}
		legendaDao.ulozit(legenda, idKrizovky);
		int velkostPo = legendaDao.getHornaPodlaId(idKrizovky).size();
		assertEquals(velkostPred + 10, velkostPo);
		
		
	}
	
	@Test
	void getHornaPodlaIdTest() {
		List<Legenda> legenda = legendaDao.getHornaPodlaId(idKrizovky);
		assertNotNull(legenda);
	}

	@Test
	void getLavaPodlaIdTest() {
		List<Legenda> legenda = legendaDao.getLavaPodlaId(idKrizovky);
		assertNotNull(legenda);
	}
	
	@Test
	void vymazatTest() {
		int velkostPred = legendaDao.getHornaPodlaId(idKrizovky).size();
		legendaDao.vymazat(idKrizovky);
		int velkostPo = legendaDao.getHornaPodlaId(idKrizovky).size();
		assertTrue(velkostPo == 0 && velkostPred != 0);
	}
}
