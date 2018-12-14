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
import sk.upjs.paz1c.griddlers.persistentna.PolickoDao;

class MysqlPolickoDaoTest {

	private PolickoDao polickoDao = DaoFactory.INSTANCE.getPolickoDao();
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();

	private Krizovka krizovka;
	
	
	public MysqlPolickoDaoTest() {
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
	void getPodlaIdTest() {
		Long idKrizovky = krizovkaDao.ulozit(krizovka).getId();
		List<Policko> zoznam = polickoDao.getPodlaId(idKrizovky);
		assertNotNull(zoznam);
		assertTrue(zoznam.size() > 0);
		krizovkaDao.vymazat(idKrizovky);
	}

	@Test
	void ulozitTest() {
		Long idKrizovky = krizovkaDao.ulozit(krizovka).getId();
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
		krizovkaDao.vymazat(idKrizovky);
	}

	@Test
	void vymazatTest() {
		Long idKrizovky = krizovkaDao.ulozit(krizovka).getId();
		int velkostPred = polickoDao.getPodlaId(idKrizovky).size();
		polickoDao.vymazat(idKrizovky);
		int velkostPo = polickoDao.getPodlaId(idKrizovky).size();
		assertTrue(velkostPred != 0 && velkostPo == 0);
		krizovkaDao.vymazat(idKrizovky);
		
	}

}
