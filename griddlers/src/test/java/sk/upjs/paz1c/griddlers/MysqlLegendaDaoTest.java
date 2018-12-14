package sk.upjs.paz1c.griddlers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;
import sk.upjs.paz1c.griddlers.persistentna.LegendaDao;

class MysqlLegendaDaoTest {

	private LegendaDao legendaDao = DaoFactory.INSTANCE.getLegendaDao();
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();
	
	private Krizovka krizovka;


	public MysqlLegendaDaoTest() {
		krizovka = new Krizovka();
		krizovka.setNazov("abc");
		krizovka.setNarocnost(Narocnost.LAHKA);
		krizovka.setSirka(15);
		krizovka.setVyska(15);

		List<Policko> riesenie = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			riesenie.add(new Policko(true, i, i + 2));
		}
		krizovka.setRiesenie(riesenie);

		List<Legenda> legendaH = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 5; j++)
				legendaH.add(new Legenda(true, i, j, i + 5));
		}
		krizovka.setLegendaH(legendaH);

		List<Legenda> legendaL = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++)
				legendaL.add(new Legenda(false, i, j, i + 5));
		}
		krizovka.setLegendaL(legendaL);
	}
	
	@Test
	void vymazatTest() {
		Long idKrizovky = krizovkaDao.ulozit(krizovka).getId();
		int velkostPred = legendaDao.getHornaPodlaId(idKrizovky).size();
		legendaDao.vymazat(idKrizovky);
		int velkostPo = legendaDao.getHornaPodlaId(idKrizovky).size();
		assertTrue(velkostPo == 0 && velkostPred != 0);
		krizovkaDao.vymazat(idKrizovky);
	}

	@Test
	void ulozitTest() {
		Long idKrizovky = krizovkaDao.ulozit(krizovka).getId();
		List<Legenda> legenda = new ArrayList<>();
		int velkostPred = legendaDao.getHornaPodlaId(idKrizovky).size();
		for (int i = 0; i < 10; i++) {
			legenda.add(new Legenda(true, i + 251, 1, (int) Math.pow(i, 2)));
		}
		legendaDao.ulozit(legenda, idKrizovky);
		int velkostPo = legendaDao.getHornaPodlaId(idKrizovky).size();
		assertEquals(velkostPred + 10, velkostPo);
		krizovkaDao.vymazat(idKrizovky);
	}

	@Test
	void getHornaPodlaIdTest() {
		Long idKrizovky = krizovkaDao.ulozit(krizovka).getId();
		List<Legenda> legenda = legendaDao.getHornaPodlaId(idKrizovky);
		assertNotNull(legenda);
		assertTrue(legenda.size() > 0);
		krizovkaDao.vymazat(idKrizovky);
	}

	@Test
	void getLavaPodlaIdTest() {
		Long idKrizovky = krizovkaDao.ulozit(krizovka).getId();
		List<Legenda> legenda = legendaDao.getLavaPodlaId(idKrizovky);
		assertNotNull(legenda);
		assertTrue(legenda.size() > 0);
		krizovkaDao.vymazat(idKrizovky);
	}

}
